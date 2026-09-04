package testcases.android.apilearning;

import commons.BaseTest;
import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.android.wdio.FormsPage;
import reports.AllureManager;
import utils.AppiumDriverEx;

import java.io.File;
import java.io.IOException;

/**
 * TakingScreenshot — Refactored to Page Object Model (POM) with Allure.
 * TC: Interact with Forms screen and capture a screenshot, saving to disk and Allure.
 */
@Epic("API Learning")
@Feature("Screenshot Capture")
public class TakingScreenshot extends BaseTest {

    @Test(
        description = "Verify interacting with Forms screen and capturing screenshot",
        groups = {"regression"}
    )
    @Severity(SeverityLevel.NORMAL)
    public void testTakingScreenshot() {
        FormsPage formsPage = new FormsPage(getDriver());
        formsPage.navigateToFormsScreen();

        // Turn switch ON if it is currently OFF
        if (!formsPage.isSwitchOn()) {
            formsPage.clickSwitch();
        }

        // Select dropdown option
        formsPage.selectDropdownOption("webdriver.io is awesome");

        // Capture screenshot and save to disk
        File screenshotFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
        String destinationPath = System.getProperty("user.dir") + "/screenshot/formsScreen.png";
        File destFile = new File(destinationPath);

        try {
            FileUtils.copyFile(screenshotFile, destFile);
        } catch (IOException e) {
            Assert.fail("Failed to save screenshot: " + e.getMessage());
        }

        // Attach to Allure Report
        AllureManager.saveScreenshot("Forms Screen Screenshot", getDriver());

        Assert.assertTrue(destFile.exists(), "Screenshot file should exist on disk.");
    }

    public static void main(String[] args) {
        AppiumDriver driver = AppiumDriverEx.getAppiumDriver();
        try {
            FormsPage formsPage = new FormsPage(driver);
            formsPage.navigateToFormsScreen();

            if (!formsPage.isSwitchOn()) {
                formsPage.clickSwitch();
            }

            formsPage.selectDropdownOption("webdriver.io is awesome");

            File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String destinationPath = System.getProperty("user.dir") + "/screenshot/formsScreen.png";
            FileUtils.copyFile(screenshotFile, new File(destinationPath));
            System.out.println("Screenshot saved successfully to: " + destinationPath);
            System.out.println(">>> [PASS] Took screenshot successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }
}
