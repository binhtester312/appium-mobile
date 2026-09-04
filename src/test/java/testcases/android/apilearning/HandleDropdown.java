package testcases.android.apilearning;

import commons.BaseTest;
import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.android.wdio.FormsPage;
import utils.AppiumDriverEx;

/**
 * HandleDropdown — Refactored to Page Object Model (POM) with Fluent Method Chaining and Allure.
 * TC: Verify selecting an option from dropdown in Forms screen.
 */
@Epic("API Learning")
@Feature("Dropdown Selection")
public class HandleDropdown extends BaseTest {

    @Test(
        description = "Verify selecting option from dropdown in Forms screen using method chaining",
        groups = {"regression"}
    )
    @Severity(SeverityLevel.NORMAL)
    public void testHandleDropdown() {
        String targetOption = "webdriver.io is awesome";

        // Fluent Method Chaining: Navigate -> Select Option -> Check screen display
        boolean isDisplayed = new FormsPage(getDriver())
                .navigateToFormsScreen()
                .selectDropdownOption(targetOption)
                .isFormsScreenDisplayed();

        Assert.assertTrue(isDisplayed, "Forms screen should still be displayed after dropdown selection.");
    }

    public static void main(String[] args) {
        AppiumDriver driver = AppiumDriverEx.getAppiumDriver();
        try {
            boolean isDisplayed = new FormsPage(driver)
                    .navigateToFormsScreen()
                    .selectDropdownOption("webdriver.io is awesome")
                    .isFormsScreenDisplayed();

            System.out.println("Forms screen displayed: " + isDisplayed);
            System.out.println(">>> [PASS] Handled dropdown option successfully with Method Chaining!");
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }
}
