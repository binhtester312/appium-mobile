package pageObjects.android.wdio;

import commons.BasePage;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pageUIs.android.wdio.FormsUI;

/**
 * FormsPage — Page Object for WDIO Demo App Forms screen.
 */
public class FormsPage extends BasePage {

    private static final Logger log = LoggerFactory.getLogger(FormsPage.class);

    public FormsPage(AppiumDriver driver) {
        super(driver);
    }

    /**
     * Navigates to Forms screen via bottom navigation.
     */
    public void navigateToFormsScreen() {
        log.info("Navigating to Forms screen.");
        click(FormsUI.FORMS_NAV_TAB);
    }

    public void enterTextInput(String text) {
        log.info("Entering text into input field: {}", text);
        sendKeys(FormsUI.TEXT_INPUT, text);
    }

    public String getTextInputResult() {
        log.info("Getting typed text result.");
        return getText(FormsUI.INPUT_RESULT);
    }

    public void clickSwitch() {
        log.info("Toggling switch button.");
        click(FormsUI.SWITCH);
    }

    public String getSwitchText() {
        log.info("Getting switch text.");
        return getText(FormsUI.SWITCH_TEXT);
    }

    public boolean isSwitchOn() {
        String text = getSwitchText();
        return text != null && text.contains("OFF"); // When it says "Click to turn the switch OFF", it is currently ON
    }

    public void turnSwitch(boolean turnOn) {
        log.info("Turning switch to: {}", turnOn ? "ON" : "OFF");
        boolean currentState = isSwitchOn();
        if (currentState != turnOn) {
            clickSwitch();
        }
    }

    public void openDropdown() {
        log.info("Opening dropdown menu.");
        click(FormsUI.DROPDOWN_MENU);
    }

    public void selectDropdownOption(String optionText) {
        log.info("Selecting dropdown option: {}", optionText);
        openDropdown();
        By optionLocator = FormsUI.getDropdownOption(optionText);
        click(optionLocator);
    }

    public boolean isFormsScreenDisplayed() {
        log.info("Checking if Forms screen is displayed.");
        return isElementDisplayed(FormsUI.TEXT_INPUT) || isElementDisplayed(FormsUI.SWITCH);
    }
}
