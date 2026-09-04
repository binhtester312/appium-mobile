package pageObjects.android.wdio;

import commons.BasePage;
import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pageUIs.android.wdio.SwipeUI;
import utils.SwipeHelper;

import java.time.Duration;

/**
 * SwipePage — Page Object for WDIO Demo App Swipe screen with Fluent Method Chaining and Allure @Step.
 * All swipe coordinates are calculated using meaningful constants instead of magic numbers.
 */
public class SwipePage extends BasePage {

    private static final Logger log = LoggerFactory.getLogger(SwipePage.class);

    // ─── Gesture Ratios & Timing Constants ───────────────────────────────────
    private static final double CAROUSEL_Y_RATIO = 0.65;
    private static final double HORIZONTAL_START_X_RATIO = 0.80;
    private static final double HORIZONTAL_END_X_RATIO = 0.10;
    private static final double VERTICAL_START_Y_RATIO = 0.80;
    private static final double VERTICAL_END_Y_RATIO = 0.20;
    private static final Duration HORIZONTAL_SWIPE_DURATION = Duration.ofMillis(500);
    private static final Duration VERTICAL_SWIPE_DURATION = Duration.ofMillis(800);
    private static final long UI_STABILIZE_PAUSE_MS = 800L;

    public SwipePage(AppiumDriver driver) {
        super(driver);
    }

    /**
     * Navigates to Swipe screen via bottom navigation.
     */
    @Step("Navigate to Swipe screen via bottom navigation")
    public SwipePage navigateToSwipeScreen() {
        log.info("Navigating to Swipe screen.");
        click(SwipeUI.SWIPE_NAV_TAB);
        waitForVisibility(SwipeUI.SWIPE_HEADER);
        return this;
    }

    @Step("Verify if Swipe screen is displayed")
    public boolean isSwipeScreenDisplayed() {
        log.info("Checking if Swipe screen is displayed.");
        return isElementDisplayed(SwipeUI.SWIPE_HEADER);
    }

    @Step("Check if card with text '{cardText}' is displayed")
    public boolean isCardDisplayed(String cardText) {
        By cardLocator = SwipeUI.getCardLocator(cardText);
        return isElementPresent(cardLocator);
    }

    /**
     * Swipes horizontally to view the next card in the carousel (right to left).
     */
    @Step("Swipe carousel to next card (Right to Left)")
    public SwipePage swipeCardNext() {
        log.info("Swiping horizontal: Next card (Right to Left).");
        Dimension windowSize = driver.manage().window().getSize();
        int screenHeight = windowSize.getHeight();
        int screenWidth = windowSize.getWidth();

        int yMid = (int) (screenHeight * CAROUSEL_Y_RATIO);
        Point startPoint = new Point((int) (screenWidth * HORIZONTAL_START_X_RATIO), yMid);
        Point endPoint = new Point((int) (screenWidth * HORIZONTAL_END_X_RATIO), yMid);

        SwipeHelper.swipe(driver, startPoint, endPoint, HORIZONTAL_SWIPE_DURATION);
        sleep(UI_STABILIZE_PAUSE_MS);
        return this;
    }

    /**
     * Swipes horizontally to view the previous card in the carousel (left to right).
     */
    @Step("Swipe carousel to previous card (Left to Right)")
    public SwipePage swipeCardPrevious() {
        log.info("Swiping horizontal: Previous card (Left to Right).");
        Dimension windowSize = driver.manage().window().getSize();
        int screenHeight = windowSize.getHeight();
        int screenWidth = windowSize.getWidth();

        int yMid = (int) (screenHeight * CAROUSEL_Y_RATIO);
        Point startPoint = new Point((int) (screenWidth * HORIZONTAL_START_X_RATIO), yMid);
        Point endPoint = new Point((int) (screenWidth * HORIZONTAL_END_X_RATIO), yMid);

        SwipeHelper.swipe(driver, startPoint, endPoint, HORIZONTAL_SWIPE_DURATION);
        sleep(UI_STABILIZE_PAUSE_MS);
        return this;
    }

    /**
     * Swipes vertically up (scrolls down the page).
     */
    @Step("Swipe vertically up (Scroll down page)")
    public SwipePage swipeVerticalUp() {
        log.info("Swiping vertical: Scrolling down (finger moves bottom to top).");
        Dimension windowSize = driver.manage().window().getSize();
        int screenHeight = windowSize.getHeight();
        int screenWidth = windowSize.getWidth();

        Point startPoint = new Point(screenWidth / 2, (int) (screenHeight * VERTICAL_START_Y_RATIO));
        Point endPoint = new Point(screenWidth / 2, (int) (screenHeight * VERTICAL_END_Y_RATIO));

        SwipeHelper.swipe(driver, startPoint, endPoint, VERTICAL_SWIPE_DURATION);
        sleep(UI_STABILIZE_PAUSE_MS);
        return this;
    }

    /**
     * Swipes vertically down (scrolls up the page).
     */
    @Step("Swipe vertically down (Scroll up page)")
    public SwipePage swipeVerticalDown() {
        log.info("Swiping vertical: Scrolling up (finger moves top to bottom).");
        Dimension windowSize = driver.manage().window().getSize();
        int screenHeight = windowSize.getHeight();
        int screenWidth = windowSize.getWidth();

        Point startPoint = new Point(screenWidth / 2, (int) (screenHeight * VERTICAL_END_Y_RATIO));
        Point endPoint = new Point(screenWidth / 2, (int) (screenHeight * VERTICAL_START_Y_RATIO));

        SwipeHelper.swipe(driver, startPoint, endPoint, VERTICAL_SWIPE_DURATION);
        sleep(UI_STABILIZE_PAUSE_MS);
        return this;
    }

    /**
     * Repeatedly swipes horizontal until a card containing cardText is found.
     *
     * @param cardText Target text to find on the card
     * @param maxSwipes Maximum number of swipes before giving up
     * @return true if card was found, false otherwise
     */
    @Step("Swipe horizontally until card containing '{cardText}' is visible (max {maxSwipes} swipes)")
    public boolean swipeUntilCardVisible(String cardText, int maxSwipes) {
        log.info("Swiping until card containing '{}' is visible (max {} swipes)...", cardText, maxSwipes);
        int swipeCount = 0;
        By cardLocator = SwipeUI.getCardLocator(cardText);

        while (swipeCount < maxSwipes) {
            if (isElementPresent(cardLocator)) {
                log.info("Found card '{}' after {} swipes.", cardText, swipeCount);
                return true;
            }
            swipeCardNext();
            swipeCount++;
        }

        boolean finalCheck = isElementPresent(cardLocator);
        if (finalCheck) {
            log.info("Found card '{}' on final check.", cardText);
        } else {
            log.warn("Card '{}' was NOT found after {} swipes.", cardText, maxSwipes);
        }
        return finalCheck;
    }
}
