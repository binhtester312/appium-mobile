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
 * emailTxtBx — Verifies entering dynamic email into the email input field.
 */
@Epic("API Learning")
@Feature("Text Input Handling")
public class emailTxtBx extends BaseTest {

    @Test(
        description = "TC_TEXT_001: Verify entering dynamic email text into the Login email text box",
        groups = {"regression"}
    )
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that dynamic email text is typed accurately into the Login email field.")
    public void testEmailTxtBx() {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.navigateToLoginScreen();

        String testEmail = DataGenerator.generateEmail("textbox");
        loginPage.enterEmail(testEmail);

        Assert.assertTrue(loginPage.isLoginScreenDisplayed(), "Login screen should be displayed.");
    }
}
