package selenium;

import listeners.CoverageWriter;
import listeners.WebCovSeleniumListener;
import listeners.WebCovTestNGListener;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.time.Duration;

import static org.testng.Assert.assertEquals;

@Listeners({WebCovTestNGListener.class, CoverageWriter.class})
public class SeleniumTest {

    private WebDriver driver;

    @BeforeClass
    public void setUp() {
        WebDriver chromeDriver = new ChromeDriver();
        WebCovSeleniumListener listener = new WebCovSeleniumListener();
        EventFiringDecorator<WebDriver> decorator = new EventFiringDecorator<>(listener); //Pass listener to constructor

        driver = decorator.decorate(chromeDriver);
        driver.get("https://tmpout.sh/");
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
    }

    @Test(description = "Test description")
    public void seleniumTest() {
        WebElement zingLink = driver.findElement(By.cssSelector("pre a:nth-child(1)"));
        zingLink.click();

        WebElement volume1Btn = driver.findElement(By.cssSelector("pre a:nth-child(1)"));
        String volumeTitle = volume1Btn.getText();
        assertEquals(volumeTitle, "┌─VOLUME 1 - APRIL 2021─┐");
        volume1Btn.click();

        WebElement sixthChapter = driver.findElement(By.cssSelector("pre a:nth-child(6)"));
        String value = sixthChapter.getText();
        assertEquals(value, "Fuzzing Radare2 For 0days In About 30 Lines Of Code");
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
