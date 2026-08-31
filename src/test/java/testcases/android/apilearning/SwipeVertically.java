package testcases.android.apilearning;

import java.time.Duration;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
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

        // Click on Swipe label
        WebElement swipeLabel = wait
                .until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.accessibilityId("Swipe")));
        swipeLabel.click();

        // Make sure I'm on the target screen (Swipe module screen)
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@content-desc='Swipe']")));

        // Get the mobile screen sizes
        Dimension windowSize = appiumDriver.manage().window().getSize();
        int screenHeight = windowSize.getHeight();
        int screenWidth = windowSize.getWidth();

        // Init start points and end points to touch and release
        int xStartPoint = 50 * screenWidth / 100;
        int xEndPoint = xStartPoint;
        int yStartPoint = 90 * screenHeight / 100;
        int yEndPoint = 10 * screenHeight / 100;

        // Perform W3C Touch Actions

        // Thứ tự Logic chuẩn
        // Thao tác 1 (swipeUp) - Vuốt từ dưới (90%) lên trên (10%):
        // Cuộn màn hình xuống dưới để xem các nội dung bên dưới (như icon con robot
        // "You found me!").
        // Nghỉ 3 giây: Để bạn quan sát rõ màn hình đã cuộn xuống.
        // Thao tác 2 (swipeDown) - Vuốt từ trên (10%) xuống dưới (90%):
        // Cuộn màn hình ngược trở lại về vị trí ban đầu trên đỉnh trang.
        // Nghỉ 3 giây: Để bạn quan sát màn hình đã cuộn lại về đầu trang.

        // --- 1. SCROLL DOWN UNTIL "You found me" IS VISIBLE ---
        System.out.println(">>> 1. Executing SWIPE UP to scroll down until 'You found me' is visible...");

        // Set implicit wait to 1s so findElements check does not wait 30s per swipe
        appiumDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));

        boolean isRobotElementFound = false;

        for (int i = 0; i < 5; i++) {
            List<WebElement> elements = appiumDriver.findElements(By.xpath("//*[contains(@text, 'You found me')]"));
            if (!elements.isEmpty()) {
                isRobotElementFound = true;
                System.out.println(">>> Robot element found on swipe attempt #" + (i + 1));
                break;
            }

            System.out.println(">>> Swiping up (attempt #" + (i + 1) + ")...");
            PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger_up_" + i);
            Sequence swipeUp = new Sequence(finger, 1);

            swipeUp.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), xStartPoint, yStartPoint));
            swipeUp.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            swipeUp.addAction(finger.createPointerMove(Duration.ofMillis(800), PointerInput.Origin.viewport(), xEndPoint, yEndPoint));
            swipeUp.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

            appiumDriver.perform(Collections.singletonList(swipeUp));
        }

        // Restore implicit wait
        appiumDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

        // --- ASSERTION: Verify 'You found me' robot element is displayed ---
        Assert.assertTrue(isRobotElementFound, "Robot icon / 'You found me' text is not displayed!");
        System.out.println(">>> ASSERTION PASSED: Successfully verified 'You found me' robot element on screen!");

        // Dừng 3 giây để nhìn rõ robot icon trên màn hình
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // --- 2. SCROLL BACK UP TO TOP ---
        System.out.println(">>> 2. Executing SWIPE DOWN (Scrolling page back up to top)...");
        for (int i = 0; i < 3; i++) {
            PointerInput fingerBack = new PointerInput(PointerInput.Kind.TOUCH, "finger_down_" + i);
            Sequence swipeDown = new Sequence(fingerBack, 1);

            swipeDown.addAction(fingerBack.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), xEndPoint, yEndPoint));
            swipeDown.addAction(fingerBack.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            swipeDown.addAction(fingerBack.createPointerMove(Duration.ofMillis(800), PointerInput.Origin.viewport(), xStartPoint, yStartPoint));
            swipeDown.addAction(fingerBack.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

            appiumDriver.perform(Collections.singletonList(swipeDown));
        }

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
