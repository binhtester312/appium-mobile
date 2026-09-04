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
 * SwipeUntil — Verifies conditional swiping until target element is found.
 */
@Epic("API Learning")
@Feature("Swipe & Scroll Gestures")
public class SwipeUntil extends BaseTest {

    @Test(
        description = "TC_SWIPE_003: Verify conditional horizontal swiping until 'EXTENDABLE' card is found",
        groups = {"regression"}
    )
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that the test repeatedly swipes cards until the target card with text 'EXTENDABLE' appears.")
    public void testSwipeUntil() {
        SwipePage swipePage = new SwipePage(getDriver());
        swipePage.navigateToSwipeScreen();

        String targetCardText = "EXTENDABLE";
        boolean isFound = swipePage.swipeUntilCardVisible(targetCardText, 10);

        Assert.assertTrue(isFound, "Target card '" + targetCardText + "' should be found within 10 swipes.");
    }
}
