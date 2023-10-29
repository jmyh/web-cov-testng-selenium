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
public class TestAddToCart {

    private void login() {
        $(By.cssSelector("#user-name")).should(exist);

        $(By.cssSelector("#user-name")).clear();
        $(By.cssSelector("#user-name")).sendKeys("standard_user");
        $(By.cssSelector("#password")).clear();
        $(By.cssSelector("#password")).sendKeys("secret_sauce");
        $(By.cssSelector("#login-button")).click();

        $(By.cssSelector(".inventory_list")).should(exist);
    }

    @BeforeClass
    public void setUp() {
        Configuration.browser="chrome";
        Configuration.baseUrl="https://www.saucedemo.com";
        WebDriverRunner.addListener(new WebCovSeleniumListener());
        open("/");
        login();
    }

    @Test(description = "Check init cart counter")
    public void testCheckInitCartCounter() {
        $(".shopping_cart_badge").shouldNot(exist);
    }

    @Test(description = "Check init status for the backpack")
    public void testCheckInitStatusForBackpack() {
        $("#add-to-cart-sauce-labs-backpack").should(exist);
        $("#add-to-cart-sauce-labs-backpack").shouldHave(exactText("Add to cart"));
        $("#add-to-cart-sauce-labs-backpack").shouldHave(cssValue("color", "rgba(19, 35, 34, 1)"));
    }

    @Test(description = "Add backpack to the cart", priority = 1)
    public void testAddBackpackToCart() {
        $("#add-to-cart-sauce-labs-backpack").click();
        $("#add-to-cart-sauce-labs-backpack").should(disappear);

        $("#remove-sauce-labs-backpack").should(appear);
        $("#remove-sauce-labs-backpack").shouldHave(exactText("Remove"));
        $("#remove-sauce-labs-backpack").shouldHave(cssValue("color","rgba(226, 35, 26, 1)"));

        $(".shopping_cart_badge").should(appear);
        $(".shopping_cart_badge").shouldHave(exactText("1"));
    }

    @Test(description = "Remove backpack from the cart", dependsOnMethods = "testAddBackpackToCart", priority = 1)
    public void testRemoveBackpackToCart() {
        $("#remove-sauce-labs-backpack").click();
        $("#remove-sauce-labs-backpack").should(disappear);

        $("#add-to-cart-sauce-labs-backpack").should(appear);
        $("#add-to-cart-sauce-labs-backpack").shouldHave(exactText("Add to cart"));
        $("#add-to-cart-sauce-labs-backpack").shouldHave(cssValue("color", "rgba(19, 35, 34, 1)"));

        $(".shopping_cart_badge").should(disappear);
    }
}
