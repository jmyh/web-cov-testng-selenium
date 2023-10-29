package listeners;

import core.Coordinator;
import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.xml.XmlSuite;

import java.util.List;

public class CoverageWriter implements IReporter {

    @Override
    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
        IReporter.super.generateReport(xmlSuites, suites, outputDirectory);
        Coordinator coordinator = Coordinator.getInstance();
        coordinator.write();
    }
}
