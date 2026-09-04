package testcases.android.apilearning;

import commons.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.android.wdio.FormsPage;

/**
 * HandleToggleButton — Verifies switch toggle interaction.
 */
@Epic("API Learning")
@Feature("Switch & Toggle Buttons")
public class HandleToggleButton extends BaseTest {

    @Test(
        description = "TC_TOGGLE_001: Verify toggling the switch button changes the switch label text",
        groups = {"regression"}
    )
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that clicking the switch element toggles its state and updates the status label text.")
    public void testHandleToggleButton() {
        FormsPage formsPage = new FormsPage(getDriver());
        formsPage.navigateToFormsScreen();

        String textBefore = formsPage.getSwitchText();
        formsPage.clickSwitch();
        String textAfter = formsPage.getSwitchText();

        Assert.assertNotEquals(textBefore, textAfter, "Switch text must change after toggling.");
    }
}
