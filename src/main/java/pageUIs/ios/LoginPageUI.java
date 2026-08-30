package pageUIs.ios;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;

/**
 * LoginPageUI (iOS) — Stores UI element locators for iOS Login screen.
 *
 * Locator strategy follows appium_rules.md priority:
 *   1. accessibilityId  — stable, cross-platform
 *   2. iOSClassChain    — fast, iOS-specific structural query
 *   3. iOSNsPredicateString — fast, iOS predicate
 *   4. xpath            — last resort only
 *
 * Verified against real iPhone 17 Simulator (iOS 26.5) via Appium Inspector.
 */
public class LoginPageUI {

    // --- Navigation ---
    // "More" tab verified: accessibilityId = "More" ✅
    public static final By MENU_BUTTON = AppiumBy.accessibilityId("More");

    // "Login Button" is the real accessibility label for the Login menu item in the More menu.
    // NOTE: Inspector confirmed name="Login Button" — NOT "Login"
    public static final By MENU_LOGIN_ITEM = AppiumBy.accessibilityId("Login Button");

    // --- Input Fields ---
    // TextField & SecureTextField have NO name/accessibilityId on this app.
    // Use iOSClassChain (Rule 1 priority: faster than xpath) with [1] index.
    public static final By USERNAME_INPUT  = AppiumBy.iOSClassChain("**/XCUIElementTypeTextField[1]");
    public static final By PASSWORD_INPUT  = AppiumBy.iOSClassChain("**/XCUIElementTypeSecureTextField[1]");

    // --- Buttons ---
    // Login button: Inspector confirmed type=XCUIElementTypeButton, name="Login" ✅
    public static final By LOGIN_BUTTON = AppiumBy.accessibilityId("Login");

    // --- Labels / Messages ---
    // Error message has no accessibilityId — xpath is last resort per Rule 1
    public static final By ERROR_MESSAGE = AppiumBy.iOSNsPredicateString(
            "type == 'XCUIElementTypeStaticText' AND " +
            "(label CONTAINS 'credentials' OR label CONTAINS 'locked' OR label CONTAINS 'incorrect')"
    );

    // After login success, Products header is shown
    public static final By WELCOME_TEXT = AppiumBy.accessibilityId("Products");
}
