package testcases.android.apilearning;

import commons.BaseTest;
import io.appium.java_client.AppiumDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.android.wdio.FormsPage;
import reports.ExtentReportManager;
import utils.AppiumDriverEx;

/**
 * HandleToggleButton — Refactored to Page Object Model (POM).
 * TC: Verify toggling the switch button and validating its text change.
 */
public class HandleToggleButton extends BaseTest {

    @Test(description = "Verify toggling the switch button changes the switch label text")
    public void testHandleToggleButton() {
        FormsPage formsPage = new FormsPage(getDriver());
        formsPage.navigateToFormsScreen();
        ExtentReportManager.logInfo("Navigated to Forms screen.");

        String textBefore = formsPage.getSwitchText();
        ExtentReportManager.logInfo("Switch text before click: " + textBefore);

        formsPage.clickSwitch();
        ExtentReportManager.logInfo("Clicked on switch button.");

        String textAfter = formsPage.getSwitchText();
        ExtentReportManager.logInfo("Switch text after click: " + textAfter);

        Assert.assertNotEquals(textBefore, textAfter, "Switch text must change after toggling.");
        ExtentReportManager.logPass("Verified switch toggled successfully.");
    }

    public static void main(String[] args) {
        AppiumDriver driver = AppiumDriverEx.getAppiumDriver();
        try {
            FormsPage formsPage = new FormsPage(driver);
            formsPage.navigateToFormsScreen();
            String before = formsPage.getSwitchText();
            System.out.println("Label Text BEFORE interacting with Toggle button: " + before);
            formsPage.clickSwitch();
            String after = formsPage.getSwitchText();
            System.out.println("Label Text AFTER interacting with Toggle button: " + after);
            System.out.println(">>> [PASS] Handled toggle button successfully!");
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }
}
