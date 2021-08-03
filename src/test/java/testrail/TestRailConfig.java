package testrail;

import com.codepine.api.testrail.TestRail;
import com.codepine.api.testrail.model.*;
import config.InitialConfig;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class TestRailConfig {
    private static List<ResultField> customResultFields;
    private static Run run;
    private static TestRail testRail;
    private static Suite suite;
    private static TestRail.Results testRailResults;
    private static Map<String, Case> testCases = new HashMap<>(10);
    private static Project project;


    public static void initialize(){
        testRail = TestRail.builder(InitialConfig.getDomainTestRail(),
                InitialConfig.getUserNameTR(),InitialConfig.getPasswordTR()).applicationName("Cucum-mkmytr").build();
        project = testRail.projects().add(new Project().setId(4).setName("Project-fin")).execute();
        suite = testRail.suites().add(project.getId(), new Suite()
//                .setId(Integer.parseInt(InitialConfig.getSuiteIdTestRail()))
                .setName("Make My trip tests")).execute();
        Section section = testRail.sections().add(project.getId(),
                new Section().setSuiteId(suite.getId()).setName("All cases")).execute();
        List<CaseField> customCaseFields = testRail.caseFields().list().execute();

        Case defaultCitiesCase = testRail.cases().add(section.getId(), new Case()
                .setTitle(InitialConfig.getDefaultCities()), customCaseFields).execute();
        Case prevSelCitiesCase = testRail.cases().add(section.getId(), new Case()
                .setTitle(InitialConfig.getPrevSelCities()), customCaseFields).execute();
        Case roundTripWorkFlowCase = testRail.cases().add(section.getId(), new Case()
                .setTitle(InitialConfig.getRoundTripWorkFlow()), customCaseFields).execute();
        run = testRail.runs().add(project.getId(), new Run().setSuiteId(
                suite.getId()).setName("Weekly regression: "+ LocalDateTime.now())).execute();
        testCases.put(defaultCitiesCase.getTitle(), defaultCitiesCase);
        testCases.put(prevSelCitiesCase.getTitle(), prevSelCitiesCase);
        testCases.put(roundTripWorkFlowCase.getTitle(), roundTripWorkFlowCase);

        testRailResults = testRail.results();
        customResultFields = testRail.resultFields().list().execute();
    }
    public static void addTestResult(String testName, boolean result){
        int statusId = result ? 1 : 5;
        Result testResult = new Result();
        testRailResults.addForCase(run.getId(), testCases.get(testName).getId(),
                testResult.setStatusId(statusId), customResultFields).execute();

    }
    public static void finishUp(){
        System.out.println("Finished");
        testRail.runs().close(run.getId()).execute();
        testRail.projects().update(project.setCompleted(true)).execute();
    }
}
