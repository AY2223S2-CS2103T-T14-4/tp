package taa.model;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.util.Duration;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import taa.assignment.AssignmentList;
import taa.commons.core.GuiSettings;
import taa.commons.core.LogsCenter;
import taa.commons.core.index.Index;
import taa.commons.util.CollectionUtil;
import taa.logic.commands.enums.ChartType;
import taa.logic.commands.exceptions.CommandException;
import taa.model.student.Attendance;
import taa.model.student.Name;
import taa.model.student.SameStudentPredicate;
import taa.model.student.Student;
import taa.model.tag.Tag;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final ClassList classList;
    private final UserPrefs userPrefs;
    private final Tutor tutor;
    private final FilteredList<Student> filteredStudents;
    private final FilteredList<ClassList> filteredClassLists;

    private final AssignmentList assignmentList = new AssignmentList();
    private Predicate<ClassList> activeClassListPredicate;

    /**
     * Initializes a ModelManager with the given classList and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        CollectionUtil.requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.userPrefs = new UserPrefs(userPrefs);
        this.classList = new ClassList(addressBook);
        UniqueClassLists temp = new UniqueClassLists(this.classList);
        this.tutor = new Tutor(new Name("James"), new HashSet<>(), temp);
        this.filteredStudents = new FilteredList<>(this.classList.getStudentList());
        this.filteredClassLists = new FilteredList<ClassList>(this.tutor.getClassList());
        this.activeClassListPredicate = null;

        for (Student student : this.classList.getUniqueStudentList()) {
            addStudentToTaggedClasses(student);
        }

    }

    public ModelManager() {
        this(new ClassList(), new UserPrefs());
    }

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
        addStudentToTaggedClasses(student);
        if (activeClassListPredicate == null) {
            updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);
        } else {
            updateFilteredClassLists(activeClassListPredicate);
        }
    }

    @Override
    public void updateStudent(Student target) {
        classList.updateStudent(target);
    }

    @Override
    public void setStudent(Student target, Student editedStudent) {
        CollectionUtil.requireAllNonNull(target, editedStudent);

        classList.setStudent(target, editedStudent);
    }


    //=========== ClassList ================================================================================

    /**
     * Check whether the tutor already has the class.
     * @param classList the class name to be checked.
     * @return Boolean variable indicating whether it's contained.
     */
    public boolean hasClassList(ClassList classList) {
        requireNonNull(classList);
        return tutor.containsClassList(classList);
    }

    @Override
    public void addClassList(ClassList toAdd) {
        tutor.addClass(toAdd);
        updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);
    }

    @Override
    public void addStudentToTaggedClasses(Student student) {
        requireNonNull(student);

        Set<Tag> classTags = student.getClassTags();
        for (Tag tag : classTags) {
            String className = tag.tagName;
            this.tutor.addStudentToClass(student, className);
        }
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
    public ObservableList<Student> getFilteredClassList() {
        return filteredStudents;
    }

    @Override
    public void updateFilteredStudentList(Predicate<Student> predicate) {
        requireNonNull(predicate);
        this.activeClassListPredicate = null;
        filteredStudents.setPredicate(predicate);
    }

    @Override
    public void updateFilteredClassLists(Predicate<ClassList> predicate) {
        requireNonNull(predicate);
        //filteredClassLists.setPredicate(predicate);
        this.activeClassListPredicate = predicate;
        FilteredList<ClassList> filtered = filteredClassLists.filtered(this.activeClassListPredicate);
        if (filtered.size() > 0) {
            filteredStudents.setPredicate(new SameStudentPredicate(filtered.get(0)));
        } else {
            filteredStudents.setPredicate(p->false);
        }
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
    public boolean hasAssignment(String assignmentName) {
        requireNonNull(assignmentName);
        return assignmentList.contains(assignmentName);
    }

    @Override
    public void addAssignment(String assignmentName, int totalMarks) throws CommandException {
        assignmentList.add(assignmentName, filteredStudents, totalMarks);
        for (Student student : filteredStudents) {
            updateStudent(student);
        }
    }

    @Override
    public void deleteAssignment(String assignmentName) throws CommandException {
        assignmentList.delete(assignmentName);
        for (Student student : filteredStudents) {
            updateStudent(student);
        }
    }

    @Override
    public void grade(String assignmentName, int studentId, int marks, boolean isLateSubmission)
            throws CommandException {
        Student student = this.filteredStudents.get(Index.fromOneBased(studentId).getZeroBased());
        assignmentList.grade(assignmentName, student, marks, isLateSubmission);
        updateStudent(student);
    }

    @Override
    public void ungrade(String assignmentName, int studentId) throws CommandException {
        Student student = this.filteredStudents.get(Index.fromOneBased(studentId).getZeroBased());
        assignmentList.ungrade(assignmentName, student);
        updateStudent(student);
    }

    @Override
    public String listAssignments() {
        return assignmentList.list();
    }

    //Solution below adapted from ChatGPT
    @Override
    public void addAlarm(Alarm alarm) {
        Duration duration = Duration.minutes(alarm.getTime());
        Timeline timeline = new Timeline(new KeyFrame(duration, event -> {
            String music = "src/main/resources/sounds/bell.wav";
            try {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(music));
                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.start();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }));
        timeline.play();
    }

    @Override
    public void displayChart(ChartType chartType) {
        String title;
        JFreeChart chart;

        switch (chartType) {
        case CLASS_GRADES:
            title = "Grade Distribution";
            chart = generateGradeBarChart();
            break;
        case CLASS_ATTENDANCE:
            title = "Attendance Distribution";
            chart = generateAttendanceDistribution();
            break;
        default:
            // this should be unreachable; any invalid ChartType should be handled before this is invoked!
            assert false;
            return;
        }

        ChartFrame frame = new ChartFrame(title, chart);
        frame.pack();
        frame.setVisible(true);
    }

    private JFreeChart generateGradeBarChart() {
        return null;
    }

    private JFreeChart generateAttendanceDistribution() {
        DefaultCategoryDataset attendanceData = new DefaultCategoryDataset();
        int[] studentAttendance = countStudentAttendance();

        for (int i = 0; i < studentAttendance.length; i++) {
            attendanceData.setValue(
                    studentAttendance[i],
                    "Present",
                    String.format("W%d", i+1));
        }

        return ChartFactory.createBarChart(
                "Attendance",     //Chart title
                "Week",     //Domain axis label
                "Number of Students",         //Range axis label
                attendanceData,         //Chart Data
                PlotOrientation.VERTICAL, // orientation
                true,             // include legend?
                true,             // include tooltips?
                false             // include URLs?
        );
    }

    private int[] countStudentAttendance() {
        int[] result = new int[Attendance.NUM_WEEKS];

        for (Student student : filteredStudents) {
            student.updateAttendanceCounter(result);
        }

        return result;
    }

}
