package pageUIs.android.wdio;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;

/**
 * SwipeUI — Stores UI locators for WDIO Demo App Swipe screen.
 */
public class SwipeUI {
    public static final By SWIPE_NAV_TAB = AppiumBy.accessibilityId("Swipe");
    public static final By SWIPE_HEADER = AppiumBy.xpath("//android.widget.TextView[@text='Swipe horizontal']");
    public static final By WDIO_LOGO = AppiumBy.accessibilityId("WebdriverIO logo");

    public static By getCardLocator(String cardText) {
        return By.xpath("//android.widget.TextView[contains(@text, '" + cardText + "')]");
    }
}
