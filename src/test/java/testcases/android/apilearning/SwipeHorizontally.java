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
 * SwipeHorizontally — Verifies horizontal swipe gestures across carousel cards.
 */
@Epic("API Learning")
@Feature("Swipe & Scroll Gestures")
public class SwipeHorizontally extends BaseTest {

    @Test(
        description = "TC_SWIPE_002: Verify horizontal swipe next and previous on carousel cards using method chaining",
        groups = {"regression"}
    )
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that horizontal swipe gestures successfully navigate to the next and previous carousel cards.")
    public void testSwipeHorizontally() {
        boolean isDisplayed = new SwipePage(getDriver())
                .navigateToSwipeScreen()
                .swipeCardNext()
                .swipeCardPrevious()
                .isSwipeScreenDisplayed();

        Assert.assertTrue(isDisplayed, "Swipe screen should be displayed.");
    }
}
