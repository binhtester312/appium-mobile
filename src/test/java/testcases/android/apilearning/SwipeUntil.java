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

public class SwipeUntil {

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

            // 5. Init start points and end points for Horizontal Swipe
            // Trục Y nằm ở giữa card (khoảng 65% chiều cao màn hình)
            int yStartPoint = (int) (screenHeight * 0.65);
            int yEndPoint = yStartPoint;

            // Vuốt từ phải qua trái (xStart: 80% -> xEnd: 10%)
            int xStartPoint = (int) (screenWidth * 0.80);
            int xEndPoint = (int) (screenWidth * 0.10);

            Point startPoint = new Point(xStartPoint, yStartPoint);
            Point endPoint = new Point(xEndPoint, yEndPoint);

            // 6. Conditional Swiping: Swipe from right to left until target card is displayed
            int swipeTime = 0;
            int maxSwipeTimes = 6;
            boolean notSeeingTheTargetCard = true;

            // Giảm implicit wait về 0s để check element trả về kết quả ngay lập tức
            appiumDriver.manage().timeouts().implicitlyWait(Duration.ZERO);

            System.out.println(">>> Bắt đầu vuốt có điều kiện (Conditional Swipe) để tìm card 'EXTENDABLE'...");

            while (notSeeingTheTargetCard && swipeTime < maxSwipeTimes) {
                try {
                    boolean isTargetCardDisplayed = !appiumDriver.findElements(
                            AppiumBy.xpath("//*[@text='EXTENDABLE' or contains(@text, 'EXTENDABLE')]")).isEmpty();

                    if (isTargetCardDisplayed) {
                        notSeeingTheTargetCard = false;
                        System.out.println(">>> [SUCCESS] Đã tìm thấy thẻ 'EXTENDABLE' sau " + swipeTime + " lần vuốt!");
                        break;
                    }
                } catch (Exception e) {
                    // Ignore exception if element not found yet
                }

                // Vuốt từ phải sang trái (Right -> Left)
                System.out.println("   [Swipe] Lần " + (swipeTime + 1) + "...");
                swipe(appiumDriver, startPoint, endPoint, Duration.ofMillis(300));
                swipeTime++;
                Thread.sleep(800);
            }

            if (notSeeingTheTargetCard) {
                System.err.println(">>> [FAIL] Không tìm thấy thẻ 'EXTENDABLE' sau " + maxSwipeTimes + " lần vuốt!");
            }

            // Khôi phục lại implicit wait
            appiumDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

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
        // Giữ nhẹ trước khi kéo
        swipe.addAction(new Pause(finger, Duration.ofMillis(200)));
        // Vuốt đến toạ độ kết thúc
        swipe.addAction(finger.createPointerMove(duration, PointerInput.Origin.viewport(), end.getX(), end.getY()));
        // Nhấc ngón tay lên (release)
        swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(Collections.singletonList(swipe));
    }
}
