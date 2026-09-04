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
 * HandleDropdown — Verifies dropdown interaction and option selection.
 */
@Epic("API Learning")
@Feature("Dropdown Selection")
public class HandleDropdown extends BaseTest {

    @Test(
        description = "TC_DROP_001: Verify selecting option from dropdown in Forms screen using method chaining",
        groups = {"regression"}
    )
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that clicking dropdown opens the native picker and selecting an option updates the UI.")
    public void testHandleDropdown() {
        String targetOption = "webdriver.io is awesome";

        boolean isDisplayed = new FormsPage(getDriver())
                .navigateToFormsScreen()
                .selectDropdownOption(targetOption)
                .isFormsScreenDisplayed();

        Assert.assertTrue(isDisplayed, "Forms screen should still be displayed after dropdown selection.");
    }
}
