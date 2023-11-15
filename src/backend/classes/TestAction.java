package backend.classes;

import org.openqa.selenium.WebDriver;

public abstract class TestAction {
    WebDriver driver;

    abstract void execute();

    public void setDriver(WebDriver driver) {
      this.driver = driver;
    }

    public WebDriver getDriver() {
      return this.driver;
    }
}
