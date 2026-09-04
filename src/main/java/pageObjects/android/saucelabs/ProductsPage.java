package pageObjects.android.saucelabs;

import commons.BasePage;
import io.appium.java_client.AppiumDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pageUIs.android.saucelabs.ProductsPageUI;

/**
 * ProductsPage — Page Object for Sauce Labs Demo App Catalog / Products screen.
 */
public class ProductsPage extends BasePage {

    private static final Logger log = LoggerFactory.getLogger(ProductsPage.class);

    public ProductsPage(AppiumDriver driver) {
        super(driver);
    }

    public void openMenu() {
        log.info("Opening side navigation menu.");
        click(ProductsPageUI.MENU_BUTTON);
    }

    public void clickLoginMenuItem() {
        log.info("Clicking Login Menu Item.");
        click(ProductsPageUI.MENU_LOGIN_ITEM);
    }

    public boolean isAppLogoDisplayed() {
        return isElementPresent(ProductsPageUI.APP_LOGO_HEADER) || isElementPresent(ProductsPageUI.PRODUCTS_TITLE);
    }

    public boolean isMenuButtonDisplayed() {
        return isElementPresent(ProductsPageUI.MENU_BUTTON);
    }

    public boolean isLoginMenuItemDisplayed() {
        openMenu();
        return isElementPresent(ProductsPageUI.MENU_LOGIN_ITEM);
    }
}
