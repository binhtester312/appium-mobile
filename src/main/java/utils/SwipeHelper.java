package utils;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;

import java.time.Duration;
import java.util.Collections;

/**
 * Helper dùng chung cho toàn bộ dự án.
 * Tự động tính toán toạ độ theo tỷ lệ % màn hình.
 * Sử dụng W3C Actions — 100% tương thích UiAutomator2 và XCUITest.
 */
public class SwipeHelper {

    // ─── Hằng số mặc định ──────────────────────────────────────────────────────

    /** Thời gian mặc định cho mỗi thao tác vuốt (ms) */
    private static final Duration DEFAULT_SWIPE_DURATION = Duration.ofMillis(800);

    /** Khoảng dừng ngắn trước khi kéo, giúp UiAutomator2 nhận diện đúng gesture */
    private static final Duration PRESS_HOLD_DURATION = Duration.ofMillis(200);

    // ─── Enum hướng vuốt ──────────────────────────────────────────────────────

    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    // ─── API chính ────────────────────────────────────────────────────────────

    /**
     * Vuốt từ điểm start → end với thời gian tuỳ chỉnh.
     *
     * @param driver   AppiumDriver đang hoạt động
     * @param start    Toạ độ bắt đầu (pixel)
     * @param end      Toạ độ kết thúc (pixel)
     * @param duration Tốc độ vuốt
     */
    public static void swipe(AppiumDriver driver, Point start, Point end, Duration duration) {
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence sequence = new Sequence(finger, 1);

        // Di chuyển ngón tay đến toạ độ bắt đầu
        sequence.addAction(
                finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), start.getX(), start.getY()));
        // Chạm ngón tay xuống màn hình (press)
        sequence.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        // Giữ nhẹ trước khi kéo để gesture được nhận diện chính xác
        sequence.addAction(new Pause(finger, PRESS_HOLD_DURATION));
        // Vuốt đến toạ độ kết thúc
        sequence.addAction(finger.createPointerMove(duration, PointerInput.Origin.viewport(), end.getX(), end.getY()));
        // Nhấc ngón tay lên (release)
        sequence.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(Collections.singletonList(sequence));
    }

    /**
     * Vuốt từ điểm start → end với tốc độ mặc định (800ms).
     */
    public static void swipe(AppiumDriver driver, Point start, Point end) {
        swipe(driver, start, end, DEFAULT_SWIPE_DURATION);
    }

    /**
     * Vuốt theo hướng (UP / DOWN / LEFT / RIGHT) dựa trên tỷ lệ % màn hình.
     * Không cần hardcode toạ độ — tự động tính theo kích thước thiết bị thực tế.
     *
     * @param driver    AppiumDriver đang hoạt động
     * @param direction Hướng vuốt
     * @param duration  Tốc độ vuốt
     */
    public static void swipeByDirection(AppiumDriver driver, Direction direction, Duration duration) {
        Dimension size = driver.manage().window().getSize();
        int width  = size.getWidth();
        int height = size.getHeight();

        Point start;
        Point end;

        switch (direction) {
            case UP:
                // Ngón tay đi từ 80% → 20% chiều dọc (cuộn nội dung lên)
                start = new Point(width / 2, height * 80 / 100);
                end   = new Point(width / 2, height * 20 / 100);
                break;
            case DOWN:
                // Ngón tay đi từ 20% → 80% chiều dọc (cuộn nội dung xuống)
                start = new Point(width / 2, height * 20 / 100);
                end   = new Point(width / 2, height * 80 / 100);
                break;
            case LEFT:
                // Ngón tay đi từ 80% → 10% chiều ngang (next item)
                start = new Point(width * 80 / 100, height / 2);
                end   = new Point(width * 10 / 100, height / 2);
                break;
            case RIGHT:
                // Ngón tay đi từ 10% → 80% chiều ngang (previous item)
                start = new Point(width * 10 / 100, height / 2);
                end   = new Point(width * 80 / 100, height / 2);
                break;
            default:
                throw new IllegalArgumentException("Hướng không hợp lệ: " + direction);
        }

        swipe(driver, start, end, duration);
    }

    /**
     * Vuốt theo hướng với tốc độ mặc định (800ms).
     */
    public static void swipeByDirection(AppiumDriver driver, Direction direction) {
        swipeByDirection(driver, direction, DEFAULT_SWIPE_DURATION);
    }
}
