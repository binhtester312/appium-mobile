package pageObjects.android;

import commons.BasePage;
import io.appium.java_client.AppiumDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pageUIs.android.LoginPageUI;

/**
 * LoginPage — Page Object for the Login screen.
 *
 * RESPONSIBILITY:
 *   Encapsulates ALL interactions with the Login screen.
 *   Test classes call methods like loginPage.login("user", "pass")
 *   instead of directly interacting with elements.
 *
 * This class does NOT contain assertions — assertions belong in test classes.
 * This class does NOT contain raw locators — those are in LoginPageUI.
 */
public class LoginPage extends BasePage {

    private static final Logger log = LoggerFactory.getLogger(LoginPage.class);

    /**
     * Constructor — passes the driver up to BasePage.
     *
     * @param driver AndroidDriver from DriverFactory
     */
    public LoginPage(AppiumDriver driver) {
        super(driver);
        log.info("LoginPage initialized.");
    }

    /**
     * Navigates from the Catalog screen to the Login screen via the side menu drawer.
     */
    public void navigateToLoginScreen() {
        log.info("Navigating to Login Screen...");
        click(LoginPageUI.MENU_BUTTON);
        click(LoginPageUI.MENU_LOGIN_ITEM);
    }

    // =========================================================
    //  ACTION METHODS — User interactions on this screen
    // =========================================================

    /**
     * Types a username into the username field.
     *
     * @param username The username to enter
     */
    public void enterUsername(String username) {
        log.info("Entering username: {}", username);
        sendKeys(LoginPageUI.USERNAME_INPUT, username);
    }

    /**
     * Types a password into the password field.
     *
     * @param password The password to enter
     */
    public void enterPassword(String password) {
        log.info("Entering password.");
        sendKeys(LoginPageUI.PASSWORD_INPUT, password);
    }

    /**
     * Taps the Login button.
     */
    public void tapLoginButton() {
        log.info("Tapping Login button.");
        click(LoginPageUI.LOGIN_BUTTON);
    }

    /**
     * Complete login flow — combines enter credentials + tap login.
     * This is the high-level method tests will use most often.
     *
     * @param username The username
     * @param password The password
     */
    public void login(String username, String password) {
        log.info("Performing login for user: {}", username);
        enterUsername(username);
        enterPassword(password);
        tapLoginButton();
    }

    // =========================================================
    //  VERIFICATION METHODS — State checks for assertions
    // =========================================================

    /**
     * Returns the error message shown after a failed login.
     * Used in test assertions: assertEquals(loginPage.getErrorMessage(), "Invalid credentials")
     *
     * @return The error message text
     */
    public String getErrorMessage() {
        return getText(LoginPageUI.ERROR_MESSAGE);
    }

    /**
     * Returns the welcome message shown after a successful login.
     *
     * @return The welcome message text
     */
    public String getWelcomeMessage() {
        return getText(LoginPageUI.WELCOME_TEXT);
    }

    /**
     * Checks if the Login button is displayed on screen.
     * Used to verify the login page is loaded.
     *
     * @return true if Login button is visible
     */
    public boolean isLoginPageDisplayed() {
        return isElementPresent(LoginPageUI.LOGIN_BUTTON);
    }

    /**
     * Checks if an error message is currently visible.
     *
     * @return true if error message element is present
     */
    public boolean isErrorDisplayed() {
        return isElementPresent(LoginPageUI.ERROR_MESSAGE);
    }
}
