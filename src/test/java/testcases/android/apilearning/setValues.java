package testcases.android.apilearning;

import org.openqa.selenium.WebElement;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import utils.AppiumDriverEx;

public class setValues {

    public static void main(String[] args) {

        // 1. Launch the target app
        AppiumDriver appiumDriver = AppiumDriverEx.getAppiumDriver();

        // 2. Click on Login Label
        WebElement loginLabel = appiumDriver.findElement(AppiumBy.accessibilityId("Login"));
        loginLabel.click();

        // 3. Input username
        WebElement emailTxtBx = appiumDriver.findElement(AppiumBy.accessibilityId("input-email"));
        emailTxtBx.sendKeys("nhubinh@maildomain.com");

        // 4. Input password
        WebElement passwordTxtBx = appiumDriver.findElement(AppiumBy.accessibilityId("input-password"));
        passwordTxtBx.sendKeys("12345678");

        // 5. Click on Login Btn
        WebElement loginBtn = appiumDriver.findElement(AppiumBy.accessibilityId("button-LOGIN"));
        loginBtn.click();
    }
}
