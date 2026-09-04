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

public class SwipeVertically {

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

            // 5. Init start points and end points (tính theo % màn hình)
            Point startPoint = new Point(screenWidth / 2, 90 * screenHeight / 100);
            Point endPoint   = new Point(screenWidth / 2, 10 * screenHeight / 100);

            // 6. Scroll up — Swipe từ dưới lên trên
            SwipeHelper.swipe(appiumDriver, startPoint, endPoint, Duration.ofMillis(1000));

            // Dừng ngắn giữa 2 lần vuốt để UI ổn định
            Thread.sleep(1000);

            // 7. Scroll down — Swipe từ trên xuống dưới
            SwipeHelper.swipe(appiumDriver, endPoint, startPoint, Duration.ofMillis(1000));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (appiumDriver != null) {
                appiumDriver.quit();
            }
        }
    }
}