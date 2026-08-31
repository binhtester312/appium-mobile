package testcases.android.apilearning;

import java.time.Duration;
import java.util.Collections;

import org.openqa.selenium.By;
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

        // Click on Swipe label
        WebElement swipeLabel = wait
                .until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.accessibilityId("Swipe")));
        swipeLabel.click();

        // Make sure I'm on the target screen (Swipe module screen)
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[@text='Swipe horizontal' or @content-desc='Swipe-screen' or @text='Swipe']")));


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

        // Tạo 1 chuỗi hành động số 1 cho ngón tay
        Sequence swipeUp = new Sequence(finger, 1);

        // B1: đưa ngón tay ở mép dưới screen
        swipeUp.addAction(
                finger.createPointerMove(Duration.ZERO, PointerInput.Origin
                        .viewport(), xStartPoint, yStartPoint));

        // B2: ấn ngón tay xuống mặt kính
        swipeUp.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));

        // B3: di chuyển ngón tay lên phía mép trên trong 1s
        swipeUp.addAction(finger.createPointerMove(Duration.ofMillis(1000), PointerInput.Origin.viewport(), xEndPoint,
                yEndPoint));

        // B4: nhấc ngón tay lên
        swipeUp.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        // B5: gửi toàn bộ 4 bước trên cho điện thoại thực thi
        appiumDriver.perform(Collections.singletonList(swipeUp));

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Scroll down - Swipe from top to bottom
        Sequence swipeDown = new Sequence(finger, 1);

        swipeDown.addAction(
                finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(),
                        xEndPoint, yEndPoint));

        swipeDown.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));

        swipeDown.addAction(finger.createPointerMove(Duration.ofMillis(1000),
                PointerInput.Origin.viewport(),
                xStartPoint, yStartPoint));

        swipeDown.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        appiumDriver.perform(Collections.singletonList(swipeDown));
    }

}
