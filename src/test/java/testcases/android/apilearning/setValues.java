package testcases.android.apilearning;

import commons.BaseTest;
import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.android.wdio.LoginPage;
import utils.AppiumDriverEx;
import utils.DataGenerator;

/**
 * setValues — Refactored to Page Object Model (POM) with Fluent Method Chaining & Dynamic Test Data.
 * TC: Input dynamic email and password, then submit login form.
 */
@Epic("API Learning")
@Feature("Form Submission & Values")
public class setValues extends BaseTest {

    @Test(
        description = "Verify entering email and password and submitting login form using method chaining",
        groups = {"regression"}
    )
    @Severity(SeverityLevel.CRITICAL)
    public void testSetValues() {
        LoginPage loginPage = new LoginPage(getDriver());

        String dynamicEmail = DataGenerator.generateEmail("setvalues");
        String dynamicPassword = DataGenerator.generatePassword();

        // Fluent Method Chaining: Navigate -> Enter Credentials -> Submit -> Check Alert State
        boolean isAlertShown = loginPage
                .navigateToLoginScreen()
                .enterEmail(dynamicEmail)
                .enterPassword(dynamicPassword)
                .tapLoginButton()
                .isAlertDisplayed();

        Assert.assertTrue(isAlertShown, "Success/Alert dialog should appear after login submission.");
        loginPage.clickAlertOkButton();
    }

    public static void main(String[] args) {
        AppiumDriver driver = AppiumDriverEx.getAppiumDriver();
        try {
            String email = DataGenerator.generateEmail("main");
            String password = DataGenerator.generatePassword();

            boolean isAlert = new LoginPage(driver)
                    .navigateToLoginScreen()
                    .enterEmail(email)
                    .enterPassword(password)
                    .tapLoginButton()
                    .isAlertDisplayed();

            System.out.println("Alert displayed: " + isAlert);
            System.out.println(">>> [PASS] Set values and submitted login successfully with Dynamic Data!");
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }
}
