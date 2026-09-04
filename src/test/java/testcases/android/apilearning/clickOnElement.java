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

/**
 * clickOnElement — Verifies tapping Login navigation tab to open Login screen.
 */
@Epic("API Learning")
@Feature("Navigation & Click Gestures")
public class clickOnElement extends BaseTest {

    @Test(
        description = "TC_CLICK_001: Verify clicking on Login tab opens Login screen",
        groups = {"smoke", "regression"}
    )
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that clicking the Login navigation tab routes the user to the Login screen.")
    public void testClickOnElement() {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.navigateToLoginScreen();

        Assert.assertTrue(loginPage.isLoginScreenDisplayed(), "Login screen should be displayed.");
    }
}
