package seedu.opus.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.opus.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.opus.commons.core.Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
import static seedu.opus.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.google.common.eventbus.Subscribe;

import seedu.opus.commons.core.EventsCenter;
import seedu.opus.commons.events.model.TaskManagerChangedEvent;
import seedu.opus.commons.events.ui.JumpToListRequestEvent;
import seedu.opus.commons.events.ui.ShowHelpRequestEvent;
import seedu.opus.logic.commands.AddCommand;
import seedu.opus.logic.commands.ClearCommand;
import seedu.opus.logic.commands.Command;
import seedu.opus.logic.commands.CommandResult;
import seedu.opus.logic.commands.DeleteCommand;
import seedu.opus.logic.commands.EditCommand;
import seedu.opus.logic.commands.ExitCommand;
import seedu.opus.logic.commands.FindCommand;
import seedu.opus.logic.commands.HelpCommand;
import seedu.opus.logic.commands.ListCommand;
import seedu.opus.logic.commands.MarkCommand;
import seedu.opus.logic.commands.RedoCommand;
import seedu.opus.logic.commands.SelectCommand;
import seedu.opus.logic.commands.SortCommand;
import seedu.opus.logic.commands.UndoCommand;
import seedu.opus.logic.commands.exceptions.CommandException;
import seedu.opus.model.History;
import seedu.opus.model.Model;
import seedu.opus.model.ModelManager;
import seedu.opus.model.ReadOnlyTaskManager;
import seedu.opus.model.TaskManager;
import seedu.opus.model.tag.Tag;
import seedu.opus.model.tag.UniqueTagList;
import seedu.opus.model.task.DateTime;
import seedu.opus.model.task.Name;
import seedu.opus.model.task.Note;
import seedu.opus.model.task.Priority;
import seedu.opus.model.task.ReadOnlyTask;
import seedu.opus.model.task.Status;
import seedu.opus.model.task.Task;
import seedu.opus.storage.StorageManager;


public class LogicManagerTest {

    /**
     * See https://github.com/junit-team/junit4/wiki/rules#temporaryfolder-rule
     */
    @Rule
    public TemporaryFolder saveFolder = new TemporaryFolder();

    private Model model;
    private Logic logic;

    //These are for checking the correctness of the events raised
    private ReadOnlyTaskManager latestSavedTaskManager;
    private boolean helpShown;
    private int targetedJumpIndex;

    @Subscribe
    private void handleLocalModelChangedEvent(TaskManagerChangedEvent tmce) {
        latestSavedTaskManager = new TaskManager(tmce.data);
    }

    @Subscribe
    private void handleShowHelpRequestEvent(ShowHelpRequestEvent she) {
        helpShown = true;
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent je) {
        targetedJumpIndex = je.targetIndex;
    }

    @Before
    public void setUp() {
        model = new ModelManager();
        String tempTaskManagerFile = saveFolder.getRoot().getPath() + "TempTaskManager.xml";
        String tempPreferencesFile = saveFolder.getRoot().getPath() + "TempPreferences.json";
        logic = new LogicManager(model, new StorageManager(tempTaskManagerFile, tempPreferencesFile));
        EventsCenter.getInstance().registerHandler(this);

        latestSavedTaskManager = new TaskManager(model.getTaskManager()); // last saved assumed to be up to date
        helpShown = false;
        targetedJumpIndex = -1; // non yet
    }

    @After
    public void tearDown() {
        EventsCenter.clearSubscribers();
    }

