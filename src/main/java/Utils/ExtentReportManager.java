package Utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentReportManager {

    private static ExtentReports extent;

    // One active test tracker (test) shared across all test scripts.
    public static ExtentTest test;
    private static String runFolderPath = null;

    public static ExtentReports getReportInstance() {
        // #1 test execution - extent == null; #2, #3,... extent won't be null - existing report is passed back
        if (extent == null) {
            String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());

            // File(s) will be stored inside reports folder.
            String reportFileName = "reports/ExtentReport_" + timeStamp + ".html";

            // Opens a new plain HTML file in the specified location with said file name.
            ExtentSparkReporter reporter = new ExtentSparkReporter(reportFileName);

            reporter.config().setDocumentTitle("Test Automation Report");

            //Report's main header inside HTML dashboard webpage.
            reporter.config().setReportName("QA Practice Site - Test Execution Report");

            // To turn on the main engine that listens to the test results (passed, failed, logs, screenshots).
            extent = new ExtentReports();

            // Connects main engine to the customized HTML layout (reporter).
            extent.attachReporter(reporter);
        }
        return extent;
    }

    public static ExtentTest createTest(String testName) { // Call this method whenever a new test case starts.
        test = getReportInstance().createTest(testName); /* Asks getReportInstance() to spin up a new tracker row inside
                                                            HTML file using whatever name passed in as testName. */
        return test;
    }

    public static void initializeRunFolder() {
        if (runFolderPath == null) {
            String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
            runFolderPath = "screenshots/Run_" + timestamp;
        }

        File dir = new File(runFolderPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    public static String captureScreenshot(WebDriver driver, String screenshotName) {
        if (driver == null) {
            return null;
        }

        if (runFolderPath == null) {
            initializeRunFolder();
        }
        String path = runFolderPath + "/" + screenshotName + ".png";

        try {
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(src, new File(path));
            return path;

        } catch (Exception e) {
            System.err.println("Failure to capture screenshot " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
