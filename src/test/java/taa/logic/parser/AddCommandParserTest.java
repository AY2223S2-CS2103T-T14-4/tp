package taa.logic.parser;

import org.junit.jupiter.api.Test;

import taa.logic.commands.AddCommand;
import seedu.address.model.student.*;
import taa.model.tag.Tag;
import taa.testutil.PersonBuilder;
import taa.commons.core.Messages;
import taa.logic.commands.CommandTestUtil;
import taa.model.student.Email;
import taa.model.student.Name;
import taa.model.student.Phone;
import taa.model.student.Student;
import taa.testutil.TypicalPersons;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Student expectedStudent = new PersonBuilder(TypicalPersons.BOB).withTags(CommandTestUtil.VALID_TAG_FRIEND).build();

        // whitespace only preamble
        CommandParserTestUtil.assertParseSuccess(parser, CommandTestUtil.PREAMBLE_WHITESPACE + CommandTestUtil.NAME_DESC_BOB + CommandTestUtil.PHONE_DESC_BOB + CommandTestUtil.EMAIL_DESC_BOB
                + CommandTestUtil.ADDRESS_DESC_BOB + CommandTestUtil.TAG_DESC_FRIEND, new AddCommand(expectedStudent));

        // multiple names - last name accepted
        CommandParserTestUtil.assertParseSuccess(parser, CommandTestUtil.NAME_DESC_AMY + CommandTestUtil.NAME_DESC_BOB + CommandTestUtil.PHONE_DESC_BOB + CommandTestUtil.EMAIL_DESC_BOB
                + CommandTestUtil.ADDRESS_DESC_BOB + CommandTestUtil.TAG_DESC_FRIEND, new AddCommand(expectedStudent));

        // multiple phones - last phone accepted
        CommandParserTestUtil.assertParseSuccess(parser, CommandTestUtil.NAME_DESC_BOB + CommandTestUtil.PHONE_DESC_AMY + CommandTestUtil.PHONE_DESC_BOB + CommandTestUtil.EMAIL_DESC_BOB
                + CommandTestUtil.ADDRESS_DESC_BOB + CommandTestUtil.TAG_DESC_FRIEND, new AddCommand(expectedStudent));

        // multiple emails - last email accepted
        CommandParserTestUtil.assertParseSuccess(parser, CommandTestUtil.NAME_DESC_BOB + CommandTestUtil.PHONE_DESC_BOB + CommandTestUtil.EMAIL_DESC_AMY + CommandTestUtil.EMAIL_DESC_BOB
                + CommandTestUtil.ADDRESS_DESC_BOB + CommandTestUtil.TAG_DESC_FRIEND, new AddCommand(expectedStudent));

        // multiple addresses - last address accepted
        CommandParserTestUtil.assertParseSuccess(parser, CommandTestUtil.NAME_DESC_BOB + CommandTestUtil.PHONE_DESC_BOB + CommandTestUtil.EMAIL_DESC_BOB + CommandTestUtil.ADDRESS_DESC_AMY
                + CommandTestUtil.ADDRESS_DESC_BOB + CommandTestUtil.TAG_DESC_FRIEND, new AddCommand(expectedStudent));

        // multiple tags - all accepted
        Student expectedStudentMultipleTags = new PersonBuilder(TypicalPersons.BOB).withTags(CommandTestUtil.VALID_TAG_FRIEND, CommandTestUtil.VALID_TAG_HUSBAND)
                .build();
        CommandParserTestUtil.assertParseSuccess(parser, CommandTestUtil.NAME_DESC_BOB + CommandTestUtil.PHONE_DESC_BOB + CommandTestUtil.EMAIL_DESC_BOB + CommandTestUtil.ADDRESS_DESC_BOB
                + CommandTestUtil.TAG_DESC_HUSBAND + CommandTestUtil.TAG_DESC_FRIEND, new AddCommand(expectedStudentMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Student expectedStudent = new PersonBuilder(TypicalPersons.AMY).withTags().build();
        CommandParserTestUtil.assertParseSuccess(parser, CommandTestUtil.NAME_DESC_AMY + CommandTestUtil.PHONE_DESC_AMY + CommandTestUtil.EMAIL_DESC_AMY + CommandTestUtil.ADDRESS_DESC_AMY,
                new AddCommand(expectedStudent));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.VALID_NAME_BOB + CommandTestUtil.PHONE_DESC_BOB + CommandTestUtil.EMAIL_DESC_BOB + CommandTestUtil.ADDRESS_DESC_BOB,
                expectedMessage);

        // missing phone prefix
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.NAME_DESC_BOB + CommandTestUtil.VALID_PHONE_BOB + CommandTestUtil.EMAIL_DESC_BOB + CommandTestUtil.ADDRESS_DESC_BOB,
                expectedMessage);

        // missing email prefix
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.NAME_DESC_BOB + CommandTestUtil.PHONE_DESC_BOB + CommandTestUtil.VALID_EMAIL_BOB + CommandTestUtil.ADDRESS_DESC_BOB,
                expectedMessage);

        // missing address prefix
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.NAME_DESC_BOB + CommandTestUtil.PHONE_DESC_BOB + CommandTestUtil.EMAIL_DESC_BOB + CommandTestUtil.VALID_ADDRESS_BOB,
                expectedMessage);

        // all prefixes missing
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.VALID_NAME_BOB + CommandTestUtil.VALID_PHONE_BOB + CommandTestUtil.VALID_EMAIL_BOB + CommandTestUtil.VALID_ADDRESS_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.INVALID_NAME_DESC + CommandTestUtil.PHONE_DESC_BOB + CommandTestUtil.EMAIL_DESC_BOB + CommandTestUtil.ADDRESS_DESC_BOB
                + CommandTestUtil.TAG_DESC_HUSBAND + CommandTestUtil.TAG_DESC_FRIEND, Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.NAME_DESC_BOB + CommandTestUtil.INVALID_PHONE_DESC + CommandTestUtil.EMAIL_DESC_BOB + CommandTestUtil.ADDRESS_DESC_BOB
                + CommandTestUtil.TAG_DESC_HUSBAND + CommandTestUtil.TAG_DESC_FRIEND, Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.NAME_DESC_BOB + CommandTestUtil.PHONE_DESC_BOB + CommandTestUtil.INVALID_EMAIL_DESC + CommandTestUtil.ADDRESS_DESC_BOB
                + CommandTestUtil.TAG_DESC_HUSBAND + CommandTestUtil.TAG_DESC_FRIEND, Email.MESSAGE_CONSTRAINTS);

        // invalid tag
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.NAME_DESC_BOB + CommandTestUtil.PHONE_DESC_BOB + CommandTestUtil.EMAIL_DESC_BOB + CommandTestUtil.ADDRESS_DESC_BOB
                + CommandTestUtil.INVALID_TAG_DESC + CommandTestUtil.VALID_TAG_FRIEND, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.INVALID_NAME_DESC + CommandTestUtil.PHONE_DESC_BOB + CommandTestUtil.EMAIL_DESC_BOB + CommandTestUtil.INVALID_ADDRESS_DESC,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.PREAMBLE_NON_EMPTY + CommandTestUtil.NAME_DESC_BOB + CommandTestUtil.PHONE_DESC_BOB + CommandTestUtil.EMAIL_DESC_BOB
                + CommandTestUtil.ADDRESS_DESC_BOB + CommandTestUtil.TAG_DESC_HUSBAND + CommandTestUtil.TAG_DESC_FRIEND,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
