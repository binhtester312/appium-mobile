package pageUIs.android;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;

/**
 * LoginPageUI — Stores ALL UI element locators for the Sauce Labs Demo App Login screen.
 */
public class LoginPageUI {

    // --- Navigation ---
    public static final By MENU_BUTTON = AppiumBy.accessibilityId("View menu");
    public static final By MENU_LOGIN_ITEM = AppiumBy.accessibilityId("Login Menu Item");

    // --- Input Fields ---
    public static final By USERNAME_INPUT = By.id("com.saucelabs.mydemoapp.android:id/nameET");
    public static final By PASSWORD_INPUT = By.id("com.saucelabs.mydemoapp.android:id/passwordET");

    // --- Buttons ---
    public static final By LOGIN_BUTTON = By.id("com.saucelabs.mydemoapp.android:id/loginBtn");

    // --- Labels / Messages ---
    // Error message text on login failure
    public static final By ERROR_MESSAGE = By.xpath("//android.widget.TextView[contains(@text, 'incorrect') or contains(@text, 'locked')]");
    
    // Header text on success screen (e.g. Products page title or catalog title)
    public static final By WELCOME_TEXT = By.xpath("//android.widget.TextView[@text='Products']");
}
