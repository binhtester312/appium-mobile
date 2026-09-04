package pageObjects.android.saucelabs;

import commons.BasePage;
import io.appium.java_client.AppiumDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pageUIs.android.saucelabs.LoginPageUI;

/**
 * LoginPage — Page Object for Sauce Labs Demo App Login screen.
 */
public class LoginPage extends BasePage {

    private static final Logger log = LoggerFactory.getLogger(LoginPage.class);

    public LoginPage(AppiumDriver driver) {
        super(driver);
    }

    public void navigateToLoginScreen() {
        log.info("Navigating to Login Screen...");
        click(LoginPageUI.MENU_BUTTON);
        click(LoginPageUI.MENU_LOGIN_ITEM);
    }

    public void enterUsername(String username) {
        log.info("Entering username: {}", username);
        sendKeys(LoginPageUI.USERNAME_INPUT, username);
    }

    public void enterPassword(String password) {
        log.info("Entering password.");
        sendKeys(LoginPageUI.PASSWORD_INPUT, password);
    }

    public void tapLoginButton() {
        log.info("Tapping Login button.");
        click(LoginPageUI.LOGIN_BUTTON);
    }

    public void login(String username, String password) {
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
        return isElementPresent(LoginPageUI.LOGIN_BUTTON);
    }

    public boolean isErrorDisplayed() {
        return isElementPresent(LoginPageUI.ERROR_MESSAGE);
    }
}
