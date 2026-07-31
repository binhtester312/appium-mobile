package pageUIs.ios;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;

/**
 * ProductsPageUI (iOS) — Exact locators matching iOS Sauce Labs My Demo App.
 */
public class ProductsPageUI {

    // --- Bottom Tab Bar & Menu Items ---
    public static final By MENU_BUTTON = AppiumBy.accessibilityId("More");
    public static final By MENU_LOGIN_ITEM = AppiumBy.accessibilityId("Login");

    // --- Screen Identifiers ---
    public static final By APP_LOGO_HEADER = AppiumBy.accessibilityId("Products");
    public static final By PRODUCTS_TITLE = AppiumBy.accessibilityId("Products");
}
