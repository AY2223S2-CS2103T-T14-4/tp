package taa.testutil;

import java.util.HashSet;
import java.util.Set;

import taa.model.student.Email;
import taa.model.student.Name;
import taa.model.student.Student;
import taa.model.tag.Tag;
import taa.model.util.SampleDataUtil;

/**
 * A utility class to help with building Student objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";

    private Name name;
    private Email email;
    private Set<Tag> tags;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        email = new Email(DEFAULT_EMAIL);
        tags = new HashSet<>();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code studentToCopy}.
     */
    public PersonBuilder(Student studentToCopy) {
        name = studentToCopy.getName();
        email = studentToCopy.getEmail();
        tags = new HashSet<>(studentToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Student} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Student} that we are building.
     */
    public PersonBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Student} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Student} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    public Student build() {
        return new Student(name, email, tags);
    }

}
