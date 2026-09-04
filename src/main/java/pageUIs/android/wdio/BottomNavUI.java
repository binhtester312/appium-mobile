package pageUIs.android.wdio;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;

/**
 * BottomNavUI — Stores UI locators for WDIO Demo App bottom navigation bar.
 */
public class BottomNavUI {
    public static final By HOME_TAB = AppiumBy.accessibilityId("Home");
    public static final By WEBVIEW_TAB = AppiumBy.accessibilityId("Webview");
    public static final By LOGIN_TAB = AppiumBy.accessibilityId("Login");
    public static final By FORMS_TAB = AppiumBy.accessibilityId("Forms");
    public static final By SWIPE_TAB = AppiumBy.accessibilityId("Swipe");
    public static final By DRAG_TAB = AppiumBy.accessibilityId("Drag");
}
