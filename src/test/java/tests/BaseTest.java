package tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import utils.ExtentReportManager;
import utils.Log;

public class BaseTest {

  protected WebDriver driver;

  protected static ExtentReports extentReports;

  protected ExtentTest extentTest;

  @BeforeSuite
  public void setupReport() {
    extentReports = ExtentReportManager.getReportInstance();
  }

  @AfterSuite
  public void teardownReport() {
    extentReports.flush();
  }

  @BeforeMethod
  @Parameters("browser")
  public void setUp(@Optional("chrome") String browser) {
    Log.info("Starting WebDriver...");
    if (browser.equalsIgnoreCase("chrome")) {
      ChromeOptions chromeOptions = new ChromeOptions();
      chromeOptions.addArguments("--disable-notifications");
      driver = new ChromeDriver(chromeOptions);
    } else if (browser.equalsIgnoreCase("firefox")) {
      FirefoxProfile profile = new FirefoxProfile();
      profile.setPreference("dom.webnotifications.enabled", false);
      FirefoxOptions firefoxOptions = new FirefoxOptions();
      firefoxOptions.setProfile(profile);
      driver = new FirefoxDriver(firefoxOptions);
    } else {
      throw new IllegalArgumentException("Browser type not supported");
    }
    driver.manage().window().maximize();
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    Log.info("Navigating to URL...");
    driver.get("https://useinsider.com/");
  }

  @AfterMethod
  public void tearDown(ITestResult result) {
    if (result.getStatus() == ITestResult.FAILURE) {
      String methodName = result.getMethod().getMethodName();
      String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
      String screenshotName = methodName + "_" + timestamp;
      String screenshotPath = ExtentReportManager.captureScreenshot(driver, screenshotName);
      extentTest.fail("Test Failed! Check Screenshot",
                      MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
    }
    if (driver != null) {
      Log.info("Closing Browser...");
      driver.quit();
    }
  }
}
