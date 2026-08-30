package testcases.android.apilearning;

import org.openqa.selenium.WebElement;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import utils.AppiumDriverEx;

public class HandleToggleButton {

    public static void main(String[] args) {

        // Launch an appium session
        AppiumDriver appiumDriver = AppiumDriverEx.getAppiumDriver();

        // Click on the [Forms] label
        WebElement formsLabel = appiumDriver.findElement(AppiumBy.accessibilityId("Forms"));
        formsLabel.click();

        // Get the toggle label value before interacting with the switch
        WebElement switchTextElement = appiumDriver.findElement(AppiumBy.accessibilityId("switch-text"));
        System.out.println("Label Text BEFORE interacting with Toggle button: " + switchTextElement.getText());

        // Click on the Toggle button
        WebElement switchElement = appiumDriver.findElement(AppiumBy.accessibilityId("switch"));
        switchElement.click();

        // Get the toggle label value after interacting with the switch
        WebElement switchTextElementAfter = appiumDriver.findElement(AppiumBy.accessibilityId("switch-text"));
        System.out.println("Label Text AFTER interacting with Toggle button: " + switchTextElementAfter.getText());
    }
}
