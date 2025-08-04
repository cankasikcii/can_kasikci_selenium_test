package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ExtentReportManager {

  private static ExtentReports extent;

  public static String reportPath;

  public static ExtentReports getReportInstance() {

    if (extent == null) {

      String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
      reportPath = "reports/ExtentReport_" + timestamp + ".html";
      ExtentSparkReporter reporter = new ExtentSparkReporter(reportPath);

      reporter.config().setDocumentTitle("Automation Test Report");
      reporter.config().setReportName("Test Execution Report");

      extent = new ExtentReports();
      extent.attachReporter(reporter);
    }

    return extent;
  }

  public static ExtentTest createTest(String testName) {
    return getReportInstance().createTest(testName);
  }

  public static String captureScreenshot(WebDriver driver, String screenshotName) {
    File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
    String path = System.getProperty("user.dir") + "/screenshots/" + screenshotName + ".png";

    try {
      FileUtils.copyFile(src, new File(path));
    } catch (IOException e) {
      Log.error("Screenshot couldn't be saved: " + e.getMessage());
      throw new RuntimeException("Failed to save screenshot to path: " + path, e);
    }
    return path;
  }
}









