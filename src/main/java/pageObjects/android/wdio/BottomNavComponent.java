package pageObjects.android.wdio;

import commons.BasePage;
import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pageUIs.android.wdio.BottomNavUI;

/**
 * BottomNavComponent — Handles bottom navigation across all WDIO app screens with Page Transition Chaining and Allure @Step.
 */
public class BottomNavComponent extends BasePage {

    private static final Logger log = LoggerFactory.getLogger(BottomNavComponent.class);

    public BottomNavComponent(AppiumDriver driver) {
        super(driver);
    }

    @Step("Tap 'Home' tab on bottom navigation")
    public BottomNavComponent tapHomeTab() {
        log.info("Tapping Home tab.");
        click(BottomNavUI.HOME_TAB);
        return this;
    }

    @Step("Tap 'Webview' tab on bottom navigation")
    public BottomNavComponent tapWebviewTab() {
        log.info("Tapping Webview tab.");
        click(BottomNavUI.WEBVIEW_TAB);
        return this;
    }

    @Step("Tap 'Login' tab on bottom navigation")
    public LoginPage tapLoginTab() {
        log.info("Tapping Login tab.");
        click(BottomNavUI.LOGIN_TAB);
        return new LoginPage(driver);
    }

    @Step("Tap 'Forms' tab on bottom navigation")
    public FormsPage tapFormsTab() {
        log.info("Tapping Forms tab.");
        click(BottomNavUI.FORMS_TAB);
        return new FormsPage(driver);
    }

    @Step("Tap 'Swipe' tab on bottom navigation")
    public SwipePage tapSwipeTab() {
        log.info("Tapping Swipe tab.");
        click(BottomNavUI.SWIPE_TAB);
        return new SwipePage(driver);
    }

    @Step("Tap 'Drag' tab on bottom navigation")
    public BottomNavComponent tapDragTab() {
        log.info("Tapping Drag tab.");
        click(BottomNavUI.DRAG_TAB);
        return this;
    }
}
