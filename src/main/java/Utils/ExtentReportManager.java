package Utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentReportManager {

    private static ExtentReports extent; // One single global report file (extent).
    private static ExtentTest test; // One active test tracker (test) shared across all test scripts.

    public static ExtentReports getReportInstance() {
        if (extent == null) { // #1 test execution - extent == null; #2, #3,... extent won't be null - existing report is passed back
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String reportFileName = "reports/ExtentReport_" + timeStamp + ".html"; // File(s) will be stored inside reports folder.
            ExtentSparkReporter reporter = new ExtentSparkReporter(reportFileName); // Opens a new plain HTML file in the specified location with said file name.
            reporter.config().setDocumentTitle("Test Automation Report"); //Browser's tab title.
            reporter.config().setReportName("QA Practice Site - Test Execution Report"); //Report's main header inside HTML dashboard webpage.
            extent = new ExtentReports(); // To turn on the main engine that listens to the test results (passed, failed, logs, screenshots).
            extent.attachReporter(reporter); // Connects main engine to the customized HTML layout (reporter).
        }
        return extent;
    }

    public static ExtentTest createTest(String testName) { // Call this method whenever a new test case starts.
        test = getReportInstance().createTest(testName); /* Asks getReportInstance() to spin up a new tracker row inside
                                                            HTML file using whatever name passed in as testName. */
        return test;
    }

}
