package testcases.android.apilearning;

import java.time.Duration;
import java.util.Collections;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import utils.AppiumDriverEx;

public class SwipeVertically {

    @Test
    public void testSwipeVertically() {

        // Create a session
        AppiumDriver appiumDriver = AppiumDriverEx.getAppiumDriver();

        WebDriverWait wait = new WebDriverWait(appiumDriver, Duration.ofSeconds(30));

        // Click on Forms label
        WebElement formsLabel = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.accessibilityId("Forms")));
        formsLabel.click();

        // Make sure I'm on the target screen
        wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.accessibilityId("switch-text")));

        // Get the mobile screen sizes
        Dimension windowSize = appiumDriver.manage().window().getSize();
        int screenHeight = windowSize.getHeight();
        int screenWidth = windowSize.getWidth();

        // Init start points and end points to touch and release
        int xStartPoint = 50 * screenWidth / 100;
        int xEndPoint = xStartPoint;
        int yStartPoint = 90 * screenHeight / 100;
        int yEndPoint = 10 * screenHeight / 100;

        // Perform W3C Touch Actions (Appium 8/9 standard migration from TouchAction)
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");

        // Scroll up - Swipe from bottom to top
        Sequence swipeUp = new Sequence(finger, 1);
        swipeUp.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), xStartPoint, yStartPoint));
        swipeUp.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipeUp.addAction(finger.createPointerMove(Duration.ofMillis(1000), PointerInput.Origin.viewport(), xEndPoint, yEndPoint));
        swipeUp.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        appiumDriver.perform(Collections.singletonList(swipeUp));

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Scroll down - Swipe from top to bottom
        Sequence swipeDown = new Sequence(finger, 1);
        swipeDown.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), xEndPoint, yEndPoint));
        swipeDown.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipeDown.addAction(finger.createPointerMove(Duration.ofMillis(1000), PointerInput.Origin.viewport(), xStartPoint, yStartPoint));
        swipeDown.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        appiumDriver.perform(Collections.singletonList(swipeDown));
    }

    public static void main(String[] args) {
        new SwipeVertically().testSwipeVertically();
    }
}
