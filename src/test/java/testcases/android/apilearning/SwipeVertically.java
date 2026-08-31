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

        // --- 1. SCROLL DOWN PAGE (Swipe from bottom 90% to top 10%) ---
        System.out.println(">>> 1. Executing SWIPE UP (Scrolling page down to bottom)...");
        PointerInput finger1 = new PointerInput(PointerInput.Kind.TOUCH, "finger1");
        Sequence swipeUp = new Sequence(finger1, 1);

        // B1: Đưa ngón tay ở mép dưới screen (90% height)
        swipeUp.addAction(
                finger1.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), xStartPoint, yStartPoint));

        // B2: Ấn ngón tay xuống mặt kính
        swipeUp.addAction(finger1.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));

        // B3: Di chuyển ngón tay lên phía mép trên (10% height) trong 1s
        swipeUp.addAction(finger1.createPointerMove(Duration.ofMillis(1000), PointerInput.Origin.viewport(), xEndPoint,
                yEndPoint));

        // B4: Nhấc ngón tay lên
        swipeUp.addAction(finger1.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        // B5: Gửi chuỗi hành động cho Appium thực thi
        appiumDriver.perform(Collections.singletonList(swipeUp));

        // --- ASSERTION: Kiểm tra đã nhìn thấy icon/text "You found me!" sau khi cuộn xuống ---
        WebElement robotIconElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(@text, 'You found me') or contains(@content-desc, 'You found me') or contains(@text, 'found me')]")
        ));
        Assert.assertTrue(robotIconElement.isDisplayed(), "Robot icon / 'You found me!' text is not displayed!");
        System.out.println(">>> ASSERTION PASSED: Found 'You found me!' robot element on screen!");

        // Dừng 3 giây để bạn nhìn rõ màn hình đã cuộn xuống!
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // --- 2. SCROLL BACK UP PAGE (Swipe from top 10% to bottom 90%) ---
        System.out.println(">>> 2. Executing SWIPE DOWN (Scrolling page back up to top)...");
        PointerInput finger2 = new PointerInput(PointerInput.Kind.TOUCH, "finger2");
        Sequence swipeDown = new Sequence(finger2, 1);

        swipeDown.addAction(
                finger2.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), xEndPoint, yEndPoint));

        swipeDown.addAction(finger2.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));

        swipeDown.addAction(finger2.createPointerMove(Duration.ofMillis(1000), PointerInput.Origin.viewport(),
                xStartPoint, yStartPoint));

        swipeDown.addAction(finger2.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        appiumDriver.perform(Collections.singletonList(swipeDown));

        // Dừng 3 giây để nhìn rõ màn hình đã cuộn ngược trở lại!
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
