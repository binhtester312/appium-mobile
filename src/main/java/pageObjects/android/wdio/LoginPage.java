package pageObjects.android.wdio;

import commons.BasePage;
import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pageUIs.android.wdio.LoginUI;

import java.util.List;

/**
 * LoginPage — Page Object for WDIO Demo App Login screen with Fluent Method Chaining and Allure @Step.
 */
public class LoginPage extends BasePage {

    private static final Logger log = LoggerFactory.getLogger(LoginPage.class);

    public LoginPage(AppiumDriver driver) {
        super(driver);
    }

    /**
     * Navigates to the Login screen via bottom navigation.
     */
    @Step("Navigate to Login screen via bottom navigation")
    public LoginPage navigateToLoginScreen() {
        log.info("Navigating to Login screen.");
        if (isElementPresent(LoginUI.ALERT_OK_BUTTON)) {
            try {
                click(LoginUI.ALERT_OK_BUTTON);
                sleep(500);
            } catch (Exception ignored) {}
        }
        click(LoginUI.LOGIN_NAV_TAB);
        return this;
    }

    @Step("Enter email: '{email}'")
    public LoginPage enterEmail(String email) {
        log.info("Entering email: {}", email);
        sendKeys(LoginUI.EMAIL_INPUT, email);
        return this;
    }

    @Step("Enter password")
    public LoginPage enterPassword(String password) {
        log.info("Entering password.");
        sendKeys(LoginUI.PASSWORD_INPUT, password);
        return this;
    }

    @Step("Tap Login button")
    public LoginPage tapLoginButton() {
        log.info("Tapping Login button.");
        click(LoginUI.LOGIN_BUTTON);
        return this;
    }

    /**
     * Performs full login flow using chained actions.
     */
    @Step("Login with email: '{email}'")
    public LoginPage login(String email, String password) {
        log.info("Performing login for: {}", email);
        return enterEmail(email)
                .enterPassword(password)
                .tapLoginButton();
    }

    @Step("Get alert dialog title")
    public String getAlertTitle() {
        log.info("Getting alert dialog title.");
        return getText(LoginUI.ALERT_TITLE);
    }

    @Step("Get alert dialog message")
    public String getAlertMessage() {
        log.info("Getting alert dialog message.");
        return getText(LoginUI.ALERT_MESSAGE);
    }

    @Step("Click alert dialog OK button")
    public LoginPage clickAlertOkButton() {
        log.info("Clicking alert dialog OK button.");
        click(LoginUI.ALERT_OK_BUTTON);
        return this;
    }

    @Step("Count login text elements")
    public int getLoginTextElementsCount() {
        log.info("Waiting for and counting Login text elements.");
        List<WebElement> elements = waitForNumberOfElements(LoginUI.LOGIN_TEXT_ELEMENTS, 2);
        return elements.size();
    }

    @Step("Click login text element at index: {index}")
    public LoginPage clickLoginTextElementAt(int index) {
        log.info("Clicking Login text element at index: {}", index);
        List<WebElement> elements = waitForNumberOfElements(LoginUI.LOGIN_TEXT_ELEMENTS, 2);
        if (index < elements.size()) {
            elements.get(index).click();
        } else {
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds for size " + elements.size());
        }
        return this;
    }

    @Step("Verify if Login screen is displayed")
    public boolean isLoginScreenDisplayed() {
        log.info("Checking if Login screen is displayed.");
        return isElementDisplayed(LoginUI.EMAIL_INPUT) || isElementDisplayed(LoginUI.LOGIN_BUTTON);
    }

    @Step("Verify if alert dialog is displayed")
    public boolean isAlertDisplayed() {
        log.info("Checking if Alert dialog is displayed.");
        return isElementDisplayed(LoginUI.ALERT_TITLE);
    }
}
