package reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utilities.ConfigReader;

import java.io.File;

/**
 * ExtentReportManager — Manages the ExtentReports HTML test report lifecycle.
 *
 * DESIGN: ThreadLocal<ExtentTest> for parallel-safe test logging.
 * Each test thread writes to its own ExtentTest node in the shared report.
 *
 * LIFECYCLE:
 *   1. initReport()   → @BeforeSuite: creates the HTML file
 *   2. createTest()   → @BeforeMethod: creates a node per test
 *   3. logPass/Fail() → @Test: records result
 *   4. flushReport()  → @AfterSuite: saves & closes the file
 */
public class ExtentReportManager {

    private static final Logger log = LoggerFactory.getLogger(ExtentReportManager.class);

    /** Single shared ExtentReports instance for the entire suite */
    private static ExtentReports extent;

    /** One ExtentTest per thread — enables safe parallel test logging */
    private static final ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    // Prevent instantiation
    private ExtentReportManager() {}

    /**
     * Initializes the ExtentReports instance and configures the HTML reporter.
     * Must be called once in @BeforeSuite.
     */
    public static synchronized void initReport() {
        ConfigReader config = ConfigReader.getInstance();
        String reportPath = config.get("extent.report.path", "test-output/ExtentReport.html");

        // Ensure report directory exists
        new File(reportPath).getParentFile().mkdirs();

        // ExtentSparkReporter = generates the HTML report file
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
        sparkReporter.config().setTheme(Theme.DARK);
        sparkReporter.config().setDocumentTitle(
                config.get("report.title", "Appium Android Test Report")
        );
        sparkReporter.config().setReportName(
                config.get("report.name", "Android Automation Suite")
        );
        sparkReporter.config().setEncoding("UTF-8");
        sparkReporter.config().setTimeStampFormat("yyyy-MM-dd HH:mm:ss");

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);

        // Add system info to the report dashboard
        extent.setSystemInfo("OS", System.getProperty("os.name") + " " + System.getProperty("os.arch"));
        extent.setSystemInfo("Java", System.getProperty("java.version"));
        extent.setSystemInfo("Framework", "Appium 3 + TestNG + Java 17");
        extent.setSystemInfo("Platform", config.get("platform.name", "Android"));
        extent.setSystemInfo("Device", config.get("device.name", "N/A"));
        extent.setSystemInfo("Android Version", config.get("platform.version", "N/A"));

        log.info("ExtentReport initialized at: {}", reportPath);
    }

    /**
     * Creates a new test node in the report.
     * Must be called in @BeforeMethod.
     *
     * @param testName Name of the test method
     */
    public static void createTest(String testName) {
        if (extent == null) {
            log.warn("ExtentReports not initialized! Call initReport() first.");
            return;
        }
        ExtentTest test = extent.createTest(testName);
        extentTest.set(test);
    }

    /**
     * Logs a PASS result for the current test.
     *
     * @param message Success message to display in the report
     */
    public static void logPass(String message) {
        ExtentTest test = extentTest.get();
        if (test != null) {
            test.pass(message);
        }
    }

    /**
     * Logs a FAIL result with the exception stacktrace and screenshot.
     *
     * @param throwable      The exception that caused the failure
     * @param screenshotPath Absolute path to the failure screenshot
     */
    public static void logFail(Throwable throwable, String screenshotPath) {
        ExtentTest test = extentTest.get();
        if (test == null) return;

        test.fail(throwable);

        // Attach screenshot if available
        if (screenshotPath != null && !screenshotPath.isEmpty()) {
            try {
                test.fail("Screenshot on failure:",
                        MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
            } catch (Exception e) {
                log.warn("Could not attach screenshot to report: {}", e.getMessage());
            }
        }
    }

    /**
     * Logs a SKIP result.
     *
     * @param reason Reason for the test being skipped
     */
    public static void logSkip(String reason) {
        ExtentTest test = extentTest.get();
        if (test != null) {
            test.skip(reason);
        }
    }

    /**
     * Logs an informational step inside the current test.
     * Use this to record important actions during a test.
     *
     * @param message Step description
     */
    public static void logInfo(String message) {
        ExtentTest test = extentTest.get();
        if (test != null) {
            test.info(message);
        }
    }

    /**
     * Flushes all pending data and saves the HTML report to disk.
     * Must be called in @AfterSuite.
     */
    public static synchronized void flushReport() {
        if (extent != null) {
            extent.flush();
            log.info("ExtentReport flushed and saved.");
        }
    }

    /**
     * Returns the ExtentTest for the current thread.
     * Use when you want to log steps from Page Object classes.
     *
     * @return Current thread's ExtentTest, or null if not initialized
     */
    public static ExtentTest getTest() {
        return extentTest.get();
    }
}
