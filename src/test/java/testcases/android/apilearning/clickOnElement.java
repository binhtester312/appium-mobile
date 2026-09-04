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

/**
 * clickOnElement — Refactored to Page Object Model (POM) with Allure.
 * TC: Verify navigating to Login screen by tapping Login tab.
 */
@Epic("API Learning")
@Feature("Navigation & Click Gestures")
public class clickOnElement extends BaseTest {

    @Test(
        description = "Verify clicking on Login tab opens Login screen",
        groups = {"smoke", "regression"}
    )
    @Severity(SeverityLevel.CRITICAL)
    public void testClickOnElement() {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.navigateToLoginScreen();

        Assert.assertTrue(loginPage.isLoginScreenDisplayed(), "Login screen should be displayed.");
    }

    public static void main(String[] args) {
        AppiumDriver driver = AppiumDriverEx.getAppiumDriver();
        try {
            LoginPage loginPage = new LoginPage(driver);
            loginPage.navigateToLoginScreen();
            System.out.println(">>> [PASS] Clicked on Login tab successfully!");
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }
}
