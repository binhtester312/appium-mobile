package pageUIs.ios;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;

/**
 * ProductsPageUI (iOS) — Exact locators matching iOS Sauce Labs My Demo App.
 */
public class ProductsPageUI {

    // --- Bottom Tab Bar ---
    // IMPORTANT: Use "More-tab-item" NOT "More".
    // "More" is the child StaticText label: visible=FALSE, hittable=FALSE in XCUITest.
    // "More-tab-item" is the parent XCUIElementTypeOther: visible=TRUE, hittable=TRUE.
    // Verified via wdVisible/wdHittable attributes on real iPhone 17 Simulator.
    public static final By MENU_BUTTON = AppiumBy.accessibilityId("More-tab-item");

    // Inspector confirmed: Login menu item has accessibilityId = "Login Button" (NOT "Login")
    public static final By MENU_LOGIN_ITEM = AppiumBy.accessibilityId("Login Button");

    // --- Screen Identifiers ---
    // "Products" StaticText — check visible attribute if failing
    public static final By APP_LOGO_HEADER = AppiumBy.accessibilityId("Catalog-screen");
    public static final By PRODUCTS_TITLE  = AppiumBy.accessibilityId("Catalog-screen");

}
