package testcases.android;

import commons.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.android.wdio.LoginPage;
import reports.ExtentReportManager;
import utils.DataGenerator;

/**
 * LoginTest — Test suite for WDIO Demo App Login functionality.
 *
 * EXTENDS BaseTest → automatically gets:
 *   - Driver setup/teardown (@BeforeMethod / @AfterMethod)
 *   - Screenshot on failure
 *   - ExtentReport logging
 *   - TestNG reporting
 */
public class LoginTest extends BaseTest {

    // =========================================================
    //  TEST 1 — Valid Login
    // =========================================================

    /**
     * TC_LOGIN_001: Verify successful login with valid credentials.
     */
    @Test(
        description = "TC_LOGIN_001: Verify user can login with valid credentials",
        groups = {"smoke", "regression"}
    )
    public void verifyValidLogin() {
        LoginPage loginPage = new LoginPage(getDriver());
        String email = DataGenerator.generateEmail("valid_login");
        String password = DataGenerator.generatePassword();

        // Perform login flow using method chaining
        loginPage.navigateToLoginScreen()
                .login(email, password);

        ExtentReportManager.logInfo("Submitted login credentials.");

        // Assert success alert
        String alertTitle = loginPage.getAlertTitle();
        ExtentReportManager.logInfo("Alert title found: " + alertTitle);

        Assert.assertEquals(alertTitle, "Success", "Alert title should be 'Success' after valid login.");
        loginPage.clickAlertOkButton();
    }

    // =========================================================
    //  TEST 2 — Login Screen Navigation Verification
    // =========================================================

    /**
     * TC_LOGIN_002: Verify the Login page loads correctly when navigating from bottom tab.
     */
    @Test(
        description = "TC_LOGIN_002: Verify Login screen displays input fields and button",
        groups = {"smoke"}
    )
    public void verifyLoginPageIsDisplayed() {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.navigateToLoginScreen();
        ExtentReportManager.logInfo("Navigated to Login screen.");

        boolean isDisplayed = loginPage.isLoginScreenDisplayed();
        ExtentReportManager.logInfo("Login page displayed: " + isDisplayed);

        Assert.assertTrue(isDisplayed, "Login page should display email input and login button.");
    }
}
