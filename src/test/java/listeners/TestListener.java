package listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestListener;
import org.testng.ITestResult;
import reports.ExtentReportManager;

/**
 * TestListener — TestNG listener that hooks into the test lifecycle.
 *
 * WHY A LISTENER:
 *   Listeners react to TestNG events without modifying test code.
 *   This one logs real-time test status to the console AND ExtentReport.
 *
 * Registration: Listed in testng.xml under <listeners> section.
 *
 * EVENTS HANDLED:
 *   onTestStart   → test is about to run
 *   onTestSuccess → test passed
 *   onTestFailure → test failed
 *   onTestSkipped → test was skipped (dependency failure, etc.)
 */
public class TestListener implements ITestListener {

    private static final Logger log = LoggerFactory.getLogger(TestListener.class);

    @Override
    public void onTestStart(ITestResult result) {
        log.info("▶️  STARTING: {}", result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        log.info("✅ PASSED:  {} ({}ms)", result.getName(),
                (result.getEndMillis() - result.getStartMillis()));
    }

    @Override
    public void onTestFailure(ITestResult result) {
        log.error("❌ FAILED:  {} — {}", result.getName(),
                result.getThrowable() != null ? result.getThrowable().getMessage() : "Unknown error");
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        log.warn("⏭️  SKIPPED: {}", result.getName());
    }
}
