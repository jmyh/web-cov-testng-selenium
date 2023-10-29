package core;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Builder
@Getter
@Setter
public class ElementInfo {

    private String fullPath;

    @Setter(value = AccessLevel.NONE)
    private Map<String, String> testNames;

    private int count;

    public void incrementCounter() {
        count++;
    }

    public void addTestInfo(TestInfo testInfo) {
        if (testNames == null)
            testNames = new HashMap<>();
        testNames.putIfAbsent(testInfo.getId(), testInfo.getName());
    }
}
