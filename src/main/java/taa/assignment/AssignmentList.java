package taa.assignment;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.collections.transformation.FilteredList;
import taa.assignment.exceptions.AssignmentNotFoundException;
import taa.assignment.exceptions.InvalidGradeException;
import taa.assignment.exceptions.SubmissiontNotFoundException;
import taa.model.student.Student;

/**
 * List of assignments
 */
public class AssignmentList {
    private final ArrayList<Assignment> assignments = new ArrayList<>();
    private final HashMap<String, Assignment> assignmentMap = new HashMap<>();

    /**
     * @param assignmentName
     * @param sl
     */
    public void add(String assignmentName, FilteredList<Student> sl) throws AssignmentNotFoundException {
        if (assignmentMap.containsKey(assignmentName)) {
            throw new AssignmentNotFoundException("Duplicate assignment name: " + assignmentName);
        } else {
            Assignment a = new Assignment(assignmentName, sl);
            assignments.add(a);
            assignmentMap.put(assignmentName, a);
        }
    }

    /**
     * @param assignmentName
     */
    public void delete(String assignmentName) throws AssignmentNotFoundException {
        if (!assignmentMap.containsKey(assignmentName)) {
            throw new AssignmentNotFoundException("Assignment: " + assignmentName + " not found");
        } else {
            Assignment removed = assignmentMap.remove(assignmentName);
            removed.delete();
            assignments.remove(removed);
        }
    }

    /**
     * @param assignmentName
     * @param student
     * @param marks
     */
    public void grade(String assignmentName, Student student, int marks) throws AssignmentNotFoundException, SubmissiontNotFoundException, InvalidGradeException {
        if (!assignmentMap.containsKey(assignmentName)) {
            throw new AssignmentNotFoundException("Assignment: " + assignmentName + " not found");
        } else {
            assignmentMap.get(assignmentName).gradeSubmission(student, marks);
        }
    }

    public void ungrade(String assignmentName, Student student) throws AssignmentNotFoundException, SubmissiontNotFoundException {
        if (!assignmentMap.containsKey(assignmentName)) {
            throw new AssignmentNotFoundException("Assignment: " + assignmentName + " not found");
        } else {
            assignmentMap.get(assignmentName).ungradeSubmission(student);
        }
    }

    /**
     * @return A list of all assignments and submissions
     */
    public String list() {
        StringBuilder sb = new StringBuilder();
        for (Assignment a : assignments) {
            sb.append("Assignment " + a + ":\n");
            for (Submission s : a.getSubmissions()) {
                sb.append("  " + s + "\n");
            }
        }
        return sb.toString();
    }
}
