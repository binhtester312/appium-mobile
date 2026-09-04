package testcases.android;

import commons.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.android.wdio.LoginPage;
import utils.DataGenerator;

/**
 * LoginTest — Test suite for WDIO Demo App Login functionality with Allure reporting.
 */
@Epic("Authentication")
@Feature("Login Feature")
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
    @Story("Valid Login Flow")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Verify that a registered user can successfully log in with valid dynamic credentials and see Success dialog.")
    public void verifyValidLogin() {
        LoginPage loginPage = new LoginPage(getDriver());
        String email = DataGenerator.generateEmail("valid_login");
        String password = DataGenerator.generatePassword();

        // Perform login flow using method chaining
        loginPage.navigateToLoginScreen()
                .login(email, password);

        // Assert success alert
        String alertTitle = loginPage.getAlertTitle();
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
        groups = {"regression"}
    )
    @Story("Login UI Verification")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that the Login screen correctly renders email/password input fields and the Login CTA button.")
    public void verifyLoginPageIsDisplayed() {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.navigateToLoginScreen();

        boolean isDisplayed = loginPage.isLoginScreenDisplayed();
        Assert.assertTrue(isDisplayed, "Login page should display email input and login button.");
    }
}
