package pageObjects.android;

import commons.BasePage;
import io.appium.java_client.AppiumDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pageUIs.android.ProductsPageUI;

/**
 * ProductsPage — Page Object for the Catalog / Products screen.
 * Encapsulates all interactions and verification checks on this screen.
 */
public class ProductsPage extends BasePage {

    private static final Logger log = LoggerFactory.getLogger(ProductsPage.class);

    public ProductsPage(AppiumDriver driver) {
        super(driver);
        log.info("ProductsPage initialized.");
    }

    /**
     * Opens the side navigation menu drawer.
     */
    public void openMenu() {
        log.info("Opening side navigation menu.");
        sleep(1500);
        click(ProductsPageUI.MENU_BUTTON);
        sleep(1500);
    }

    /**
     * Clicks the Login menu item from the side drawer.
     */
    public void clickLoginMenuItem() {
        log.info("Clicking Login Menu Item.");
        sleep(1500);
        click(ProductsPageUI.MENU_LOGIN_ITEM);
        sleep(1500);
    }

    /**
     * Checks if the app header logo or Products title is displayed.
     *
     * @return true if logo or title is present
     */
    public boolean isAppLogoDisplayed() {
        log.info("Checking if App Logo / Products title is displayed.");
        sleep(1000);
        return isElementPresent(ProductsPageUI.APP_LOGO_HEADER) || isElementPresent(ProductsPageUI.PRODUCTS_TITLE);
    }

    /**
     * Checks if the menu button (hamburger icon) is visible.
     *
     * @return true if menu button is present
     */
    public boolean isMenuButtonDisplayed() {
        log.info("Checking if side menu button is displayed.");
        return isElementPresent(ProductsPageUI.MENU_BUTTON);
    }

    /**
     * Opens the side menu and checks if the Login Menu Item is displayed.
     *
     * @return true if Login Menu Item is present
     */
    public boolean isLoginMenuItemDisplayed() {
        openMenu();
        log.info("Checking if Login Menu Item is displayed.");
        sleep(1500);
        return isElementPresent(ProductsPageUI.MENU_LOGIN_ITEM);
    }
}
