package commons;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utilities.ConfigReader;

import java.time.Duration;
import java.util.List;

/**
 * BasePage — Parent class for ALL Page Object classes.
 *
 * WHY PAGE OBJECT MODEL (POM):
 * - Separates test logic from UI
 * - Changes to the app UI only require updating the Page class — not every test
 * - Promotes code reuse (findElement, click, sendKeys all defined once here)
 *
 * WHY AppiumDriver (not AndroidDriver):
 * - AppiumDriver is the correct abstraction for Page Objects.
 * - Page Objects don't need Android-specific methods — those belong in
 * DriverFactory.
 * - If you later add iOS support, your Page Objects work unchanged.
 *
 * HOW TO USE:
 * public class LoginPage extends BasePage {
 * public LoginPage(AppiumDriver driver) { super(driver); }
 * }
 */
public class BasePage {

    private static final Logger log = LoggerFactory.getLogger(BasePage.class);

    protected AppiumDriver driver;
    protected FluentWait<WebDriver> wait;
    protected FluentWait<WebDriver> shortWait;

    private final int EXPLICIT_WAIT;
    private final int IMPLICIT_WAIT;

    /**
     * Constructor — all Page Objects receive the driver through here.
     *
     * @param driver The AppiumDriver instance from DriverFactory
     */
    public BasePage(AppiumDriver driver) {
        this.driver = driver;
        ConfigReader config = ConfigReader.getInstance();
        EXPLICIT_WAIT = config.getInt("explicit.wait");
        IMPLICIT_WAIT = config.getInt("implicit.wait");

        // IMPORTANT: Configure WebDriverWait to ignore NoSuchElementException during polling.
        // This prevents the exception from bubbling up and breaking the wait loop.
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_WAIT))
                .ignoring(NoSuchElementException.class);
        this.shortWait = new WebDriverWait(driver, Duration.ofSeconds(5))
                .ignoring(NoSuchElementException.class);
    }

    /**
     * Temporarily disables implicit wait before running an explicit wait to avoid
     * the well-known implicit+explicit wait conflict in Selenium/Appium.
     *
     * WHY: When implicit.wait is set (e.g. 10s), each findElement() call inside the
     * ExplicitWait polling loop ALSO waits up to 10s before throwing.
     * This makes a 20s ExplicitWait effectively retry only 1-2 times — not 40 times.
     * Fix: set implicit wait to 0 during ExplicitWait, restore afterward.
     */
    private void disableImplicitWait() {
        driver.manage().timeouts().implicitlyWait(Duration.ZERO);
    }

    private void restoreImplicitWait() {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICIT_WAIT));
    }

    protected WebElement waitForVisibility(By locator) {
        try {
            disableImplicitWait();
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } finally {
            restoreImplicitWait();
        }
    }

    protected WebElement waitForClickability(By locator) {
        try {
            disableImplicitWait();
            return wait.until(ExpectedConditions.elementToBeClickable(locator));
        } finally {
            restoreImplicitWait();
        }
    }

    protected boolean isElementPresent(By locator) {
        try {
            disableImplicitWait();
            driver.findElement(locator);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        } finally {
            restoreImplicitWait();
        }
    }

    protected List<WebElement> findElements(By locator) {
        try {
            disableImplicitWait();
            return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
        } finally {
            restoreImplicitWait();
        }
    }

    protected void click(By locator) {
        waitForClickability(locator).click();
    }


    protected void sendKeys(By locator, String text) {
        WebElement element = waitForVisibility(locator);
        element.clear();
        element.sendKeys(text);
    }

    protected String getText(By locator) {
        return waitForVisibility(locator).getText();
    }

    protected String getAttribute(By locator, String attribute) {
        return waitForVisibility(locator).getAttribute(attribute);
    }

    protected void waitForInvisibility(By locator) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    protected List<WebElement> waitForNumberOfElements(By locator, int count) {
        try {
            disableImplicitWait();
            wait.until(ExpectedConditions.numberOfElementsToBe(locator, count));
            return driver.findElements(locator);
        } finally {
            restoreImplicitWait();
        }
    }

    protected boolean isElementDisplayed(By locator) {
        try {
            disableImplicitWait();
            WebElement element = shortWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            return element != null && element.isDisplayed();
        } catch (Exception e) {
            return false;
        } finally {
            restoreImplicitWait();
        }
    }

    public pageObjects.android.wdio.BottomNavComponent getBottomNav() {
        return new pageObjects.android.wdio.BottomNavComponent(driver);
    }

    protected void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
