package utils;

import org.openqa.selenium.remote.CapabilityType;

public interface MobileCababilityTypeEx extends CapabilityType {

    String APP_PACKAGE = "appPackage";
    String APP_ACTIVITY = "appActivity";
    String APP_PATH = "app";
    String APP_UDID = "udid";
    String APP_PLATFORM_NAME = "platformName";
    String APP_AUTOMATION_NAME = "automationName";

}