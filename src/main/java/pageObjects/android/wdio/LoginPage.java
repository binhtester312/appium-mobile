package pageObjects.android.wdio;

import commons.BasePage;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pageUIs.android.wdio.LoginUI;

import java.util.List;

/**
 * LoginPage — Page Object for WDIO Demo App Login screen.
 */
public class LoginPage extends BasePage {

    private static final Logger log = LoggerFactory.getLogger(LoginPage.class);

    public LoginPage(AppiumDriver driver) {
        super(driver);
    }

    /**
     * Navigates to the Login screen via bottom navigation.
     */
    public void navigateToLoginScreen() {
        log.info("Navigating to Login screen.");
        if (isElementPresent(LoginUI.ALERT_OK_BUTTON)) {
            try {
                click(LoginUI.ALERT_OK_BUTTON);
                sleep(500);
            } catch (Exception ignored) {}
        }
        click(LoginUI.LOGIN_NAV_TAB);
    }

    public void enterEmail(String email) {
        log.info("Entering email: {}", email);
        sendKeys(LoginUI.EMAIL_INPUT, email);
    }

    public void enterPassword(String password) {
        log.info("Entering password.");
        sendKeys(LoginUI.PASSWORD_INPUT, password);
    }

    public void tapLoginButton() {
        log.info("Tapping Login button.");
        click(LoginUI.LOGIN_BUTTON);
    }

    /**
     * Performs full login flow.
     */
    public void login(String email, String password) {
        log.info("Performing login for: {}", email);
        enterEmail(email);
        enterPassword(password);
        tapLoginButton();
    }

    public String getAlertTitle() {
        log.info("Getting alert dialog title.");
        return getText(LoginUI.ALERT_TITLE);
    }

    public String getAlertMessage() {
        log.info("Getting alert dialog message.");
        return getText(LoginUI.ALERT_MESSAGE);
    }

    public void clickAlertOkButton() {
        log.info("Clicking alert dialog OK button.");
        click(LoginUI.ALERT_OK_BUTTON);
    }

    public int getLoginTextElementsCount() {
        log.info("Waiting for and counting Login text elements.");
        List<WebElement> elements = waitForNumberOfElements(LoginUI.LOGIN_TEXT_ELEMENTS, 2);
        return elements.size();
    }

    public void clickLoginTextElementAt(int index) {
        log.info("Clicking Login text element at index: {}", index);
        List<WebElement> elements = waitForNumberOfElements(LoginUI.LOGIN_TEXT_ELEMENTS, 2);
        if (index < elements.size()) {
            elements.get(index).click();
        } else {
            throw(new IndexOutOfBoundsException("Index " + index + " out of bounds for size " + elements.size()));
        }
    }

    public boolean isLoginScreenDisplayed() {
        log.info("Checking if Login screen is displayed.");
        return isElementDisplayed(LoginUI.EMAIL_INPUT) || isElementDisplayed(LoginUI.LOGIN_BUTTON);
    }

    public boolean isAlertDisplayed() {
        log.info("Checking if Alert dialog is displayed.");
        return isElementDisplayed(LoginUI.ALERT_TITLE);
    }
}
