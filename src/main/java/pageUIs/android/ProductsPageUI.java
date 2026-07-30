package pageUIs.android;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;

/**
 * ProductsPageUI — Stores UI element locators for the Products / Catalog screen and Navigation header.
 */
public class ProductsPageUI {

    // --- Navigation Header & Drawer ---
    public static final By MENU_BUTTON = AppiumBy.accessibilityId("View menu");
    public static final By MENU_LOGIN_ITEM = AppiumBy.accessibilityId("Login Menu Item");

    // --- Screen Identifiers ---
    public static final By APP_LOGO_HEADER = AppiumBy.accessibilityId("App logo and name");
    public static final By PRODUCTS_TITLE = By.xpath("//android.widget.TextView[@text='Products']");
}
