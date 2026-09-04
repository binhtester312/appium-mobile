package pageUIs.android.wdio;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;

/**
 * LoginUI — Stores UI locators for WDIO Demo App Login screen.
 */
public class LoginUI {
    public static final By LOGIN_NAV_TAB = AppiumBy.accessibilityId("Login");
    public static final By EMAIL_INPUT = AppiumBy.accessibilityId("input-email");
    public static final By PASSWORD_INPUT = AppiumBy.accessibilityId("input-password");
    public static final By LOGIN_BUTTON = AppiumBy.accessibilityId("button-LOGIN");
    public static final By LOGIN_TEXT_ELEMENTS = AppiumBy.xpath("//*[@text='Login']");
    public static final By SIGN_UP_TAB = AppiumBy.accessibilityId("button-sign-up-container");
    public static final By LOGIN_CONTAINER_TAB = AppiumBy.accessibilityId("button-login-container");

    // Native Alert Dialog Locators
    public static final By ALERT_TITLE = AppiumBy.xpath("//*[contains(@resource-id, 'alert_title') or @resource-id='android:id/alert_title']");
    public static final By ALERT_MESSAGE = AppiumBy.xpath("//*[contains(@resource-id, 'message') or @resource-id='android:id/message']");
    public static final By ALERT_OK_BUTTON = AppiumBy.xpath("//*[@text='OK' or contains(@resource-id, 'button1')]");
}
