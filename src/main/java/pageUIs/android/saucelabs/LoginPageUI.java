package pageUIs.android.saucelabs;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;

/**
 * LoginPageUI — Stores UI element locators for Sauce Labs Demo App Login screen.
 */
public class LoginPageUI {
    public static final By MENU_BUTTON = AppiumBy.accessibilityId("View menu");
    public static final By MENU_LOGIN_ITEM = AppiumBy.accessibilityId("Login Menu Item");
    public static final By USERNAME_INPUT = By.id("com.saucelabs.mydemoapp.android:id/nameET");
    public static final By PASSWORD_INPUT = By.id("com.saucelabs.mydemoapp.android:id/passwordET");
    public static final By LOGIN_BUTTON = By.id("com.saucelabs.mydemoapp.android:id/loginBtn");
    public static final By ERROR_MESSAGE = By.xpath("//android.widget.TextView[contains(@text, 'incorrect') or contains(@text, 'locked')]");
    public static final By WELCOME_TEXT = By.xpath("//android.widget.TextView[@text='Products']");
}
