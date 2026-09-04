package testcases.android.apilearning;

import commons.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.android.wdio.SwipePage;

/**
 * SwipeVertically — Verifies vertical swipe gestures (scroll up & down).
 */
@Epic("API Learning")
@Feature("Swipe & Scroll Gestures")
public class SwipeVertically extends BaseTest {

    @Test(
        description = "TC_SWIPE_001: Verify vertical swipe scrolling up and down using method chaining",
        groups = {"regression"}
    )
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that vertical swipe gestures scroll the view downwards and upwards smoothly.")
    public void testSwipeVertically() {
        boolean isDisplayed = new SwipePage(getDriver())
                .navigateToSwipeScreen()
                .swipeVerticalUp()
                .swipeVerticalDown()
                .isSwipeScreenDisplayed();

        Assert.assertTrue(isDisplayed, "Swipe screen should be displayed.");
    }
}