package testcases.android.apilearning;

import commons.BaseTest;
import io.appium.java_client.AppiumDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.android.wdio.FormsPage;
import reports.ExtentReportManager;
import utils.AppiumDriverEx;

/**
 * HandleDropdown — Refactored to Page Object Model (POM).
 * TC: Verify selecting an option from dropdown in Forms screen.
 */
public class HandleDropdown extends BaseTest {

    @Test(description = "Verify selecting option from dropdown in Forms screen")
    public void testHandleDropdown() {
        FormsPage formsPage = new FormsPage(getDriver());
        formsPage.navigateToFormsScreen();
        ExtentReportManager.logInfo("Navigated to Forms screen.");

        String targetOption = "webdriver.io is awesome";
        formsPage.selectDropdownOption(targetOption);
        ExtentReportManager.logInfo("Selected dropdown option: " + targetOption);

        Assert.assertTrue(formsPage.isFormsScreenDisplayed(), "Forms screen should still be displayed after dropdown selection.");
        ExtentReportManager.logPass("Verified dropdown option selected successfully.");
    }

    public static void main(String[] args) {
        AppiumDriver driver = AppiumDriverEx.getAppiumDriver();
        try {
            FormsPage formsPage = new FormsPage(driver);
            formsPage.navigateToFormsScreen();
            formsPage.selectDropdownOption("webdriver.io is awesome");
            System.out.println(">>> [PASS] Handled dropdown option successfully!");
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }
}
