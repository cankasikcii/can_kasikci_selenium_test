package pages;

import base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CareersPage extends BasePage {

  @FindBy(xpath = "//h3[normalize-space()='Find your calling']")
  private WebElement teamBlock;

  @FindBy(xpath = "//h2[text()='Life at Insider']")
  private WebElement lifeAtInsiderBlock;

  @FindBy(xpath = "//h3[normalize-space()='Our Locations']")
  private WebElement locationBlock;

  public CareersPage(WebDriver driver) {
    super(driver);
  }

  public boolean isCareerPageDisplayed() {
    return driver.getCurrentUrl().contains("useinsider.com/careers");
  }

  public boolean isTeamBlockVisible() {
    return isElementDisplayed(teamBlock);
  }

  public boolean isLifeAtInsiderVisible() {
    return isElementDisplayed(lifeAtInsiderBlock);
  }

  public boolean isLocationBlockVisible() {
    return isElementDisplayed(locationBlock);
  }
}
