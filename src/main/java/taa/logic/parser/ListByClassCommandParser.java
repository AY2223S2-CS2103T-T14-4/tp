package taa.logic.parser;

import java.util.Arrays;

import taa.commons.core.Messages;
import taa.logic.commands.FindCommand;
import taa.logic.commands.ListByClassCommand;
import taa.logic.parser.exceptions.ParseException;
import taa.model.ClassIdMatchesPredicate;
import taa.model.student.NameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class ListByClassCommandParser implements Parser<ListByClassCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListByClassCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
        int num = Integer.parseInt(trimmedArgs);
        return new ListByClassCommand(new ClassIdMatchesPredicate(num));
    }

}
