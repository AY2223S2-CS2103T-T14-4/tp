---
layout: page
title: User Guide
---

Teaching Assistant Assistant (TAA) is a **desktop app for managing teaching assistant activities, optimized for use via a Command Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, TAA can get your teaching assistant tasks done faster than traditional GUI apps.

* Table of Contents
  {:toc}

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `11` or above installed in your Computer.

1. Download the latest `addressbook.jar` from [here](https://github.com/AY2223S2-CS2103T-T14-4/tp/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your TAA.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar taa.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](https://user-images.githubusercontent.com/59087730/222305199-8590f0f0-7e6d-4801-bcb9-cbb2a48fa28e.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list` : Lists all students.

   * `add_student n/John Doe cl/T01 cl/L02` : Adds a contact named `John Doe` to the Class Lists `T01` and `L02`.

   * `delete 3` : Deletes the 3rd student shown in the current list.

   * `clear` : Deletes all students.

   * `exit` : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

# Features

## Attendance
For attendance, you can make the following calls:
* markAtd
* unmarkAtd

### Mark attendance: `markAtd`
Marks the attendance of a student for that week<br>
Format: `markAtd {student_number} w/{week_number}`<br>
Examples
* `markAtd 1 w/1`
* `markAtd 2 w/1`

### Unmark attendance: `unmarkAtd`
Unmarks the attendance of a student for that week<br>
Format: `unmarkAtd {student_number} w/{week_number}`<br>
Examples
* `unmarkAtd 1 w/1`
* `unmarkAtd 2 w/1`

### Participation
For Participation, you can make the following calls:
* insert

### Insert participation points: `insertPP`
Insert attendance of a student for that week<br>
Format: `insertPP {student_number} w/{week_number} pp/{points}`
Examples
* `insertPP 1 w/1 pp/200`
* `insertPP 2 w/1 pp/300`

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

### Create a class list: `create`
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

### Adding a student: `add_student`
Adds a student to a given class list.

Format: `add_student n/STUDENT_NAME cl/CLASS_NAME`
- The argument `STUDENT_NAME` should be the name of the student to be added
- The argument `CLASS_NAME` should be the name of a given class the student belongs to
- If the class list `CLASS_NAME` does not exist, it will automatically be created with student
`STUDENT_NAME` as its first student


Examples:
- `add n/Tom cl/cs2103t-t14` adds Tom to the class CS2103T-T14
- `add n/Harry cl/cs6244` adds Harry to the class CS6244

### Editing a student: `edit_student`
Edits a student in the active class list.

Format: `edit_student STUDENT_INDEX [n/STUDENT_NAME] [cl/CLASS_NAME]`
- The `STUDENT_INDEX` is the index of the student that is currently being shown on screen.
- At least one of the optional fields must be present. (i.e. either `STUDENT_NAME` and/or `CLASS_NAME`)

Examples:
- `edit_student 3 n/Barry Allen` changes the name of the 3rd student to "Barry Allen"
- `edit_student 5 cl/Tutorial_T14` assigns the 5th student only to the class "Tutorial_14"
- `edit_student 12 n/Tom Hanks cl/Lab_L11` changes the name of the 12th student to "Tom Hanks", and assigns
him to the class "Lab_L11"


### Deleting a student: `delete_student`
Deletes a student from the active class list.

Format: `delete_student STUDENT_INDEX`
- The `STUDENT_INDEX` is the index of the student that is currently being shown on screen.

Examples:
- `delete_student 3` removes the 3rd student in the currently active/displayed class list

### Listing all students : `list` 
Lists all students tracked by TAA globally.

Format: `list`


### Find a particular student : `find` [coming soon]
List the students in the class by their student number/name.

Format: `find FLAG STUDENT_NUMBER` or `find FLAG STUDENT_NAME`
- Flag -id refers to find by student number
- Flag -n refers to find by name
- The format of the command follows find flag String
- The name or student number of the student are not case-sensitive


Examples:
- find -name john lee
- find -id a0123456b


## CSV Parser

Stores and loads data in with CSV files.

* Import data in CSV format: `import`
* Export data in CSV format: `export`

Our CSV files follow the following format:
1. All CSV files are header-less. Student data has exactly 2 columns: name, tags.
2. If a student has no tags, a comma representing the tags column is still required because [if a column is defined as optional, it means that the column is required to exist, but the value can be blank.](https://www.ibm.com/docs/en/atlas-policy-suite/6.0.3?topic=files-rules-creating-populating-csv) 

Acceptable CSV format example:
```
Technoblade, Minecrafter Pig Anarchist
Meggy Spletzer,Inkling
John von Neumann,
```

### Import data in CSV format: `import` [in dev]
Import data in CSV format from file.

Format: `import [flag] [file path]`
* Flag -force overwrites records of existing students.
* Nothing is changed if file does not exist or file access denied.

### Export data in CSV format: `export` [coming soon]
Export data in CSV format to file.

Format: `export [flag] [file path]`
* If file exists, export is blocked unless -force flag is used. Otherwise, create file and export.
* Flag -force overwrites existing file.
* Nothing is changed if file access denied.

--------------------------------------------------------------------------------------------------------------------

## FAQ

_Details coming soon ..._

--------------------------------------------------------------------------------------------------------------------

## Command summary

_Details coming soon ..._
