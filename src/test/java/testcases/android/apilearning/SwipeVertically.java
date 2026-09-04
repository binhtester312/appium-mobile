package testcases.android.apilearning;

import commons.BaseTest;
import io.appium.java_client.AppiumDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.android.wdio.SwipePage;
import reports.ExtentReportManager;
import utils.AppiumDriverEx;

/**
 * SwipeVertically — Refactored to Page Object Model (POM).
 * TC: Verify vertical scroll up and down gestures.
 */
public class SwipeVertically extends BaseTest {

    @Test(description = "Verify vertical swipe scrolling up and down")
    public void testSwipeVertically() {
        SwipePage swipePage = new SwipePage(getDriver());
        swipePage.navigateToSwipeScreen();
        ExtentReportManager.logInfo("Navigated to Swipe screen.");

        // Scroll down (finger bottom to top)
        swipePage.swipeVerticalUp();
        ExtentReportManager.logInfo("Performed vertical scroll down.");

        // Scroll up (finger top to bottom)
        swipePage.swipeVerticalDown();
        ExtentReportManager.logInfo("Performed vertical scroll up.");

        Assert.assertTrue(swipePage.isSwipeScreenDisplayed(), "Swipe screen should be displayed.");
        ExtentReportManager.logPass("Verified vertical scroll gestures completed successfully.");
    }

    public static void main(String[] args) {
        AppiumDriver driver = AppiumDriverEx.getAppiumDriver();
        try {
            SwipePage swipePage = new SwipePage(driver);
            swipePage.navigateToSwipeScreen();
            swipePage.swipeVerticalUp();
            swipePage.swipeVerticalDown();
            System.out.println(">>> [PASS] Swiped vertically successfully!");
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }
}