package taa.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import taa.commons.core.GuiSettings;
import taa.logic.commands.enums.ChartType;
import taa.logic.commands.exceptions.CommandException;
import taa.model.student.Student;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Student> PREDICATE_SHOW_ALL_STUDENTS = unused -> true;
    Predicate<ClassList> PREDICATE_SHOW_ALL_CLASSES = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /** Returns the ClassList */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a student with the same identity as {@code student} exists in the address book.
     */
    boolean hasStudent(Student student);


    boolean hasClassList(ClassList tocheck);

    /**
     * Returns the number of students in the active class list.
     */
    int getClassListSize();

    /**
     * Deletes the given student.
     * The student must exist in the address book.
     */
    void deleteStudent(Student target);

    /**
     * Adds the given student.
     * {@code student} must not already exist in the address book.
     */
    void addStudent(Student student);

    void addClassList(ClassList toAdd);

    /**
     * Updates the student list to propagate change to the rest of the model.
     * @param student The student to be refreshed.
     */
    void updateStudent(Student student);

    /**
     * Replaces the given student {@code target} with {@code editedStudent}.
     * {@code target} must exist in the address book.
     * The student identity of {@code editedStudent} must not be the same as
     * another existing student in the address book.
     */
    void setStudent(Student target, Student editedStudent);

    /**
     * Returns an unmodifiable view of the filtered student list
     */
    ObservableList<Student> getFilteredStudentList();

    ObservableList<Student> getFilteredClassList();

    /**
     * Updates the filter of the filtered student list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredStudentList(Predicate<Student> predicate);

    void updateFilteredClassLists(Predicate<ClassList> predicate);

    /**
     * Adds a student to all the class lists he/she is tagged with.
     * Creates new class lists to add the student into, if required.
     * @param student The student to include in all of his/her tagged classes.
     */
    void addStudentToTaggedClasses(Student student);

    /**
     * Checks if a given assignment already exists.
     */
    boolean hasAssignment(String assignmentName) throws CommandException;
    void addAssignment(String assignmentName, int totalMarks) throws CommandException;

    void deleteAssignment(String assignmentName) throws CommandException;

    void grade(String assignmentName, int studentId, int marks, boolean isLateSubmission) throws CommandException;

    String listAssignments();

    void ungrade(String assignmentName, int studentId) throws CommandException;

    void addAlarm(Alarm alarm) throws CommandException;

    void displayChart(ChartType chartType);
}
