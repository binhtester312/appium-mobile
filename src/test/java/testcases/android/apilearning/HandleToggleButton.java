package testcases.android.apilearning;

import commons.BaseTest;
import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.android.wdio.FormsPage;
import utils.AppiumDriverEx;

/**
 * HandleToggleButton — Refactored to Page Object Model (POM) with Allure.
 * TC: Verify toggling the switch button and validating its text change.
 */
@Epic("API Learning")
@Feature("Switch & Toggle Buttons")
public class HandleToggleButton extends BaseTest {

    @Test(
        description = "Verify toggling the switch button changes the switch label text",
        groups = {"regression"}
    )
    @Severity(SeverityLevel.NORMAL)
    public void testHandleToggleButton() {
        FormsPage formsPage = new FormsPage(getDriver());
        formsPage.navigateToFormsScreen();

        String textBefore = formsPage.getSwitchText();
        formsPage.clickSwitch();
        String textAfter = formsPage.getSwitchText();

        Assert.assertNotEquals(textBefore, textAfter, "Switch text must change after toggling.");
    }

    public static void main(String[] args) {
        AppiumDriver driver = AppiumDriverEx.getAppiumDriver();
        try {
            FormsPage formsPage = new FormsPage(driver);
            formsPage.navigateToFormsScreen();
            String before = formsPage.getSwitchText();
            System.out.println("Label Text BEFORE interacting with Toggle button: " + before);
            formsPage.clickSwitch();
            String after = formsPage.getSwitchText();
            System.out.println("Label Text AFTER interacting with Toggle button: " + after);
            System.out.println(">>> [PASS] Handled toggle button successfully!");
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }
}
