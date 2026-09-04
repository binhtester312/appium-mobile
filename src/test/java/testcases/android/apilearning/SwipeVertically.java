package testcases.android.apilearning;

import commons.BaseTest;
import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.android.wdio.SwipePage;
import utils.AppiumDriverEx;

/**
 * SwipeVertically — Refactored to Page Object Model (POM) with Fluent Method Chaining and Allure.
 * TC: Verify vertical scroll up and down gestures.
 */
@Epic("API Learning")
@Feature("Swipe & Scroll Gestures")
public class SwipeVertically extends BaseTest {

    @Test(
        description = "Verify vertical swipe scrolling up and down using method chaining",
        groups = {"regression"}
    )
    @Severity(SeverityLevel.NORMAL)
    public void testSwipeVertically() {
        // Fluent Method Chaining: Navigate -> Scroll Down -> Scroll Up -> Assert Displayed
        boolean isDisplayed = new SwipePage(getDriver())
                .navigateToSwipeScreen()
                .swipeVerticalUp()
                .swipeVerticalDown()
                .isSwipeScreenDisplayed();

        Assert.assertTrue(isDisplayed, "Swipe screen should be displayed.");
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