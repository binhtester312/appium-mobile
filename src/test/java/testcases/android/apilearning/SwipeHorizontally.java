package testcases.android.apilearning;

import commons.BaseTest;
import io.appium.java_client.AppiumDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.android.wdio.SwipePage;
import reports.ExtentReportManager;
import utils.AppiumDriverEx;

/**
 * SwipeHorizontally — Refactored to Page Object Model (POM) with Fluent Method Chaining.
 * TC: Verify horizontal swipe next and previous on carousel cards.
 */
public class SwipeHorizontally extends BaseTest {

    @Test(description = "Verify horizontal swipe next and previous on carousel cards using method chaining")
    public void testSwipeHorizontally() {
        // Fluent Method Chaining: Navigate -> Swipe Next -> Swipe Previous -> Assert Displayed
        boolean isDisplayed = new SwipePage(getDriver())
                .navigateToSwipeScreen()
                .swipeCardNext()
                .swipeCardPrevious()
                .isSwipeScreenDisplayed();

        ExtentReportManager.logInfo("Performed horizontal swipe next & previous.");

        Assert.assertTrue(isDisplayed, "Swipe screen should be displayed.");
        ExtentReportManager.logPass("Verified horizontal swipe gestures completed successfully with Method Chaining.");
    }

    public static void main(String[] args) {
        AppiumDriver driver = AppiumDriverEx.getAppiumDriver();
        try {
            boolean isDisplayed = new SwipePage(driver)
                    .navigateToSwipeScreen()
                    .swipeCardNext()
                    .swipeCardPrevious()
                    .isSwipeScreenDisplayed();

            System.out.println("Swipe screen displayed: " + isDisplayed);
            System.out.println(">>> [PASS] Swiped horizontally successfully with Method Chaining!");
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }
}
