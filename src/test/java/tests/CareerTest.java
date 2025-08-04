package tests;

import org.testng.annotations.Test;
import pages.CareersPage;
import pages.HomePage;
import pages.QAPage;
import utils.ExtentReportManager;

import static org.testng.Assert.assertTrue;

public class CareerTest extends BaseTest {

  @Test
  public void testCareerPageFlow() throws InterruptedException {
    extentTest = ExtentReportManager.createTest("Career Page QA Jobs Filter Test");

    HomePage homePage = new HomePage(driver);
    homePage.acceptCookies();
    assertTrue(homePage.isHomePageDisplayed(), "Home Page not displayed");
    homePage.goToCareersPage();

    CareersPage careersPage = new CareersPage(driver);
    assertTrue(careersPage.isCareerPageDisplayed(), "Career page URL check failed.");
    assertTrue(careersPage.isTeamBlockVisible(), "Team block is not visible.");
    assertTrue(careersPage.isLifeAtInsiderVisible(), "Life at Insider block is not visible.");
    assertTrue(careersPage.isLocationBlockVisible(), "Location block is not visible.");

    driver.get("https://useinsider.com/careers/quality-assurance/");
    QAPage qaPage = new QAPage(driver);
    qaPage.clickSeeAllQAJobs();
    qaPage.isQaCareerPageDisplayed();
    qaPage.filterByLocation("Istanbul, Turkiye");
    qaPage.filterByDepartment("Quality Assurance");

    assertTrue(qaPage.isJobListDisplayed(), "Job list is not displayed.");
    assertTrue(qaPage.areAllJobsValid("Quality Assurance", "Quality Assurance", "Istanbul, Turkiye"),
               "Some jobs do not match the expected Department or Location.");

    qaPage.clickViewRoleAndVerifyLeverForm();
  }
}
