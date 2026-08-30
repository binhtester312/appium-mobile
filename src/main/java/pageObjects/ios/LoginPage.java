package pageObjects.ios;

import commons.BasePage;
import io.appium.java_client.AppiumDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pageUIs.ios.LoginPageUI;


/**
 * LoginPage (iOS) — Page Object for iOS Login screen.
 */
public class LoginPage extends BasePage {

    private static final Logger log = LoggerFactory.getLogger(LoginPage.class);

    public LoginPage(AppiumDriver driver) {
        super(driver);
        log.info("iOS LoginPage initialized.");
    }

    public void navigateToLoginScreen() {
        log.info("Navigating to iOS Login Screen...");
        click(LoginPageUI.MENU_BUTTON);
        click(LoginPageUI.MENU_LOGIN_ITEM);
    }

    public void enterUsername(String username) {
        sendKeys(LoginPageUI.USERNAME_INPUT, username);
    }

    public void enterPassword(String password) {
        log.info("Entering password on iOS.");
        sendKeys(LoginPageUI.PASSWORD_INPUT, password);
    }

    public void tapLoginButton() {
        log.info("Tapping Login button on iOS.");
        click(LoginPageUI.LOGIN_BUTTON);
    }

    public void login(String username, String password) {
        log.info("Performing iOS login for user: {}", username);
        enterUsername(username);
        enterPassword(password);
        tapLoginButton();
    }

    public String getErrorMessage() {
        return getText(LoginPageUI.ERROR_MESSAGE);
    }

    public String getWelcomeMessage() {
        return getText(LoginPageUI.WELCOME_TEXT);
    }

    public boolean isLoginPageDisplayed() {
        try {
            // LOGIN_BUTTON verified: accessibilityId="Login", type=XCUIElementTypeButton
            waitForVisibility(LoginPageUI.LOGIN_BUTTON);
            return true;
        } catch (Exception e) {
            log.warn("Login page not detected: {}", e.getMessage());
            return false;
        }
    }

    public boolean isErrorDisplayed() {
        return isElementPresent(LoginPageUI.ERROR_MESSAGE);
    }
}