    @Test
    public void execute_invalid() {
        String invalidCommand = "       ";
        assertCommandFailure(invalidCommand, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
    }

    /**
     * Executes the command, confirms that a CommandException is not thrown and that the result message is correct.
     * Also confirms that both the 'task manager' and the 'last shown list' are as specified.
     * @see #assertCommandBehavior(boolean, String, String, ReadOnlyTaskManager, List)
     */
    private void assertCommandSuccess(String inputCommand, String expectedMessage,
                                      ReadOnlyTaskManager expectedTaskManager,
                                      List<? extends ReadOnlyTask> expectedShownList) {
        assertCommandBehavior(false, inputCommand, expectedMessage, expectedTaskManager, expectedShownList);
    }

    /**
     * Executes the command, confirms that a CommandException is thrown and that the result message is correct.
     * Both the 'task manager' and the 'last shown list' are verified to be unchanged.
     * @see #assertCommandBehavior(boolean, String, String, ReadOnlyTaskManager, List)
     */
    private void assertCommandFailure(String inputCommand, String expectedMessage) {
        TaskManager expectedTaskManager = new TaskManager(model.getTaskManager());
        List<ReadOnlyTask> expectedShownList = new ArrayList<>(model.getFilteredTaskList());
        assertCommandBehavior(true, inputCommand, expectedMessage, expectedTaskManager, expectedShownList);
    }

    /**
     * Executes the command, confirms that the result message is correct
     * and that a CommandException is thrown if expected
     * and also confirms that the following three parts of the LogicManager object's state are as expected:<br>
     *      - the internal task manager data are same as those in the {@code expectedTaskManager} <br>
     *      - the backing list shown by UI matches the {@code shownList} <br>
     *      - {@code expectedTaskManager} was saved to the storage file. <br>
     */
    private void assertCommandBehavior(boolean isCommandExceptionExpected, String inputCommand, String expectedMessage,
                                       ReadOnlyTaskManager expectedTaskManager,
                                       List<? extends ReadOnlyTask> expectedShownList) {

        try {
            CommandResult result = logic.execute(inputCommand);
            assertFalse("CommandException expected but was not thrown.", isCommandExceptionExpected);
            assertEquals(expectedMessage, result.feedbackToUser);
        } catch (CommandException e) {
            assertTrue("CommandException not expected but was thrown.", isCommandExceptionExpected);
            assertEquals(expectedMessage, e.getMessage());
        }

        //Confirm the ui display elements should contain the right data
        assertEquals(expectedShownList, model.getFilteredTaskList());

        //Confirm the state of data (saved and in-memory) is as expected
        assertEquals(expectedTaskManager, model.getTaskManager());
        if (!inputCommand.contains("sort")) {
            assertEquals(expectedTaskManager, latestSavedTaskManager);
        }
    }

    @Test
    public void execute_unknownCommandWord() {
        String unknownCommand = "uicfhmowqewca";
        assertCommandFailure(unknownCommand, MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void execute_help() {
        assertCommandSuccess("help", HelpCommand.SHOWING_HELP_MESSAGE, new TaskManager(), Collections.emptyList());
        assertTrue(helpShown);
    }

    @Test
    public void execute_exit() {
        assertCommandSuccess("exit", ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT,
                new TaskManager(), Collections.emptyList());
    }

    @Test
    public void execute_clear() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        model.addTask(helper.generateTask(1));
        model.addTask(helper.generateTask(2));
        model.addTask(helper.generateTask(3));

        assertCommandSuccess("clear", ClearCommand.MESSAGE_SUCCESS, new TaskManager(), Collections.emptyList());
    }

    @Test
    public void execute_add_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.adam();
        TaskManager expectedTaskManager = new TaskManager();
        expectedTaskManager.addTask(toBeAdded);

        // execute command and verify result
        assertCommandSuccess(helper.generateAddCommand(toBeAdded),
                String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded),
                expectedTaskManager,
                expectedTaskManager.getTaskList());

    }

    @Test
    public void execute_addDuplicate_notAllowed() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.adam();

        // setup starting state
        model.addTask(toBeAdded); // task already in internal TaskManager

