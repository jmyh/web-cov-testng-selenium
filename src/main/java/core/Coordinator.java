package core;

import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Coordinator {

    private static final Coordinator INSTANCE = new Coordinator();
    private static Map<String, ElementInfo> elementsMap = new HashMap<>();
    private static Map<Long, TestInfo> namesMap = new HashMap<>();
    private static Set<String> usedSelectorsInTests = new HashSet<>();

    public static Coordinator getInstance() {
        return INSTANCE;
    }

    private String createUniqueSelectorId(String testId, String selector) {
        return testId + selector;
    }
    public synchronized void incrementCounter(String selector) {
        String uniqueId = createUniqueSelectorId(getCurrentTestInfo().getId(), selector);
        elementsMap.putIfAbsent(selector, createNewElement(selector));
        ElementInfo oldInfo = elementsMap.get(selector);
        if (!usedSelectorsInTests.contains(uniqueId)) {
            oldInfo.incrementCounter();
            oldInfo.addTestInfo(getCurrentTestInfo());
            usedSelectorsInTests.add(uniqueId);
        }
    }

    public void write() {
        Gson gson = new Gson();
        try {
            Files.createDirectories(Paths.get("coverage"));
        } catch (IOException e) {
            throw new RuntimeException("Directory 'coverage' was not created!", e);
        }

        try (FileWriter writer = new FileWriter("coverage/coverage-info.json")){
            gson.toJson(elementsMap.values(), writer);
        } catch (IOException e) {
            throw new RuntimeException("File 'coverage/coverage-info.json' was not written!");
        }
    }

    private ElementInfo createNewElement(String selector) {
        return ElementInfo.builder().fullPath(selector).build();
    }

    public void setTestInfo(TestInfo testInfo) {
        namesMap.put(Thread.currentThread().getId(), testInfo);
    }

    private TestInfo getCurrentTestInfo() {
        return namesMap.get(Thread.currentThread().getId());
    }

}
