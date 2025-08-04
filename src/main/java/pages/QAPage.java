package pages;

import base.BasePage;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class QAPage extends BasePage {

  @FindBy(xpath = "//a[text()='See all QA jobs']")
  private WebElement seeAllQAJobsButton;

  @FindBy(xpath = "//select[@id='filter-by-location']")
  private WebElement locationDropdown;

  @FindBy(xpath = "//select[@id='filter-by-department']")
  private WebElement departmentDropdown;

  @FindBy(css = ".position-list-item")
  List<WebElement> jobCards;

  @FindBy(id = "select2-filter-by-department-container")
  private WebElement qualityAssuranceTitle;

  @FindBy(xpath = "//section[@id='career-position-list']//div[@class='row']//div[1]//div[1]//a[1]")
  private WebElement viewRoleButton;

  public QAPage(WebDriver driver) {
    super(driver);
  }

  public void clickSeeAllQAJobs() {
    waitAndClick(seeAllQAJobsButton);
  }

  public boolean isQaCareerPageDisplayed() throws InterruptedException {
    boolean urlOk = driver.getCurrentUrl()
                          .contains("useinsider.com/careers/open-positions/?department=qualityassurance");
    waitUntilVisible(qualityAssuranceTitle);
    String titleText = qualityAssuranceTitle.getText().trim();
    boolean textOk = "Quality Assurance".equals(titleText);
    Thread.sleep(5000);

    return urlOk && textOk;
  }

  public void filterByLocation(String location) {
    waitUntilVisible(locationDropdown);
    waitUntilClickable(locationDropdown);
    Select select = new Select(locationDropdown);
    select.selectByVisibleText(location);
  }

  public void filterByDepartment(String department) throws InterruptedException {
    waitUntilVisible(departmentDropdown);
    waitUntilClickable(departmentDropdown);
    Select select = new Select(departmentDropdown);
    select.selectByVisibleText(department);
    Thread.sleep(5000);
  }

  public boolean isJobListDisplayed() {
    scrollToCenter(jobCards.getFirst().findElement(By.cssSelector(".position-title")));
    waitUntilVisible(jobCards);
    return true;
  }

  public boolean areAllJobsValid(String expectedPosition, String expectedDepartment, String expectedLocation) {
    waitUntilVisible(jobCards);

    for (WebElement card : jobCards) {
      String position = card.findElement(By.cssSelector(".position-title")).getText().trim();
      String department = card.findElement(By.cssSelector(".position-department")).getText().trim();
      String location = card.findElement(By.cssSelector(".position-location")).getText().trim();

      if (!containsIgnoreCase(position, expectedPosition) ||
          !containsIgnoreCase(department, expectedDepartment) ||
          !containsIgnoreCase(location, expectedLocation)) {
        return false;
      }
    }
    return true;
  }

  public void clickViewRoleAndVerifyLeverForm() {
    WebElement jobCard = jobCards.getFirst().findElement(By.cssSelector(".position-title"));
    hoverElement(jobCard);
    waitAndClick(viewRoleButton);

    String originalWindow = driver.getWindowHandle();
    for (String windowHandle : driver.getWindowHandles()) {
      if (!originalWindow.contentEquals(windowHandle)) {
        driver.switchTo().window(windowHandle);
        break;
      }
    }

    String currentUrl = driver.getCurrentUrl();
    if (!currentUrl.contains("jobs.lever.co")) {
      throw new AssertionError("Did not redirect to Lever application form. Current URL: " + currentUrl);
    }
  }
}
