package taa.logic.parser;

import taa.commons.core.Messages;
import taa.logic.commands.CreateClassCommand;
import taa.logic.parser.exceptions.ParseException;
import taa.model.ClassList;

/**
 * Parses input arguments and creates a new AddStudentCommand object
 */
public class CreateClassCommandParser implements Parser<CreateClassCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddStudentCommand
     * and returns an AddStudentCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CreateClassCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, CreateClassCommand.MESSAGE_USAGE));
        }
        ClassList toAdd = new ClassList(trimmedArgs);
        return new CreateClassCommand(toAdd);
    }

}
