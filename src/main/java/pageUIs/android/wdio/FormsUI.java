package pageUIs.android.wdio;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;

/**
 * FormsUI — Stores UI locators for WDIO Demo App Forms screen.
 */
public class FormsUI {
    public static final By FORMS_NAV_TAB = AppiumBy.accessibilityId("Forms");
    public static final By TEXT_INPUT = AppiumBy.accessibilityId("text-input");
    public static final By INPUT_RESULT = AppiumBy.accessibilityId("input-text-result");
    public static final By SWITCH = AppiumBy.accessibilityId("switch");
    public static final By SWITCH_TEXT = AppiumBy.accessibilityId("switch-text");
    public static final By DROPDOWN_MENU = AppiumBy.xpath("//*[@content-desc='Dropdown' or @content-desc='select-Dropdown' or contains(@text, 'Select an item')]");
    public static final By ACTIVE_BUTTON = AppiumBy.accessibilityId("button-Active");
    public static final By INACTIVE_BUTTON = AppiumBy.accessibilityId("button-Inactive");

    public static By getDropdownOption(String optionText) {
        return By.xpath("//*[@text='" + optionText + "']");
    }
}
