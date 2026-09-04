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
 * SwipeUntil — Refactored to Page Object Model (POM) with Allure.
 * TC: Verify conditional swiping until target card 'EXTENDABLE' appears on screen.
 */
@Epic("API Learning")
@Feature("Swipe & Scroll Gestures")
public class SwipeUntil extends BaseTest {

    @Test(
        description = "Verify conditional horizontal swiping until 'EXTENDABLE' card is found",
        groups = {"regression"}
    )
    @Severity(SeverityLevel.CRITICAL)
    public void testSwipeUntil() {
        SwipePage swipePage = new SwipePage(getDriver());
        swipePage.navigateToSwipeScreen();

        String targetCardText = "EXTENDABLE";
        boolean isFound = swipePage.swipeUntilCardVisible(targetCardText, 10);

        Assert.assertTrue(isFound, "Target card '" + targetCardText + "' should be found within 10 swipes.");
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
