package pageObjects.android.wdio;

import commons.BasePage;
import io.appium.java_client.AppiumDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pageUIs.android.wdio.BottomNavUI;

/**
 * BottomNavComponent — Handles bottom navigation across all WDIO app screens.
 */
public class BottomNavComponent extends BasePage {

    private static final Logger log = LoggerFactory.getLogger(BottomNavComponent.class);

    public BottomNavComponent(AppiumDriver driver) {
        super(driver);
    }

    public void tapHomeTab() {
        log.info("Tapping Home tab.");
        click(BottomNavUI.HOME_TAB);
    }

    public void tapWebviewTab() {
        log.info("Tapping Webview tab.");
        click(BottomNavUI.WEBVIEW_TAB);
    }

    public void tapLoginTab() {
        log.info("Tapping Login tab.");
        click(BottomNavUI.LOGIN_TAB);
    }

    public void tapFormsTab() {
        log.info("Tapping Forms tab.");
        click(BottomNavUI.FORMS_TAB);
    }

    public void tapSwipeTab() {
        log.info("Tapping Swipe tab.");
        click(BottomNavUI.SWIPE_TAB);
    }

    public void tapDragTab() {
        log.info("Tapping Drag tab.");
        click(BottomNavUI.DRAG_TAB);
    }
}
