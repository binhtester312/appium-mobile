package testcases.android.apilearning;

import commons.BaseTest;
import io.appium.java_client.AppiumDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.android.wdio.SwipePage;
import reports.ExtentReportManager;
import utils.AppiumDriverEx;

/**
 * SwipeVertically — Refactored to Page Object Model (POM) with Fluent Method Chaining.
 * TC: Verify vertical scroll up and down gestures.
 */
public class SwipeVertically extends BaseTest {

    @Test(description = "Verify vertical swipe scrolling up and down using method chaining")
    public void testSwipeVertically() {
        // Fluent Method Chaining: Navigate -> Scroll Down -> Scroll Up -> Assert Displayed
        boolean isDisplayed = new SwipePage(getDriver())
                .navigateToSwipeScreen()
                .swipeVerticalUp()
                .swipeVerticalDown()
                .isSwipeScreenDisplayed();

        ExtentReportManager.logInfo("Performed vertical scroll down & up.");

        Assert.assertTrue(isDisplayed, "Swipe screen should be displayed.");
        ExtentReportManager.logPass("Verified vertical scroll gestures completed successfully with Method Chaining.");
    }

    public static void main(String[] args) {
        AppiumDriver driver = AppiumDriverEx.getAppiumDriver();
        try {
            boolean isDisplayed = new SwipePage(driver)
                    .navigateToSwipeScreen()
                    .swipeVerticalUp()
                    .swipeVerticalDown()
                    .isSwipeScreenDisplayed();

            System.out.println("Swipe screen displayed: " + isDisplayed);
            System.out.println(">>> [PASS] Swiped vertically successfully with Method Chaining!");
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }
}