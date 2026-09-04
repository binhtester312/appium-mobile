package testcases.android.apilearning;

import commons.BaseTest;
import io.appium.java_client.AppiumDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.android.wdio.SwipePage;
import reports.ExtentReportManager;
import utils.AppiumDriverEx;

/**
 * SwipeHorizontally — Refactored to Page Object Model (POM).
 * TC: Verify swiping horizontally on carousel cards (Next & Previous).
 */
public class SwipeHorizontally extends BaseTest {

    @Test(description = "Verify horizontal swipe next and previous on carousel cards")
    public void testSwipeHorizontally() {
        SwipePage swipePage = new SwipePage(getDriver());
        swipePage.navigateToSwipeScreen();
        ExtentReportManager.logInfo("Navigated to Swipe screen.");

        // Swipe next (Right to Left)
        swipePage.swipeCardNext();
        ExtentReportManager.logInfo("Swiped right-to-left to view next card.");

        // Swipe previous (Left to Right)
        swipePage.swipeCardPrevious();
        ExtentReportManager.logInfo("Swiped left-to-right to return to previous card.");

        Assert.assertTrue(swipePage.isSwipeScreenDisplayed(), "Swipe screen should be displayed.");
        ExtentReportManager.logPass("Verified horizontal swipe gestures completed successfully.");
    }

    public static void main(String[] args) {
        AppiumDriver driver = AppiumDriverEx.getAppiumDriver();
        try {
            SwipePage swipePage = new SwipePage(driver);
            swipePage.navigateToSwipeScreen();
            swipePage.swipeCardNext();
            swipePage.swipeCardPrevious();
            System.out.println(">>> [PASS] Swiped horizontally successfully!");
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }
}
