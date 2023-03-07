---
layout: page
title: User Guide
---

Teaching Assistant Assistant (TAA) is a **desktop app for managing teaching assistant activities, optimized for use via a Command Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, TAA can get your teaching assistant tasks done faster than traditional GUI apps.

* Table of Contents
  {:toc}

--------------------------------------------------------------------------------------------------------------------

## Quick start
_Details coming soon ..._

## Feature summary
1. [Attendance](#Attendance)
   * mark
   * unmark
   * exit
1. [Assignments](#Assignments)
   * add
   * delete
   * grade
   * ungrade
   * exit
1. [Class List](#Class-List)
1. [CSV Parser](#csv-parser)

--------------------------------------------------------------------------------------------------------------------

# Features

## Attendance
Directs you to the attendance system. Within the attendance system, you can make the following calls:
* mark
* unmark
* exit

### Mark attendance: `mark`
Marks the attendance of a student for that week<br>
Format: `mark {student_name} {week_number}`<br>
Examples
* `mark john 1`
* `mark james 1`

### Unmark attendance: `unmark`
Unmarks the attendance of a student for that week<br>
Format: `unmark {student_name} {week_number}`<br>
Examples
* `unmark john 1`
* `unmark james 1`

### Exit attendance system: `exit`
Exits the attendance system.<br>
Format: `exit`

## Assignments

Directs you to the assignment system. Within the assignment system, you can call:
* add
* delete
* grade
* ungrade
* list
* exit

### Add Assignment: `add`
Adds an assignment with name, start_date, end_date, percent_weightage, total marks. All assignments will initially be ungraded.
<br>
Format: `Format: add {name} {start_date} {end_date} {percent_weightage} {total_marks}`<br>
Example:
* `add lab1 01-03-2023 15-03-2023 20 100`

### Delete Assignment: `delete`
Deletes the assignment of assignment_id you provided.<br>
Format: `delete {assignment_id}`<br>
Example:
* `delete 1`

### Grade Assignment: `grade`
Grades the assignment of assignment_id and student_id with a score you provide.<br>
Format: `grade {assignment_id} {student_id} {score}`<br>
Example:
* `grade 1 2 20`

### Ungrade Assignment: `ungrade`
Removes the grade of the assignment of assignment_id and student_id.<br>
Format: `ungrade {assignment_id} {student_id}`<br>
Example:
* `ungrade 1 2`

### List all assignments: `list`
Lists all assignments and their respective information.
Format: `list`

### Exit assignment system: `exit`
Exits the assignment system.<br>
Format: `exit`

## Class List
`class_list`

Directs you to the class list system.
In this system, you can call the following commands:

- Create class list: `create`
- (Random) Groupings: `rand_grp`
- Adding a student: `add`
- Deleting a student: `delete`
- Listing all students: `list`
- Find student: `find`

### Create a class list: `create` [coming soon]
Creates a class list to store the information about a group of students.

Format: `create LIST_NAME [STUDENT_NAMES]`
- The argument `LIST_NAME` should be the name of the new class list
  The argument `[STUDENT_NAMES]` should consist of a sequence of student names, separated by commas.


Examples:
- `create cs2103t-t14 Alex, John, Bonnie, Clyde` creates a class list of size 4 with 4 students: Alex, John, Bonnie and Clyde.
- `create cs6244` creates an empty class list.


### Random grouping of students: `rand_grp` [coming soon]
Forms random groups of a specified size within a given class list.

Format: `rand_grp CLASS_LIST GROUP_SIZE`
- The argument `CLASS_LIST` should be the name of a given class list
- The search for `CLASS_LIST` is case-insensitive. e.g. cs2103T will match CS2103T
- The argument `GROUP_SIZE` will determine the size of the groups to be formed.


Examples:
- `rand_grp cs2103t-t14 2` returns: Group 1: Alex, John; Group 2: Bonnie, Clyde
- `rand_grp cs2103t-t14 3` returns: Group 1: Alex, John, Clyde; Group 2: Bonnie

### Adding a student: `add` [coming soon]
Adds a student to a given class list.

Format: `add STUDENT_NAME CLASS_LIST`
The argument `STUDENT_NAME` should be the name of the student to be added
The argument `CLASS_LIST` should be the name of a given class list
The search for `CLASS_LIST` is case-insensitive. e.g. cs2103T will match CS2103T


Examples:
- `add Tom cs2103t-t14` adds Tom to the class list CS2103T-T14
- `add Harry cs6244` adds Harry to the class list CS6244


### Deleting a student: `delete` [coming soon]
Deletes a student from a given class list.

Format: `delete STUDENT_NAME CLASS_LIST`
- The argument `STUDENT_NAME` should be the name of the student to be deleted
- The search for `STUDENT_NAME` is case-insensitive. e.g. bOb will match BOB
- The argument `CLASS_LIST` should be the name of a given class list
- The search for `CLASS_LIST` is case-insensitive. e.g. cs2103T will match CS2103T


Examples:
- `delete Tom cs2103t-t14` removes Tom from the class list CS2103T-T14
- `delete Harry cs6244` removes Harry from the class list CS6244

### Listing all students : `list` [coming soon]
List the students in the class.

Format: `list CLASS_NAME`
- List the students in tutorial class indicated by the argument class name
- There should only be one string following list and nothing else.​
- The argument class name is not case-sensitive.


Examples:
 - Li Chengyue A0123456K


### Find a particular student : `find` [coming soon]
List the students in the class.

Format: `find FLAG STUDENT_NUMBER` or `find FLAG STUDENT_NAME`
- Flag -id refers to find by student number
- Flag -n refers to find by name
- The format of the command follows find flag String
- The name or student number of the student are not case-sensitive


Examples:
- find -name john lee
- find -id a0123456b


## CSV Parser

--------------------------------------------------------------------------------------------------------------------

## FAQ

_Details coming soon ..._

--------------------------------------------------------------------------------------------------------------------

## Command summary

_Details coming soon ..._
