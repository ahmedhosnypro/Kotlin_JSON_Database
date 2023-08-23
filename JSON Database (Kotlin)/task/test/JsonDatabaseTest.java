import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;

public class JsonDatabaseTest extends StageTest<String> {

    private static final String OK_STATUS = "OK";
    private static final String ERROR_STATUS = "ERROR";

    private static final String WRONG_EXIT = "The program should stop when 'exit' was entered.";
    private static final String WRONG_GET_EMPTY_CELL_WITH_ERROR = "When a user tries to get an empty" +
            " cell from the database you should print '" + ERROR_STATUS + "'.";
    private static final String WRONG_SET_VALUE_TO_CELL_WITH_OK = "When a user tries to set a value to" +
            " a cell you should save the value and print '" + OK_STATUS + "'.";
    private static final String WRONG_GET_VALUE = "When a user tries to get a not empty cell you program prints " +
            "wrong value.\nMay be the problem is in processing 'set' action:\nIf the specified cell already contains " +
            "information, you should simply rewrite it.";
    private static final String WRONG_DELETE = "When a user tries to delete a value from the cell you should assign " +
            "an empty string to this cell and print 'OK'.";
    private static final String WRONG_DELETE_EMPTY = "When a user tries to delete a cell with an empty value" +
            " you should assign an empty string to this cell and print 'OK'.";
    private static final String WRONG_DELETE_INDEX_OUT_OF_BOUNDS = "When a user tries to delete a cell which index " +
            "is less than 0 or greater than 100 you should print 'ERROR'.";

    @DynamicTest(order = 1)
    CheckResult checkExit() {

        System.out.println("Here");

        TestedProgram program = new TestedProgram("jsondatabase.server");
        program.start();
        program.execute("exit");

        if (!program.isFinished()) {
            return CheckResult.wrong(WRONG_EXIT);
        }

        return CheckResult.correct();
    }

    @DynamicTest(order = 2)
    CheckResult testInputs() {

        TestedProgram program = new TestedProgram("jsondatabase.server");
        program.start();

        String output;
        String expectedValue;

        output = program.execute("get 1");
        if (!output.toUpperCase().contains(ERROR_STATUS)) {
            return CheckResult.wrong(WRONG_GET_EMPTY_CELL_WITH_ERROR);
        }

        output = program.execute("set 1 Hello world!");
        if (!output.toUpperCase().contains(OK_STATUS)) {
            return CheckResult.wrong(WRONG_SET_VALUE_TO_CELL_WITH_OK);
        }

        output = program.execute("set 1 HelloWorld!");
        if (!output.toUpperCase().contains(OK_STATUS)) {
            return CheckResult.wrong(WRONG_SET_VALUE_TO_CELL_WITH_OK);
        }

        output = program.execute("get 1");
        expectedValue = "HelloWorld!";
        if (!output.contains(expectedValue)) {
            return CheckResult.wrong(WRONG_GET_VALUE +
                    "\nExpected:\n" + expectedValue + "\nYour output:\n" + output);
        }

        output = program.execute("delete 1");
        if (!output.toUpperCase().contains(OK_STATUS)) {
            return CheckResult.wrong(WRONG_DELETE);
        }

        output = program.execute("delete 1");
        if (!output.toUpperCase().contains(OK_STATUS)) {
            return CheckResult.wrong(WRONG_DELETE_EMPTY);
        }

        output = program.execute("get 1");
        if (!output.toUpperCase().contains(ERROR_STATUS)) {
            return CheckResult.wrong(WRONG_GET_EMPTY_CELL_WITH_ERROR + "\nMay be after deleting a cell you didn't " +
                    "assign an empty value to it.");
        }

        output = program.execute("set 55 Some text here");
        if (!output.toUpperCase().contains(OK_STATUS)) {
            return CheckResult.wrong(WRONG_SET_VALUE_TO_CELL_WITH_OK);
        }

        output = program.execute("get 55");
        expectedValue = "Some text here";
        if (!output.contains(expectedValue)) {
            return CheckResult.wrong(WRONG_GET_VALUE +
                    "\nExpected:\n" + expectedValue + "\nYour output:\n" + output);
        }

        output = program.execute("get 56");
        if (!output.toUpperCase().contains(ERROR_STATUS)) {
            return CheckResult.wrong(WRONG_GET_EMPTY_CELL_WITH_ERROR);
        }

        output = program.execute("delete 55");
        if (!output.toUpperCase().contains(OK_STATUS)) {
            return CheckResult.wrong(WRONG_DELETE);
        }

        output = program.execute("delete 56");
        if (!output.toUpperCase().contains(OK_STATUS)) {
            return CheckResult.wrong(WRONG_DELETE_EMPTY);
        }

        output = program.execute("delete 100");
        if (!output.toUpperCase().contains(OK_STATUS)) {
            return CheckResult.wrong(WRONG_DELETE_EMPTY);
        }

        output = program.execute("delete 101");
        if (!output.toUpperCase().contains(ERROR_STATUS)) {
            return CheckResult.wrong(WRONG_DELETE_INDEX_OUT_OF_BOUNDS);
        }

        program.execute("exit");

        return CheckResult.correct();
    }
}
