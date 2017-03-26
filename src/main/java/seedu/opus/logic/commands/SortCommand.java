package seedu.opus.logic.commands;

/**
 * Sorts all tasks in the task manager to the user.
 */
public class SortCommand extends Command {

	public static final String COMMAND_WORD = "sort";

	public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts all tasks according to the value given\n"
			+ "Parameters: KEYWORD\n"
			+ "Example: " + COMMAND_WORD + " priority";

	public static final String MESSAGE_SUCCESS = "Sorted all tasks!";

	private String keyword;

	public SortCommand(String keyword) {
		this.keyword = keyword;
	}

	@Override
	public CommandResult execute() {
		model.sortList(keyword);
		return new CommandResult(MESSAGE_SUCCESS);
	}
}
