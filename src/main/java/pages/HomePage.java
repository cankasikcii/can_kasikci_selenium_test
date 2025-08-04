package pages;

import base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {

  @FindBy(xpath = "//a[contains(text(), 'Company')]")
  private WebElement companyMenu;

  @FindBy(xpath = "//a[text()='Careers']")
  private WebElement careersLink;

  @FindBy(id = "wt-cli-accept-all-btn")
  private WebElement acceptAllCookies;

  @FindBy(id = "cookie-law-info-bar")
  private WebElement cookieInfoBar;

  public HomePage(WebDriver driver) {
    super(driver);
  }

  public void acceptCookies() {
    waitAndClick(acceptAllCookies);
    waitUntilInvisible(cookieInfoBar);
  }

  public boolean isHomePageDisplayed() {
    return driver.getCurrentUrl().contains("useinsider.com") && driver.getTitle().toLowerCase().contains("insider");
  }

  public void goToCareersPage() {
    waitAndClick(companyMenu);
    actions.moveToElement(companyMenu).perform();
    waitAndClick(careersLink);
  }
}
