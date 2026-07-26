package testcases.android;

import commons.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.android.LoginPage;
import reports.ExtentReportManager;
import utilities.ConfigReader;

/**
 * LoginTest — Sample test class demonstrating the full framework.
 *
 * EXTENDS BaseTest → automatically gets:
 *   - Driver setup/teardown (@BeforeMethod / @AfterMethod)
 *   - Screenshot on failure
 *   - ExtentReport logging
 *   - TestNG reporting
 *
 * TEST PATTERN:
 *   1. Create Page Object (pass getDriver())
 *   2. Call action methods on the page (login, navigate, etc.)
 *   3. Assert expected results
 *
 * NOTE: These tests use placeholder locators from LoginPageUI.
 * They will FAIL until you update the locators to match your real app.
 * See the README for instructions on finding locators with Appium Inspector.
 */
public class LoginTest extends BaseTest {

    // =========================================================
    //  TEST 1 — Valid Login
    // =========================================================

    /**
     * TC_LOGIN_001: Verify successful login with valid credentials.
     *
     * Steps:
     *   1. Open the app (done automatically by DriverFactory)
     *   2. Enter valid username
     *   3. Enter valid password
     *   4. Tap Login button
     *   5. Verify welcome message is displayed
     *
     * Expected: User is logged in and welcome screen appears.
     */
    @Test(
        description = "TC_LOGIN_001: Verify user can login with valid credentials",
        groups = {"smoke", "regression"}
    )
    public void verifyValidLogin() {
        ConfigReader config = ConfigReader.getInstance();

        // Step 1: Initialize Login Page Object
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.navigateToLoginScreen();
        ExtentReportManager.logInfo("Navigated to Login screen.");

        // Step 2-4: Perform login
        loginPage.login(
                config.get("test.username"),
                config.get("test.password")
        );
        ExtentReportManager.logInfo("Login credentials entered and Login button tapped.");

        // Step 5: Assert welcome message
        String welcomeMessage = loginPage.getWelcomeMessage();
        ExtentReportManager.logInfo("Welcome message found: " + welcomeMessage);

        Assert.assertNotNull(welcomeMessage, "Welcome message should not be null after login.");
        Assert.assertFalse(welcomeMessage.isEmpty(), "Welcome message should not be empty.");
    }

    // =========================================================
    //  TEST 2 — Invalid Login
    // =========================================================

    /**
     * TC_LOGIN_002: Verify error message shown with invalid credentials.
     *
     * Steps:
     *   1. Open the app
     *   2. Enter invalid username
     *   3. Enter invalid password
     *   4. Tap Login button
     *   5. Verify error message is displayed
     *
     * Expected: Error message appears — user is NOT logged in.
     */
    @Test(
        description = "TC_LOGIN_002: Verify error message appears with invalid credentials",
        groups = {"regression"}
    )
    public void verifyInvalidLoginShowsError() {
        // Step 1: Initialize Login Page Object
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.navigateToLoginScreen();
        ExtentReportManager.logInfo("Navigated to Login screen.");

        // Step 2-4: Perform login with wrong credentials
        loginPage.login("invalid@user.com", "wrongpassword123");
        ExtentReportManager.logInfo("Invalid credentials entered.");

        // Step 5: Assert error is shown
        boolean isErrorShown = loginPage.isErrorDisplayed();
        ExtentReportManager.logInfo("Error message displayed: " + isErrorShown);

        Assert.assertTrue(isErrorShown,
                "Error message should appear when invalid credentials are used.");
    }

    // =========================================================
    //  TEST 3 — Login Page Load Verification
    // =========================================================

    /**
     * TC_LOGIN_003: Verify the Login page loads correctly on app launch.
     *
     * Steps:
     *   1. Open the app
     *   2. Verify Login button is visible
     *
     * Expected: Login screen is displayed as the first screen.
     */
    @Test(
        description = "TC_LOGIN_003: Verify Login screen loads on app launch",
        groups = {"smoke"}
    )
    public void verifyLoginPageIsDisplayed() {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.navigateToLoginScreen();
        ExtentReportManager.logInfo("Navigated to Login screen.");

        boolean isDisplayed = loginPage.isLoginPageDisplayed();
        ExtentReportManager.logInfo("Login page displayed: " + isDisplayed);

        Assert.assertTrue(isDisplayed,
                "Login page should be the first screen when app launches.");
    }
}
