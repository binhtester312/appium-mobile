package testcases.android.apilearning;

import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import utils.AppiumDriverEx;

public class HandleMultipleMatchedElements {

    public static void main(String[] args) {

        // Start an appium session
        AppiumDriver appiumDriver = AppiumDriverEx.getAppiumDriver();

        // Click on Login Label
        WebElement loginLabel = appiumDriver.findElement(AppiumBy.accessibilityId("Login"));
        loginLabel.click();

        // Explicit Wait: Wait up to 15 seconds until 2 elements matched with //*[@text='Login'] are present
        WebDriverWait wait = new WebDriverWait(appiumDriver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.numberOfElementsToBe(By.xpath("//*[@text='Login']"), 2));

        // Find elements those are matched //*[@text='Login']
        List<WebElement> loginTextElements = appiumDriver.findElements(By.xpath("//*[@text='Login']"));
        System.out.println("How many elements matched: " + loginTextElements.size());

        // Option A: Use findElement (returns the first matching element directly)
        // WebElement firstLoginTextElement =
        // appiumDriver.findElement(By.xpath("//*[@text='Login']"));
        // System.out.println("First login text element text: " +
        // firstLoginTextElement.getText());

        // Option B: Alternatively, get the first element from the list
        // WebElement secondLoginTextElement = loginTextElements.get(1);
        // System.out.println("Second login text element text: " +
        // secondLoginTextElement.getText());

        // Option C: Define index constants to make element list access readable
        final int LOGIN_TEXT_LOGIN_FORM_INDEX = 0;
        final int LOGIN_TEXT_MENU_INDEX = 1;
        loginTextElements.get(LOGIN_TEXT_LOGIN_FORM_INDEX).click();
        System.out.println("Login menu text: " + loginTextElements.get(LOGIN_TEXT_MENU_INDEX).getText());
    }
}
