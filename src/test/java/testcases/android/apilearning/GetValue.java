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
 * GetValue — Verifies retrieving alert dialog text value.
 */
@Epic("API Learning")
@Feature("Value Retrieval & Assertions")
public class GetValue extends BaseTest {

    @Test(
        description = "TC_GETVAL_001: Verify retrieving text value from alert dialog after login using dynamic data",
        groups = {"regression"}
    )
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that the title text retrieved from the success alert dialog matches 'Success'.")
    public void testGetValue() {
        LoginPage loginPage = new LoginPage(getDriver());

        String dynamicEmail = DataGenerator.generateEmail("getvalue");
        String dynamicPassword = DataGenerator.generatePassword();

        String title = loginPage
                .navigateToLoginScreen()
                .enterEmail(dynamicEmail)
                .enterPassword(dynamicPassword)
                .tapLoginButton()
                .getAlertTitle();

        Assert.assertEquals(title, "Success", "Alert title should be 'Success'.");
        loginPage.clickAlertOkButton();
    }
}
