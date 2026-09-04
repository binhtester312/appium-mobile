package pageUIs.android.saucelabs;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;

/**
 * ProductsPageUI — Stores UI element locators for Sauce Labs Demo App Products screen.
 */
public class ProductsPageUI {
    public static final By MENU_BUTTON = AppiumBy.accessibilityId("View menu");
    public static final By MENU_LOGIN_ITEM = AppiumBy.accessibilityId("Login Menu Item");
    public static final By APP_LOGO_HEADER = AppiumBy.accessibilityId("App logo and name");
    public static final By PRODUCTS_TITLE = By.xpath("//android.widget.TextView[@text='Products']");
}
