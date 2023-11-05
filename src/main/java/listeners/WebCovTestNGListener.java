package listeners;

import core.Coordinator;
import core.TestInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class WebCovTestNGListener implements ITestListener {

    final Logger logger = LoggerFactory.getLogger(WebCovSeleniumListener.class);

    private static final Coordinator coordinator = Coordinator.getInstance();
    @Override
    public void onTestStart(ITestResult result) {
        logger.debug("On test start: '"+result.getMethod().getDescription()+"'");
        coordinator.setTestInfo(new TestInfo(result.id(), result.getMethod().getDescription()));
        logger.debug("ID: "+result.id());
    }

}
