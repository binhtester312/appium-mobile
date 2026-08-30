package testcases.android.apilearning;

import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import utils.AppiumDriverEx;

public class clickOnElement {

    @Test
    public static void testClickOnElement() {
        AppiumDriver appiumDriver = AppiumDriverEx.getAppiumDriver();
        WebElement loginLabel = appiumDriver.findElement(AppiumBy.accessibilityId("Login"));
        loginLabel.click();

    }
}
