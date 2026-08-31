package testcases.android.apilearning;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import utils.AppiumDriverEx;

public class HandleDropdown {

    @Test
    public void testHandleDropdown() {

        // Setup the session
        AppiumDriver appiumDriver = AppiumDriverEx.getAppiumDriver();

        // Go to Forms
        WebElement formsLabel = appiumDriver.findElement(AppiumBy.accessibilityId("Forms"));
        formsLabel.click();

        // Click on the dropdown element
        WebElement dropdownMenuElement = appiumDriver.findElement(AppiumBy.accessibilityId("select-Dropdown"));
        dropdownMenuElement.click();

        // Select the first option
        WebElement firstOption = appiumDriver.findElement(By.xpath("//*[@text='webdriver.io is awesome']"));
        firstOption.click();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new HandleDropdown().testHandleDropdown();
    }
}

