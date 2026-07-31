package pageUIs.ios;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;

/**
 * LoginPageUI (iOS) — Stores UI element locators for iOS Login screen.
 */
public class LoginPageUI {

    // --- Navigation ---
    public static final By MENU_BUTTON = AppiumBy.accessibilityId("More");
    public static final By MENU_LOGIN_ITEM = AppiumBy.accessibilityId("Login");

    // --- Input Fields ---
    public static final By USERNAME_INPUT = By.xpath("//XCUIElementTypeTextField | //*[@name='Username input field']");
    public static final By PASSWORD_INPUT = By.xpath("//XCUIElementTypeSecureTextField | //*[@name='Password input field']");

    // --- Buttons ---
    public static final By LOGIN_BUTTON = By.xpath("//XCUIElementTypeButton[contains(@name, 'Login') or contains(@name, 'Log In')] | //*[@name='login button'] | //XCUIElementTypeStaticText[@name='Login']");

    // --- Labels / Messages ---
    public static final By ERROR_MESSAGE = By.xpath("//XCUIElementTypeStaticText[contains(@name, 'incorrect') or contains(@name, 'locked') or contains(@name, 'Provided credentials')]");
    public static final By WELCOME_TEXT = AppiumBy.accessibilityId("Products");
}
