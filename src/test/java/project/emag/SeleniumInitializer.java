package project.emag;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public final class SeleniumInitializer {

    private SeleniumInitializer() {}

    public static WebDriver getDriver() {
        System.setProperty("webdriver.chrome.driver", "/home/razvanpustea/chromedriver");
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        return driver;
    }

    public static void sleep() {
        try {
            Thread.sleep(1000L);
        }
        catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
