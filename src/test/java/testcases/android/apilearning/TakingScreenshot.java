package testcases.android.apilearning;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import utils.AppiumDriverEx;

public class TakingScreenshot {

    @Test
    public void testTakingScreenshot() {

        // Create a session first
        AppiumDriver appiumDriver = AppiumDriverEx.getAppiumDriver();

        WebDriverWait wait = new WebDriverWait(appiumDriver, Duration.ofSeconds(15));

        // Navigate to forms screen
        WebElement formsLabel = wait
                .until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.accessibilityId("Forms")));
        formsLabel.click();

        // Click on toggle button IF it's OFF
        WebElement switchElement = wait
                .until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.accessibilityId("switch")));
        WebElement switchTextElement = wait
                .until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.accessibilityId("switch-text")));
        final boolean isSwitchOn = switchTextElement.getText().equals("Click to turn the switch OFF");

        if (!isSwitchOn) {
            switchElement.click();
        }

        // Click on dropdown menu -> select the first option
        WebElement dropdownMenuElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath(
                        "//*[@content-desc='Dropdown' or @content-desc='select-Dropdown' or contains(@text, 'Select an item')]")));
        dropdownMenuElement.click();

        WebElement firstOption = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[@text='webdriver.io is awesome']")));
        firstOption.click();

        // Taking a screenshot
        File formScreenBase64Data = ((TakesScreenshot) appiumDriver).getScreenshotAs(OutputType.FILE);
        String formScreenFilePath = System.getProperty("user.dir") + "/screenshot/" + "formsScreen.png";

        try {
            FileUtils.copyFile(formScreenBase64Data, new File(formScreenFilePath));
            System.out.println("Screenshot saved successfully to: " + formScreenFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
