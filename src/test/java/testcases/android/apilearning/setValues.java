package testcases.android.apilearning;

import commons.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.android.wdio.LoginPage;
import utils.DataGenerator;

/**
 * setValues — Verifies submitting login form with dynamic email and password.
 */
@Epic("API Learning")
@Feature("Form Submission & Values")
public class setValues extends BaseTest {

    @Test(
        description = "TC_SETVAL_001: Verify entering email and password and submitting login form using method chaining",
        groups = {"regression"}
    )
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that inputting dynamic credentials and tapping Login displays the success alert.")
    public void testSetValues() {
        LoginPage loginPage = new LoginPage(getDriver());

        String dynamicEmail = DataGenerator.generateEmail("setvalues");
        String dynamicPassword = DataGenerator.generatePassword();

        boolean isAlertShown = loginPage
                .navigateToLoginScreen()
                .enterEmail(dynamicEmail)
                .enterPassword(dynamicPassword)
                .tapLoginButton()
                .isAlertDisplayed();

        Assert.assertTrue(isAlertShown, "Success/Alert dialog should appear after login submission.");
        loginPage.clickAlertOkButton();
    }
}
