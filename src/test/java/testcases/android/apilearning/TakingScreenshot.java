package testcases.android.apilearning;

import commons.BaseTest;
import io.qameta.allure.Description;
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

import java.io.File;
import java.io.IOException;

/**
 * TakingScreenshot — Captures full screen screenshot and attaches to Allure Report.
 */
@Epic("API Learning")
@Feature("Screenshot Capture")
public class TakingScreenshot extends BaseTest {

    @Test(
        description = "TC_SHOT_001: Verify interacting with Forms screen and capturing screenshot",
        groups = {"regression"}
    )
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that Forms screen interactions are executed, a screenshot is saved locally, and embedded into Allure Report.")
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
}
