package testcases.android.apilearning;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.AppiumDriverEx;
import utils.SwipeHelper;

import java.time.Duration;

public class SwipeUntil {

    public static void main(String[] args) {
        // 1. Create a session
        AppiumDriver appiumDriver = AppiumDriverEx.getAppiumDriver();

        try {
            // 2. Click on Swipe tab
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
            // Trục Y nằm ở khu vực carousel thẻ (khoảng 65% chiều cao màn hình)
            int yMid = (int) (screenHeight * 0.65);

            // Vuốt từ phải qua trái (xStart: 80% -> xEnd: 10%)
            Point startPoint = new Point((int) (screenWidth * 0.80), yMid);
            Point endPoint   = new Point((int) (screenWidth * 0.10), yMid);

            // 6. Conditional Swiping: Swipe từ phải sang trái cho đến khi thấy target card
            int swipeTime = 0;
            int maxSwipeTimes = 10;
            boolean notSeeingTheTargetCard = true;

            // Giảm implicit wait về 0s để check element trả về kết quả ngay lập tức
            appiumDriver.manage().timeouts().implicitlyWait(Duration.ZERO);

            System.out.println(">>> Bắt đầu vuốt có điều kiện (Conditional Swipe) để tìm card 'EXTENDABLE'...");

            while (notSeeingTheTargetCard && swipeTime < maxSwipeTimes) {
                try {
                    boolean isTargetCardDisplayed = !appiumDriver
                            .findElements(AppiumBy.xpath("//android.widget.TextView[contains(@text, 'EXTENDABLE')]"))
                            .isEmpty();

                    if (isTargetCardDisplayed) {
                        notSeeingTheTargetCard = false;
                        System.out.println(">>> [SUCCESS] Đã tìm thấy thẻ 'EXTENDABLE' sau " + swipeTime + " lần vuốt!");
                        break;
                    }
                } catch (Exception e) {
                    // Bỏ qua ngoại lệ nếu element chưa xuất hiện trên màn hình
                }

                // Vuốt từ phải sang trái (Right -> Left)
                System.out.println("   [Swipe] Lần " + (swipeTime + 1) + "...");
                SwipeHelper.swipe(appiumDriver, startPoint, endPoint, Duration.ofMillis(300));
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
}
