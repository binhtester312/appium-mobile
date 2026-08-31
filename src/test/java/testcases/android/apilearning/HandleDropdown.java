package testcases.android.apilearning;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
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

        WebDriverWait wait = new WebDriverWait(appiumDriver, Duration.ofSeconds(15));

        // Click on the dropdown element (handles 'Dropdown', 'select-Dropdown', or 'Select an item...')
        WebElement dropdownMenuElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[@content-desc='Dropdown' or @content-desc='select-Dropdown' or contains(@text, 'Select an item')]")
        ));
        dropdownMenuElement.click();

        // Select the first option
        WebElement firstOption = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[@text='webdriver.io is awesome']")
        ));
        firstOption.click();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

