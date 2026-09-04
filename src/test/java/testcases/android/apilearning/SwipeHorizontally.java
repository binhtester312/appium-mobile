package testcases.android.apilearning;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.AppiumDriverEx;

import java.time.Duration;
import java.util.Collections;

public class SwipeHorizontally {

    public static void main(String[] args) {
        // 1. Create a session
        AppiumDriver appiumDriver = AppiumDriverEx.getAppiumDriver();

        try {
            // 2. Click on Swipe label
            appiumDriver.findElement(AppiumBy.accessibilityId("Swipe")).click();

            // 3. Make sure on the target screen
            WebDriverWait wait = new WebDriverWait(appiumDriver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    AppiumBy.xpath("//android.widget.TextView[@text='Swipe horizontal']")));

            // 4. Get the mobile screen size
            Dimension windowSize = appiumDriver.manage().window().getSize();
            int screenHeight = windowSize.getHeight();
            int screenWidth = windowSize.getWidth();

            // 5. Init start points and end points
            int xStartPoint = 90 * screenWidth / 100;
            int xEndPoint = 10 * screenWidth / 100;
            int yStartPoint = 50 * screenHeight / 100;
            int yEndPoint = yStartPoint;

            Point startPoint = new Point(xStartPoint, yStartPoint);
            Point endPoint = new Point(xEndPoint, yEndPoint);

            // 6. Swipe from right to left (Next card)
            swipe(appiumDriver, startPoint, endPoint, Duration.ofMillis(1000));

            // Dừng ngắn giữa 2 lần vuốt để UI ổn định
            Thread.sleep(1000);

            // 7. Swipe from left to right (Previous card)
            swipe(appiumDriver, endPoint, startPoint, Duration.ofMillis(1000));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (appiumDriver != null) {
                appiumDriver.quit();
            }
        }
    }

    /**
     * Hàm dùng W3C Actions thay thế cho TouchAction đã bị xóa
     */
    public static void swipe(AppiumDriver driver, Point start, Point end, Duration duration) {
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence swipe = new Sequence(finger, 1);

        // Di chuyển ngón tay đến toạ độ bắt đầu
        swipe.addAction(
                finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), start.getX(), start.getY()));
        // Chạm ngón tay xuống màn hình (press)
        swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        // Giữ nhẹ trước khi kéo (tương tự waitAction)
        swipe.addAction(new Pause(finger, Duration.ofMillis(200)));
        // Vuốt đến toạ độ kết thúc
        swipe.addAction(finger.createPointerMove(duration, PointerInput.Origin.viewport(), end.getX(), end.getY()));
        // Nhấc ngón tay lên (release)
        swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(Collections.singletonList(swipe));
    }
}
