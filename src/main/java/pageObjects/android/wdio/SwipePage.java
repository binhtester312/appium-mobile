package pageObjects.android.wdio;

import commons.BasePage;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pageUIs.android.wdio.SwipeUI;
import utils.SwipeHelper;

import java.time.Duration;

/**
 * SwipePage — Page Object for WDIO Demo App Swipe screen.
 */
public class SwipePage extends BasePage {

    private static final Logger log = LoggerFactory.getLogger(SwipePage.class);

    public SwipePage(AppiumDriver driver) {
        super(driver);
    }

    /**
     * Navigates to Swipe screen via bottom navigation.
     */
    public void navigateToSwipeScreen() {
        log.info("Navigating to Swipe screen.");
        click(SwipeUI.SWIPE_NAV_TAB);
        waitForVisibility(SwipeUI.SWIPE_HEADER);
    }

    public boolean isSwipeScreenDisplayed() {
        log.info("Checking if Swipe screen is displayed.");
        return isElementDisplayed(SwipeUI.SWIPE_HEADER);
    }

    public boolean isCardDisplayed(String cardText) {
        By cardLocator = SwipeUI.getCardLocator(cardText);
        return isElementPresent(cardLocator);
    }

    /**
     * Swipes horizontally to view the next card in the carousel (right to left).
     */
    public void swipeCardNext() {
        log.info("Swiping horizontal: Next card (Right to Left).");
        Dimension windowSize = driver.manage().window().getSize();
        int screenHeight = windowSize.getHeight();
        int screenWidth = windowSize.getWidth();

        int yMid = (int) (screenHeight * 0.65);
        Point startPoint = new Point((int) (screenWidth * 0.80), yMid);
        Point endPoint = new Point((int) (screenWidth * 0.10), yMid);

        SwipeHelper.swipe(driver, startPoint, endPoint, Duration.ofMillis(500));
        sleep(800);
    }

    /**
     * Swipes horizontally to view the previous card in the carousel (left to right).
     */
    public void swipeCardPrevious() {
        log.info("Swiping horizontal: Previous card (Left to Right).");
        Dimension windowSize = driver.manage().window().getSize();
        int screenHeight = windowSize.getHeight();
        int screenWidth = windowSize.getWidth();

        int yMid = (int) (screenHeight * 0.65);
        Point startPoint = new Point((int) (screenWidth * 0.10), yMid);
        Point endPoint = new Point((int) (screenWidth * 0.80), yMid);

        SwipeHelper.swipe(driver, startPoint, endPoint, Duration.ofMillis(500));
        sleep(800);
    }

    /**
     * Swipes vertically up (scrolls down the page).
     */
    public void swipeVerticalUp() {
        log.info("Swiping vertical: Scrolling down (finger moves bottom to top).");
        Dimension windowSize = driver.manage().window().getSize();
        int screenHeight = windowSize.getHeight();
        int screenWidth = windowSize.getWidth();

        Point startPoint = new Point(screenWidth / 2, (int) (screenHeight * 0.80));
        Point endPoint = new Point(screenWidth / 2, (int) (screenHeight * 0.20));

        SwipeHelper.swipe(driver, startPoint, endPoint, Duration.ofMillis(800));
        sleep(800);
    }

    /**
     * Swipes vertically down (scrolls up the page).
     */
    public void swipeVerticalDown() {
        log.info("Swiping vertical: Scrolling up (finger moves top to bottom).");
        Dimension windowSize = driver.manage().window().getSize();
        int screenHeight = windowSize.getHeight();
        int screenWidth = windowSize.getWidth();

        Point startPoint = new Point(screenWidth / 2, (int) (screenHeight * 0.20));
        Point endPoint = new Point(screenWidth / 2, (int) (screenHeight * 0.80));

        SwipeHelper.swipe(driver, startPoint, endPoint, Duration.ofMillis(800));
        sleep(800);
    }

    /**
     * Repeatedly swipes horizontal until a card containing cardText is found.
     *
     * @param cardText Target text to find on the card
     * @param maxSwipes Maximum number of swipes before giving up
     * @return true if card was found, false otherwise
     */
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
