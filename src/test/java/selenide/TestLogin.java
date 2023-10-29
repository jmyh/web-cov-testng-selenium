package selenide;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import listeners.CoverageWriter;
import listeners.WebCovSeleniumListener;
import listeners.WebCovTestNGListener;
import org.openqa.selenium.By;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

@Listeners({WebCovTestNGListener.class, CoverageWriter.class})
public class TestLogin {

    private String login = "standard_user";
    private String invalidLogin = "locked_out_user";
    private String password = "secret_sauce";

    @BeforeClass
    public void setUp() {
        Configuration.browser="chrome";
        Configuration.baseUrl="https://www.saucedemo.com";
        WebDriverRunner.addListener(new WebCovSeleniumListener());
        open("/");
    }

    @Test(description = "Failed login")
    public void testFailedLogin() {
        $(By.cssSelector("#user-name")).should(exist);

        $(By.cssSelector("#user-name")).sendKeys(invalidLogin);
        $(By.cssSelector("#password")).sendKeys(password);
        $(By.cssSelector("#login-button")).click();

        $(By.cssSelector(".error-message-container")).shouldHave(exactText("Epic sadface: Sorry, this user has been locked out."));
        $(By.cssSelector("#user-name+svg")).should(exist);
        $(By.cssSelector("#password+svg")).should(exist);

    }

    @Test(description = "Success login test", priority = 1)
    public void testLogin() {
        $(By.cssSelector("#user-name")).should(exist);

        $(By.cssSelector("#user-name")).clear();
        $(By.cssSelector("#user-name")).sendKeys(login);
        $(By.cssSelector("#password")).clear();
        $(By.cssSelector("#password")).sendKeys(password);
        $(By.cssSelector("#login-button")).click();

        $(By.cssSelector(".inventory_list")).should(exist);
    }
}
