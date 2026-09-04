package pageObjects.android.wdio;

import commons.BasePage;
import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pageUIs.android.wdio.FormsUI;

/**
 * FormsPage — Page Object for WDIO Demo App Forms screen with Fluent Method Chaining and Allure @Step.
 */
public class FormsPage extends BasePage {

    private static final Logger log = LoggerFactory.getLogger(FormsPage.class);

    public FormsPage(AppiumDriver driver) {
        super(driver);
    }

    /**
     * Navigates to Forms screen via bottom navigation.
     */
    @Step("Navigate to Forms screen via bottom navigation")
    public FormsPage navigateToFormsScreen() {
        log.info("Navigating to Forms screen.");
        click(FormsUI.FORMS_NAV_TAB);
        return this;
    }

    @Step("Enter text into input field: '{text}'")
    public FormsPage enterTextInput(String text) {
        log.info("Entering text into input field: {}", text);
        sendKeys(FormsUI.TEXT_INPUT, text);
        return this;
    }

    @Step("Get typed text result")
    public String getTextInputResult() {
        log.info("Getting typed text result.");
        return getText(FormsUI.INPUT_RESULT);
    }

    @Step("Click switch toggle button")
    public FormsPage clickSwitch() {
        log.info("Toggling switch button.");
        click(FormsUI.SWITCH);
        return this;
    }

    @Step("Get switch label text")
    public String getSwitchText() {
        log.info("Getting switch text.");
        return getText(FormsUI.SWITCH_TEXT);
    }

    @Step("Check if switch is ON")
    public boolean isSwitchOn() {
        String text = getSwitchText();
        return text != null && text.contains("OFF"); // When it says "Click to turn the switch OFF", it is currently ON
    }

    @Step("Turn switch to: {turnOn}")
    public FormsPage turnSwitch(boolean turnOn) {
        log.info("Turning switch to: {}", turnOn ? "ON" : "OFF");
        boolean currentState = isSwitchOn();
        if (currentState != turnOn) {
            clickSwitch();
        }
        return this;
    }

    @Step("Open dropdown menu")
    public FormsPage openDropdown() {
        log.info("Opening dropdown menu.");
        click(FormsUI.DROPDOWN_MENU);
        return this;
    }

    @Step("Select dropdown option: '{optionText}'")
    public FormsPage selectDropdownOption(String optionText) {
        log.info("Selecting dropdown option: {}", optionText);
        openDropdown();
        By optionLocator = FormsUI.getDropdownOption(optionText);
        click(optionLocator);
        return this;
    }

    @Step("Verify if Forms screen is displayed")
    public boolean isFormsScreenDisplayed() {
        log.info("Checking if Forms screen is displayed.");
        return isElementDisplayed(FormsUI.TEXT_INPUT) || isElementDisplayed(FormsUI.SWITCH);
    }
}
