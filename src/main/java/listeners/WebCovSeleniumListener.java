package listeners;

import core.Coordinator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverListener;

import java.util.List;

public class WebCovSeleniumListener implements WebDriverListener {
    final Logger logger = LogManager.getLogger();
    private static Coordinator coordinator = new Coordinator();

    private String byToSelector(By by) {
        String byStr = by.toString();
        return byStr.substring(byStr.indexOf(":")+1).trim();
    }

    private String elementToSelector(WebElement element) {
        String tmpStr = element.toString().split("->")[1].replaceFirst("(?s)(.*)\\]", "$1" + "");
        return tmpStr.substring(tmpStr.indexOf(':') + 1).trim();
    }

    @Override
    public void afterGet(WebDriver driver, String url) {
        logger.debug("After get: " + url);
    }

    @Override
    public void afterFindElement(WebDriver driver, By locator, WebElement result) {
        logger.debug("After find element: " + byToSelector(locator));
        coordinator.incrementCounter(byToSelector(locator));
    }

    @Override
    public void afterFindElements(WebDriver driver, By locator, List<WebElement> result) {
        logger.debug("After find elements: " + byToSelector(locator));
        coordinator.incrementCounter(byToSelector(locator));
    }

    @Override
    public void afterClick(WebElement element) {
        logger.debug("After click: '" + elementToSelector(element)+"'");
        coordinator.incrementCounter(elementToSelector(element));
    }

    @Override
    public void afterSendKeys(WebElement element, CharSequence... keysToSend) {
        logger.debug("After send keys: '" + elementToSelector(element)+"'");
        coordinator.incrementCounter(elementToSelector(element));
    }

    @Override
    public void afterFindElement(WebElement element, By locator, WebElement result) {
        logger.debug("After find element 2: " + byToSelector(locator));
        coordinator.incrementCounter(byToSelector(locator));
    }

    @Override
    public void afterFindElements(WebElement element, By locator, List<WebElement> result) {
        logger.debug("After find elements 2: " + byToSelector(locator));
        coordinator.incrementCounter(byToSelector(locator));
    }
}
