package testcases.android.apilearning;

import commons.BaseTest;
import io.appium.java_client.AppiumDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.android.wdio.SwipePage;
import reports.ExtentReportManager;
import utils.AppiumDriverEx;

/**
 * SwipeUntil — Refactored to Page Object Model (POM).
 * TC: Verify conditional swiping until target card 'EXTENDABLE' appears on screen.
 */
public class SwipeUntil extends BaseTest {

    @Test(description = "Verify conditional horizontal swiping until 'EXTENDABLE' card is found")
    public void testSwipeUntil() {
        SwipePage swipePage = new SwipePage(getDriver());
        swipePage.navigateToSwipeScreen();
        ExtentReportManager.logInfo("Navigated to Swipe screen.");

        String targetCardText = "EXTENDABLE";
        boolean isFound = swipePage.swipeUntilCardVisible(targetCardText, 10);
        ExtentReportManager.logInfo("Card search result for '" + targetCardText + "': " + isFound);

        Assert.assertTrue(isFound, "Target card '" + targetCardText + "' should be found within 10 swipes.");
        ExtentReportManager.logPass("Verified conditional swipe found target card successfully.");
    }

    public static void main(String[] args) {
        AppiumDriver driver = AppiumDriverEx.getAppiumDriver();
        try {
            SwipePage swipePage = new SwipePage(driver);
            swipePage.navigateToSwipeScreen();
            boolean found = swipePage.swipeUntilCardVisible("EXTENDABLE", 10);
            if (found) {
                System.out.println(">>> [PASS] Found card 'EXTENDABLE' successfully!");
            } else {
                System.err.println(">>> [FAIL] Could not find card 'EXTENDABLE'!");
            }
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }
}
