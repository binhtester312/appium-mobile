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
 * HandleMultipleMatchedElements — Verifies interacting with elements sharing identical locators.
 */
@Epic("API Learning")
@Feature("Element Lists & Multiple Matching")
public class HandleMultipleMatchedElements extends BaseTest {

    @Test(
        description = "TC_MULTI_001: Verify handling multiple matched elements with text 'Login'",
        groups = {"regression"}
    )
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that multiple elements matching the Login locator are captured into a List and accessed by index.")
    public void testHandleMultipleMatchedElements() {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.navigateToLoginScreen();

        int count = loginPage.getLoginTextElementsCount();
        Assert.assertTrue(count >= 2, "There should be at least 2 elements with text 'Login'.");

        loginPage.clickLoginTextElementAt(0);
    }
}
