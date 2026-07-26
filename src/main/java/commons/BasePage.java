package commons;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
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
 *   - Separates test logic from UI interaction logic
 *   - Changes to the app UI only require updating the Page class — not every test
 *   - Promotes code reuse (findElement, click, sendKeys all defined once here)
 *
 * WHY AppiumDriver (not AndroidDriver):
 *   - AppiumDriver is the correct abstraction for Page Objects.
 *   - It avoids compile-time issues with removed Selenium 3 interfaces
 *     (ContextAware, LocationContext) that AndroidDriver's hierarchy references.
 *   - Page Objects don't need Android-specific methods — those belong in DriverFactory.
 *   - If you later add iOS support, your Page Objects work unchanged.
 *
 * HOW TO USE:
 *   public class LoginPage extends BasePage {
 *       public LoginPage(AppiumDriver driver) { super(driver); }
 *   }
 */
public class BasePage {

    private static final Logger log = LoggerFactory.getLogger(BasePage.class);

    protected AppiumDriver driver;
    protected WebDriverWait wait;
    protected WebDriverWait shortWait;

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

        this.wait = new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_WAIT));
        this.shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    // =========================================================
    //  ELEMENT FINDERS
    // =========================================================

    /**
     * Waits for element to be visible, then returns it.
     * ALWAYS prefer this over driver.findElement() directly.
     */
    protected WebElement waitForVisibility(By locator) {
        log.debug("Waiting for visibility of: {}", locator);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Waits for element to be clickable.
     */
    protected WebElement waitForClickability(By locator) {
        log.debug("Waiting for clickability of: {}", locator);
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    /**
     * Checks if an element is present in the DOM.
     */
    protected boolean isElementPresent(By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    /**
     * Returns all elements matching the locator.
     */
    protected List<WebElement> findElements(By locator) {
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
    }

    // =========================================================
    //  INTERACTIONS
    // =========================================================

    /**
     * Clicks an element after waiting for it to be clickable.
     */
    protected void click(By locator) {
        log.debug("Clicking: {}", locator);
        waitForClickability(locator).click();
    }

    /**
     * Clears a text field and types the given text.
     */
    protected void sendKeys(By locator, String text) {
        log.debug("Typing '{}' into: {}", text, locator);
        WebElement element = waitForVisibility(locator);
        element.clear();
        element.sendKeys(text);
    }

    /**
     * Gets the text content of an element.
     */
    protected String getText(By locator) {
        return waitForVisibility(locator).getText();
    }

    /**
     * Gets the value of an attribute on an element.
     * Useful for checking if a button is enabled, getting content-desc, etc.
     */
    protected String getAttribute(By locator, String attribute) {
        return waitForVisibility(locator).getAttribute(attribute);
    }

    // =========================================================
    //  WAIT HELPERS
    // =========================================================

    /**
     * Pauses for a fixed duration.
     * Use sparingly — prefer explicit waits. Only use for Android animations.
     */
    protected void sleep(long milliseconds) {
        try {
            log.debug("Sleeping {}ms...", milliseconds);
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.warn("Sleep interrupted: {}", e.getMessage());
        }
    }

    /**
     * Waits until an element is NOT visible (e.g., loading spinner disappears).
     */
    protected void waitForInvisibility(By locator) {
        log.debug("Waiting for invisibility of: {}", locator);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    // =========================================================
    //  APP NAVIGATION
    // =========================================================

    /**
     * Navigates back using the Android Back button.
     */
    protected void pressBack() {
        log.debug("Pressing Android Back button");
        driver.navigate().back();
    }
}