        // execute command and verify result
        assertCommandFailure(helper.generateAddCommand(toBeAdded),  AddCommand.MESSAGE_DUPLICATE_TASK);

    }

    @Test
    public void executeAddInvalidEventNotAllowed() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.generateTask(1);
        LocalDateTime yesterday = LocalDateTime.now().minusDays(1);
        toBeAdded.setEndTime(new DateTime(yesterday));

        // setup starting state
        model.addTask(toBeAdded); // task already in internal TaskManager

        // execute command and verify result
        assertCommandFailure(helper.generateAddCommand(toBeAdded),  AddCommand.MESSAGE_INVALID_EVENT);

    }


    @Test
    public void execute_list_showsAllTasks() throws Exception {
        // prepare expectations
        TestDataHelper helper = new TestDataHelper();
        TaskManager expectedTaskManager = helper.generateTaskManager(2);
        List<? extends ReadOnlyTask> expectedList = expectedTaskManager.getTaskList();

        // prepare TaskManager state
        helper.addToModel(model, 2);

        assertCommandSuccess("list",
                ListCommand.MESSAGE_SUCCESS,
                expectedTaskManager,
                expectedList);
    }

    //@@author A0148081H
    @Test
    public void executeSortTasksByStartTime() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task p1 = helper.generateTaskWithStartTime("02/01/2017 00:00");
        Task p2 = helper.generateTaskWithStartTime("02/01/2017 23:59");
        Task p3 = helper.generateTaskWithStartTime("03/01/2017 00:00");
        Task p4 = helper.generateTaskWithStartTime("03/02/2017 00:00");
        Task p5 = helper.generateTaskWithStartTime("03/02/2018 00:00");

        List<Task> fiveTasks = helper.generateTaskList(p1, p2, p3, p4, p5);
        TaskManager expectedTaskManager = helper.generateTaskManager(fiveTasks);

        model.resetData(new TaskManager());
        model.addTask(p2);
        model.addTask(p3);
        model.addTask(p5);
        model.addTask(p1);
        model.addTask(p4);

        assertCommandSuccess("sort end",
                SortCommand.MESSAGE_SUCCESS + "end",
                expectedTaskManager,
                fiveTasks);
    }

    @Test
    public void executeSortTasksByEndTime() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task p1 = helper.generateTaskWithEndTime("02/01/2017 00:00");
        Task p2 = helper.generateTaskWithEndTime("02/01/2017 23:59");
        Task p3 = helper.generateTaskWithEndTime("03/01/2017 00:00");
        Task p4 = helper.generateTaskWithEndTime("03/02/2017 00:00");
        Task p5 = helper.generateTaskWithEndTime("03/02/2018 00:00");

        List<Task> fiveTasks = helper.generateTaskList(p1, p2, p3, p4, p5);
        TaskManager expectedTaskManager = helper.generateTaskManager(fiveTasks);

        model.resetData(new TaskManager());
        model.addTask(p2);
        model.addTask(p3);
        model.addTask(p5);
        model.addTask(p1);
        model.addTask(p4);

        assertCommandSuccess("sort end",
                SortCommand.MESSAGE_SUCCESS + "end",
                expectedTaskManager,
                fiveTasks);
    }

    @Test
    public void executeSortTasksByPriority() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task p1 = helper.generateTaskWithPriority("hi");
        Task p2 = helper.generateTaskWithPriority("mid");
        Task p3 = helper.generateTaskWithPriority("low");

        List<Task> threeTasks = helper.generateTaskList(p1, p2, p3);
        TaskManager expectedTaskManager = helper.generateTaskManager(threeTasks);

        model.resetData(new TaskManager());
        model.addTask(p2);
        model.addTask(p3);
        model.addTask(p1);

        assertCommandSuccess("sort priority",
                SortCommand.MESSAGE_SUCCESS + "priority",
                expectedTaskManager,
                threeTasks);
    }

    @Test
    public void executeSortTasksByStatus() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task p1 = helper.generateTaskWithStatus("incomplete");
        Task p2 = helper.generateTaskWithStatus("complete");

        List<Task> twoTasks = helper.generateTaskList(p1, p2);
        TaskManager expectedTaskManager = helper.generateTaskManager(twoTasks);

        model.resetData(new TaskManager());
        model.addTask(p2);
        model.addTask(p1);

        assertCommandSuccess("sort status",
                SortCommand.MESSAGE_SUCCESS + "status",
                expectedTaskManager,
                twoTasks);
    }

    @Test
    public void executeSortInvalidArgsFormat() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE);
        assertCommandFailure("sort ", expectedMessage);
    }
    //@@author

    /**
     * Confirms the 'invalid argument index number behaviour' for the given command
     * targeting a single task in the shown list, using visible index.
     * @param commandWord to test assuming it targets a single task in the last shown list
     *                    based on visible index.
     */
    private void assertIncorrectIndexFormatBehaviorForCommand(String commandWord, String expectedMessage)
            throws Exception {
        assertCommandFailure(commandWord , expectedMessage); //index missing
        assertCommandFailure(commandWord + " +1", expectedMessage); //index should be unsigned
        assertCommandFailure(commandWord + " -1", expectedMessage); //index should be unsigned
        assertCommandFailure(commandWord + " 0", expectedMessage); //index cannot be 0
        assertCommandFailure(commandWord + " not_a_number", expectedMessage);
    }

    /**
     * Confirms the 'invalid argument index number behaviour' for the given command
     * targeting a single task in the shown list, using visible index.
     * @param commandWord to test assuming it targets a single task in the last shown list
     *                    based on visible index.
     */
    private void assertIndexNotFoundBehaviorForCommand(String commandWord) throws Exception {
        String expectedMessage = MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
        TestDataHelper helper = new TestDataHelper();
        List<Task> taskList = helper.generateTaskList(2);

        // set TM state to 2 tasks
        model.resetData(new TaskManager());
        for (Task t : taskList) {
            model.addTask(t);
        }

        assertCommandFailure(commandWord + " 3", expectedMessage);
    }

    @Test
    public void execute_selectInvalidArgsFormat_errorMessageShown() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE);
        assertIncorrectIndexFormatBehaviorForCommand("select", expectedMessage);
    }

    @Test
    public void execute_selectIndexNotFound_errorMessageShown() throws Exception {
        assertIndexNotFoundBehaviorForCommand("select");
    }

    @Test
    public void execute_select_jumpsToCorrectTask() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threeTasks = helper.generateTaskList(3);

        TaskManager expectedTaskManager = helper.generateTaskManager(threeTasks);
        helper.addToModel(model, threeTasks);

        assertCommandSuccess("select 2",
                String.format(SelectCommand.MESSAGE_SELECT_TASK_SUCCESS, 2),
                expectedTaskManager,
                expectedTaskManager.getTaskList());
        assertEquals(1, targetedJumpIndex);
        assertEquals(model.getFilteredTaskList().get(1), threeTasks.get(1));
    }

    @Test
    public void execute_deleteInvalidArgsFormat_errorMessageShown() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE);
        assertIncorrectIndexFormatBehaviorForCommand("delete", expectedMessage);
    }

    @Test
    public void execute_deleteIndexNotFound_errorMessageShown() throws Exception {
        assertIndexNotFoundBehaviorForCommand("delete");
    }

    @Test
    public void execute_delete_removesCorrectTask() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threeTasks = helper.generateTaskList(3);

        TaskManager expectedTaskManager = helper.generateTaskManager(threeTasks);
        expectedTaskManager.removeTask(threeTasks.get(1));
        helper.addToModel(model, threeTasks);

        assertCommandSuccess("delete 2",
                String.format(DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, threeTasks.get(1)),
                expectedTaskManager,
                expectedTaskManager.getTaskList());
    }

    @Test
    public void executeMarkCompleteTest() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task p1 = helper.generateTaskWithStatus("incomplete");
        List<Task> oneTask = helper.generateTaskList(p1);

        TaskManager expectedTaskManager = helper.generateTaskManager(oneTask);
        Task editedTask = oneTask.get(0);
        editedTask.setStatus(new Status("complete"));

        expectedTaskManager.updateTask(0, editedTask);
        helper.addToModel(model, oneTask);

        assertCommandSuccess("mark 1",
                String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, oneTask.get(0)),
                expectedTaskManager,
                expectedTaskManager.getTaskList());
    }

    @Test
    public void executeMarkInvalidArgsFormat() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkCommand.MESSAGE_USAGE);
        assertCommandFailure("mark ", expectedMessage);
    }

    @Test
    public void executeMarkInvalidIndex() throws Exception {
        assertIndexNotFoundBehaviorForCommand("mark");
    }

    @Test
    public void execute_find_invalidArgsFormat() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE);
        assertCommandFailure("find ", expectedMessage);
    }

    @Test
    public void execute_find_onlyMatchesFullWordsInNames() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task pTarget1 = helper.generateTaskWithName("bla bla KEY bla");
        Task pTarget2 = helper.generateTaskWithName("bla KEY bla bceofeia");
        Task p1 = helper.generateTaskWithName("KE Y");
        Task p2 = helper.generateTaskWithName("KEYKEYKEY sduauo");

        List<Task> fourTasks = helper.generateTaskList(p1, pTarget1, p2, pTarget2);
        TaskManager expectedTaskManager = helper.generateTaskManager(fourTasks);
        List<Task> expectedList = helper.generateTaskList(pTarget1, pTarget2);
        helper.addToModel(model, fourTasks);

        assertCommandSuccess("find KEY",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedTaskManager,
                expectedList);
    }

    @Test
    public void execute_find_isNotCaseSensitive() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task p1 = helper.generateTaskWithName("bla bla KEY bla");
        Task p2 = helper.generateTaskWithName("bla KEY bla bceofeia");
        Task p3 = helper.generateTaskWithName("key key");
        Task p4 = helper.generateTaskWithName("KEy sduauo");

        List<Task> fourTasks = helper.generateTaskList(p3, p1, p4, p2);
        TaskManager expectedTaskManager = helper.generateTaskManager(fourTasks);
        List<Task> expectedList = fourTasks;
        helper.addToModel(model, fourTasks);

        assertCommandSuccess("find KEY",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedTaskManager,
                expectedList);
    }

    @Test
    public void execute_find_matchesIfAnyKeywordPresent() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task pTarget1 = helper.generateTaskWithName("bla bla KEY bla");
        Task pTarget2 = helper.generateTaskWithName("bla rAnDoM bla bceofeia");
        Task pTarget3 = helper.generateTaskWithName("key key");
        Task p1 = helper.generateTaskWithName("sduauo");

        List<Task> fourTasks = helper.generateTaskList(pTarget1, p1, pTarget2, pTarget3);
        TaskManager expectedTaskManager = helper.generateTaskManager(fourTasks);
        List<Task> expectedList = helper.generateTaskList(pTarget1, pTarget2, pTarget3);
        helper.addToModel(model, fourTasks);

        assertCommandSuccess("find key rAnDoM",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedTaskManager,
                expectedList);
    }

    //@@author A0148087W
    @Test
    public void executeUndoResetToPreviousState() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task testTask1 = helper.generateTaskWithName("Task1");
        Task testTask2 = helper.generateTaskWithName("Task2");
        List<Task> oneTasks = helper.generateTaskList(testTask1);
        TaskManager expectedTaskManager = helper.generateTaskManager(oneTasks);
        Task testTask1Copy = helper.generateTaskWithName("Task1");

        //Undo adding one task
        model.addTask(testTask1);
        assertCommandSuccess("undo", UndoCommand.MESSAGE_SUCCESS, new TaskManager(), Collections.emptyList());

        //Undo adding task when there is 1 existing task
        model.resetData(new TaskManager());
        model.addTask(testTask1);
        model.addTask(testTask2);
        assertCommandSuccess("undo", UndoCommand.MESSAGE_SUCCESS, expectedTaskManager, oneTasks);

        //Undo Deletion
        model.resetData(new TaskManager());
        model.addTask(testTask1);
        model.deleteTask(testTask1);
        assertCommandSuccess("undo", UndoCommand.MESSAGE_SUCCESS, expectedTaskManager, oneTasks);

        //Undo Edit
        model.resetData(new TaskManager());
        model.addTask(testTask1Copy);
        model.updateTask(0, testTask2);
        assertCommandSuccess("undo", UndoCommand.MESSAGE_SUCCESS, expectedTaskManager, oneTasks);
    }

    @Test
    public void executeRedoResetToPrecedingState() throws Exception {
        TaskManager tempTaskManager;
        TestDataHelper helper = new TestDataHelper();
        Task testTask1 = helper.generateTaskWithName("Task1");
        Task testTask2 = helper.generateTaskWithName("Task2");
        List<Task> oneTasks = helper.generateTaskList(testTask1);
        List<Task> twoTasks = helper.generateTaskList(testTask1, testTask2);
        List<Task> taskTwoOnly = helper.generateTaskList(testTask2);
        TaskManager expectedTaskManagerWithOneTask = helper.generateTaskManager(oneTasks);
        TaskManager expectedTaskManagerWithTwoTask = helper.generateTaskManager(twoTasks);
        Task testTask1Copy = helper.generateTaskWithName("Task1");

        //Redo adding one task
        model.addTask(testTask1);
        assertCommandSuccess("undo", UndoCommand.MESSAGE_SUCCESS, new TaskManager(), Collections.emptyList());
        assertCommandSuccess("redo", RedoCommand.MESSAGE_SUCCESS, expectedTaskManagerWithOneTask, oneTasks);

        //Redo adding two task
        model.resetData(new TaskManager());
        model.addTask(testTask1);
        model.addTask(testTask2);
        assertCommandSuccess("undo", UndoCommand.MESSAGE_SUCCESS, expectedTaskManagerWithOneTask, oneTasks);
        assertCommandSuccess("undo", UndoCommand.MESSAGE_SUCCESS, new TaskManager(), Collections.emptyList());
        assertCommandSuccess("redo", RedoCommand.MESSAGE_SUCCESS, expectedTaskManagerWithOneTask, oneTasks);
        assertCommandSuccess("redo", RedoCommand.MESSAGE_SUCCESS, expectedTaskManagerWithTwoTask, twoTasks);

        //Redo Deletion
        model.resetData(new TaskManager());
        model.addTask(testTask1);
        model.deleteTask(testTask1);
        tempTaskManager = new TaskManager(model.getTaskManager());
        assertCommandSuccess("undo", UndoCommand.MESSAGE_SUCCESS, expectedTaskManagerWithOneTask, oneTasks);
        assertCommandSuccess("redo", RedoCommand.MESSAGE_SUCCESS, tempTaskManager, Collections.emptyList());

        //Redo Edit
        model.resetData(new TaskManager());
        model.addTask(testTask1Copy);
        model.updateTask(0, testTask2);
        tempTaskManager = new TaskManager(model.getTaskManager());
        assertCommandSuccess("undo", UndoCommand.MESSAGE_SUCCESS, expectedTaskManagerWithOneTask, oneTasks);
        assertCommandSuccess("redo", RedoCommand.MESSAGE_SUCCESS, tempTaskManager, taskTwoOnly);
    }

    @Test
    public void assertUndoException() {
        assertCommandFailure("undo", History.MESSAGE_INVALID_UNDO);
    }

    @Test
    public void assertRedoException() {
        assertCommandFailure("redo", History.MESSAGE_INVALID_REDO);
    }

    @Test
    public void executeUndoFailure() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task testTask1 = helper.generateTaskWithName("Task1");

        model.addTask(testTask1);
        assertCommandSuccess("undo", UndoCommand.MESSAGE_SUCCESS, new TaskManager(), Collections.emptyList());
        assertCommandFailure("undo", History.MESSAGE_INVALID_UNDO);
    }

    @Test
    public void executeRedoFailure() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task testTask1 = helper.generateTaskWithName("Task1");
        List<Task> oneTasks = helper.generateTaskList(testTask1);
        TaskManager expectedTaskManager = helper.generateTaskManager(oneTasks);

        model.addTask(testTask1);
        assertCommandSuccess("undo", UndoCommand.MESSAGE_SUCCESS, new TaskManager(), Collections.emptyList());
        assertCommandSuccess("redo", RedoCommand.MESSAGE_SUCCESS, expectedTaskManager, oneTasks);
        assertCommandFailure("redo", History.MESSAGE_INVALID_REDO);
    }
    //@@author

    /**
     * A utility class to generate test data.
     */
    class TestDataHelper {

        Task adam() throws Exception {
            Name name = new Name("Rehearse OP1");
            Priority priority = new Priority("hi");
            Status status = new Status("incomplete");
            Note note = new Note("edit slides");
            DateTime startTime = new DateTime("12/12/2020 12:00");
            DateTime endTime = new DateTime("12/12/2020 13:00");
            Tag tag1 = new Tag("tag1");
            Tag tag2 = new Tag("longertag2");
            UniqueTagList tags = new UniqueTagList(tag1, tag2);
            return new Task(name, null, priority, status, note, startTime, endTime, tags);
        }

        /**
         * Generates a valid task using the given seed.
         * Running this function with the same parameter values guarantees the returned task will have the same state.
         * Each unique seed will generate a unique Task object.
         *
         * @param seed used to generate the task data field values
         */
        Task generateTask(int seed) throws Exception {
            return new Task(
                    new Name("Task " + seed),
                    Integer.toString(seed),
                    generatePriorityWithSeed(seed),
                    new Status("incomplete"),
                    new Note("House of " + seed),
                    new DateTime("12/12/" + (2017 + seed) + " 12:00"),
                    new DateTime("12/12/" + (2017 + seed) + " 13:00"),
                    new UniqueTagList(new Tag("tag" + Math.abs(seed)),
                    new Tag("tag" + Math.abs(seed + 1)))
            );
        }

        /** Generates the correct add command based on the task given */
        String generateAddCommand(Task p) {
            StringBuffer cmd = new StringBuffer();

            cmd.append("add ");

            cmd.append(p.getName().toString());
            cmd.append(" s/").append(p.getStatus());

            if (p.getPriority().isPresent()) {
                cmd.append(" p/").append(p.getPriority().get().toString());
            }

            if (p.getNote().isPresent()) {
                cmd.append(" n/").append(p.getNote().get().toString());
            }

            if (p.getStartTime().isPresent()) {
                cmd.append(" b/").append(p.getStartTime().get().toString());
            }

            if (p.getEndTime().isPresent()) {
                cmd.append(" e/").append(p.getEndTime().get().toString());
            }

            UniqueTagList tags = p.getTags();
            for (Tag t: tags) {
                cmd.append(" t/").append(t.tagName);
            }

            return cmd.toString();
        }

        /**
         * Generates an TaskManager with auto-generated tasks.
         */
        TaskManager generateTaskManager(int numGenerated) throws Exception {
            TaskManager taskManager = new TaskManager();
            addToTaskManager(taskManager, numGenerated);
            return taskManager;
        }

        /**
         * Generates an TaskManager based on the list of Tasks given.
         */
        TaskManager generateTaskManager(List<Task> tasks) throws Exception {
            TaskManager taskManager = new TaskManager();
            addToTaskManager(taskManager, tasks);
            return taskManager;
        }

        /**
         * Adds auto-generated Task objects to the given TaskManager
         * @param taskManager The TaskManager to which the Tasks will be added
         */
        void addToTaskManager(TaskManager taskManager, int numGenerated) throws Exception {
            addToTaskManager(taskManager, generateTaskList(numGenerated));
        }

        /**
         * Adds the given list of Tasks to the given TaskManager
         */
        void addToTaskManager(TaskManager taskManager, List<Task> tasksToAdd) throws Exception {
            for (Task p: tasksToAdd) {
                taskManager.addTask(p);
            }
        }

        /**
         * Adds auto-generated Task objects to the given model
         * @param model The model to which the Tasks will be added
         */
        void addToModel(Model model, int numGenerated) throws Exception {
            addToModel(model, generateTaskList(numGenerated));
        }

        /**
         * Adds the given list of Tasks to the given model
         */
        void addToModel(Model model, List<Task> tasksToAdd) throws Exception {
            for (Task t: tasksToAdd) {
                model.addTask(t);
            }
        }

        /**
         * Generates a list of Tasks based on the flags.
         */
        List<Task> generateTaskList(int numGenerated) throws Exception {
            List<Task> tasks = new ArrayList<>();
            for (int i = 1; i <= numGenerated; i++) {
                tasks.add(generateTask(i));
            }
            return tasks;
        }

        List<Task> generateTaskList(Task... tasks) {
            return Arrays.asList(tasks);
        }

        /**
         * Generates a Task object with given name. Other fields will have some dummy values.
         */
        Task generateTaskWithName(String name) throws Exception {
            return new Task(
                    new Name(name),
                    "1",
                    new Priority("hi"),
                    new Status("incomplete"),
                    new Note("House of 1"),
                    new DateTime("12/12/2020 12:00"),
                    new DateTime("12/12/2020 13:00"),
                    new UniqueTagList(new Tag("tag"))
            );
        }

        //@@author A0148081H-reused
        /**
         * Generates a Task object with given start time. Other fields will have some dummy values.
         */
        private Task generateTaskWithStartTime(String startTime) throws Exception {
            return new Task(
                    new Name("Finish assignment"),
                    "2",
                    new Priority("hi"),
                    new Status("incomplete"),
                    new Note("House of 1"),
                    new DateTime(startTime),
                    new DateTime("02/02/2017 00:00"),
                    new UniqueTagList(new Tag("tag"))
            );
        }

        /**
         * Generates a Task object with given end time. Other fields will have some dummy values.
         */
        private Task generateTaskWithEndTime(String endTime) throws Exception {
            return new Task(
                    new Name("Finish assignment"),
                    "3",
                    new Priority("hi"),
                    new Status("incomplete"),
                    new Note("House of 1"),
                    new DateTime("01/01/2017 00:00"),
                    new DateTime(endTime),
                    new UniqueTagList(new Tag("tag"))
            );
        }

        /**
         * Generates a Task object with given priority. Other fields will have some dummy values.
         */
        private Task generateTaskWithPriority(String priority) throws Exception {
            return new Task(
                    new Name("Finish assignment"),
                    "4",
                    new Priority(priority),
                    new Status("incomplete"),
                    new Note("House of 1"),
                    new DateTime("01/01/2017 00:00"),
                    new DateTime("01/01/2017 23:59"),
                    new UniqueTagList(new Tag("tag"))
            );
        }

        /**
         * Generates a Task object with given status. Other fields will have some dummy values.
         */
        private Task generateTaskWithStatus(String status) throws Exception {
            return new Task(
                    new Name("Finish assignment"),
                    "5",
                    new Priority("hi"),
                    new Status(status),
                    new Note("House of 1"),
                    new DateTime("01/01/2017 00:00"),
                    new DateTime("01/01/2017 23:59"),
                    new UniqueTagList(new Tag("tag"))
            );
        }
        //@@author

        private Priority generatePriorityWithSeed(int seed) {
            switch(seed) {
            case 1:
                return new Priority(Priority.Level.LOW);
            case 2:
                return new Priority(Priority.Level.MEDIUM);
            case 3:
                return new Priority(Priority.Level.HIGH);
            default:
                return null;
            }
        }
    }
}
