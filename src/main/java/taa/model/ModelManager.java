package taa.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import taa.assignment.AssignmentList;
import taa.commons.core.GuiSettings;
import taa.commons.core.LogsCenter;
import taa.commons.util.CollectionUtil;
import taa.model.student.Student;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final ClassList classList;
    private final UserPrefs userPrefs;
    private final FilteredList<Student> filteredStudents;

    private final AssignmentList assignmentList = new AssignmentList();

    /**
     * Initializes a ModelManager with the given classList and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        CollectionUtil.requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.classList = new ClassList(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredStudents = new FilteredList<>(this.classList.getStudentList());
    }

    public ModelManager() {
        this(new ClassList(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== ClassList ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.classList.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return classList;
    }

    @Override
    public boolean hasStudent(Student student) {
        requireNonNull(student);
        return classList.hasStudent(student);
    }

    @Override
    public void deleteStudent(Student target) {
        classList.removeStudent(target);
    }

    @Override
    public void addStudent(Student student) {
        classList.addStudent(student);
        updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);
    }

    @Override
    public void setStudent(Student target, Student editedStudent) {
        CollectionUtil.requireAllNonNull(target, editedStudent);

        classList.setStudent(target, editedStudent);
    }

    //=========== Filtered Student List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Student} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Student> getFilteredStudentList() {
        return filteredStudents;
    }

    @Override
    public void updateFilteredStudentList(Predicate<Student> predicate) {
        requireNonNull(predicate);
        filteredStudents.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return classList.equals(other.classList)
                && userPrefs.equals(other.userPrefs)
                && filteredStudents.equals(other.filteredStudents);
    }

    //=========== AssignmentList Helpers =============================================================

    @Override
    public void addAssignment(String assignmentName) {
        assignmentList.add(assignmentName, filteredStudents);
    }

    @Override
    public void deleteAssignment(String assignmentName) {
        assignmentList.delete(assignmentName);
    }

    @Override
    public void grade(String assignmentName, int studentId, int marks) {
        assignmentList.grade(assignmentName, studentId, marks);
    }
}
