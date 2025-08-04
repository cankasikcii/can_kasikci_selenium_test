package base;

import java.time.Duration;
import java.util.List;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {

  protected WebDriver driver;

  protected WebDriverWait wait;

  protected Actions actions;

  public BasePage(WebDriver driver) {
    this.driver = driver;
    this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    this.actions = new Actions(driver);
    PageFactory.initElements(driver, this);
  }

  public void waitUntilClickable(WebElement element) {
    wait.until(ExpectedConditions.elementToBeClickable(element));
  }

  protected void waitAndClick(WebElement element) {
    wait.until(ExpectedConditions.elementToBeClickable(element)).click();
  }

  protected void waitUntilInvisible(WebElement element) {
    wait.until(ExpectedConditions.invisibilityOf(element));
  }

  protected void waitUntilVisible(WebElement element) {
    wait.until(ExpectedConditions.visibilityOf(element));
  }

  protected void waitUntilVisible(List<WebElement> elements) {
    wait.until(ExpectedConditions.visibilityOfAllElements(elements));
  }

  public void scrollToCenter(WebElement element) {
    ((JavascriptExecutor) driver).executeScript(
        "const el = arguments[0];" +
        "const rect = el.getBoundingClientRect();" +
        "window.scrollBy({ top: rect.top - (window.innerHeight / 2) + (rect.height / 2), behavior: 'smooth' });",
        element
                                               );
  }

  protected void hoverElement(WebElement element) {
    Actions actions = new Actions(driver);
    actions.moveToElement(element).perform();
  }

  protected boolean isElementDisplayed(WebElement element) {
    try {
      waitUntilVisible(element);
      return element.isDisplayed();
    } catch (TimeoutException e) {
      return false;
    }
  }

  protected static boolean containsIgnoreCase(String text, String keyword) {
    return text.toLowerCase().contains(keyword.toLowerCase());
  }
}