# Opus - User Guide

By : `Team W15-B3`  &nbsp;&nbsp;&nbsp;&nbsp; Since: `Mar 2017`  &nbsp;&nbsp;&nbsp;&nbsp; Licence: `MIT`

---

Opus is the ideal task manager for the keyboard loving *(hint: Vim/Emacs)* users. Why spend that extra 1 second to move your hand to the mouse, when you could do everything with the keyboard? Opus is developed with the mission to make command line interfaces less intimidating. Our mission is for you to stay happy while keeping track of your tasks with your trusty keyboard. \*clickity clackity tack\*

## Table of Contents

---

1. [Quick Start](#1-quick-start)
2. [Features](#2-features)
3. [FAQ](#3-faq)
4. [Command Summary](#4-command-summary)

## 1. Quick Start

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>

   > Having any Java 8 version is not enough. <br>
   > This app will not work with earlier versions of Java 8.

1. Download the latest `opus.jar` from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as the home folder for your Task Manager.
3. Double-click the file to start the app. The GUI should appear in a few seconds.
   > <img src="images/Ui.png" width="600">

4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window.
5. Let's have a look at how Jim, an avid Opus fan, utilises the task manager:
   * **`add`**` Do laundry p/low b/31/12/2017 12:00` : Jim wants to do laundry at noon, so he adds it to the task manager.
   * **`sort`**` priority` : Jim wants to see the most urgent tasks at the top of his list.
   * **`mark`**` 1 2` : Jim realises he has completed the 1st and 2nd task so he marks them as completed.
   * **`delete`**` 3` : Jim realises he does not have to complete the 3rd task so he deletes it from his current list.
   * **`find`**` laundry` : Jim gets a call from his mum that the laundry must be completed by 3pm so he does a search for it.
   * **`edit`**` 1 p/hi` : Jim updates the priority of the found laundry task to high.
   * **`list`** : Jim wants to get back to the overview of his list of tasks.
   * **`exit`** : Jim is done for the day so he decides to exit the application!
6. Refer to the [Features](#2-features) section below for details of each command.<br>


## 2. Features

> **Command Format**
>
> * Words in `UPPER_CASE` are the parameters.
> * Items in `SQUARE_BRACKETS` are optional.
> * Items with `...` after them can have multiple instances.
> * Parameters can be in any order.

### 2.1. Viewing help : `help`

Format: `help`

> Help is shown only if you enter `help`

### 2.2. Adding a task: `add`

Adds a person to the task manager.<br>
Format: `add NAME [n/NOTES] [s/STATUS] [b/STARTIME] [e/ENDTIME] [p/PRIORITY] [t/TAG]...`

> * Task can be an event
> * Task can have notes
> * Task can have a a status
> * Task can have a deadline
> * Task can have a priority ranking of hi, mid, low
> * Task can have any number of tags (including 0)

Examples:

* `add Do laundry`
* `add Finish v0.0 documentation n/Urgent s/incomplete b/28/02/2017 12:00 e/29/02/2017 23:59 p/hi t/CS2103T`

### 2.3. Sort tasks : `sort` (Work in progress)

Sorts the list of tasks currently being shown according to the parameters.<br>
Format: `sort [s/STATUS] [b/STARTIME] [e/ENDTIME] [p/PRIORITY]...`

> * At least one of the optional fields must be provided.

Examples:

* `sort status` <br>
Sorts the list of tasks according to status
* `sort status priority`<br>
Sorts the list of tasks according to status, followed by priority.

### 2.4. Editing a task : `edit`

Edits an existing task in Opus.<br>
Format: `edit INDEX [NAME] [n/NOTES] [b/STARTIME] [e/ENDTIME] [p/PRIORITY] [t/TAG]...`

> * Edits the task at the specified `INDEX`.
    The index refers to the index number shown in the last task listing.<br>
    The index **must be a positive integer** 1, 2, 3, ...
> * At least one of the optional fields must be provided.
> * Existing values will be updated to the input values.
> * When editing tags, the existing tags of the task will be removed i.e adding of tags is not cumulative.
> * You can remove all the task's tags by typing `t/` without specifying any tags after it.

Examples:

* `edit 1 Finish tutorial exercises`<br>
Edits the name of the 1st task to `Finish tutorial exercises`.

### 2.5. Mark task

Marks task as complete.<br>
Format: `mark [INDEX]...`

> * Marks the task at all the specified `INDEX` as complete.
    The index refers to the index number shown in the last task listing.<br>
    The index **must be a positive integer** 1, 2, 3, ...

Examples:

* `mark 3 4 5`<br>
Marks tasks at index 3, 4 and 5 as complete.

### 2.6. Unmark task

Marks task as incomplete.<br>
Format: `unmark INDEX...`

> * Marks the task at all the specified `INDEX` as incomplete.
    The index refers to the index number shown in the last task listing.<br>
    The index **must be a positive integer** 1, 2, 3, ...

Examples:

* `unmark 3 4 5`<br>
Marks tasks at index 3, 4 and 5 as incomplete.

### 2.7. Schedule (Work in progress)

Set an event or a deadline.<br>
Format: `schedule INDEX [STARTIME] ENDTIME`

> * Set the start time and the end time of the task.
    The index refers to the index number shown in the last task listing.<br>
    The index **must be a positive integer** 1, 2, 3, ...

Examples:

* `schedule 6 12/05/2017-13:00 12/05/2017-15:00`<br>
Set the start time as 1pm on May 12, 2017 and the end time as 3pm on May 12, 2017.

### 2.8. Undo

Undo the latest command.<br>
Format: `undo`

> * Reverts the change done by the previous command.

### 2.9. Redo

Reverts the previous undo action.<br>
Format: `redo`

> * Reverts the change done by the previous undo action.

### 2.10. Find

Displays a list of tasks based on keywords and/or tags.<br>
Format: `find [NAME] [s/STATUS] [b/STARTIME] [e/ENDTIME] [p/PRIORITY] [t/TAG]...`

> * Displays the list of tasks matching the search parameters
> * `find` without parameters will display the help section for the command

### 2.11. Autocomplete (Work in progress)

Autocomplete the user's command on the CLI.

> * Example:
    User enters `f` and presses `TAB`, `find` command will appear in the input box.

## 3. FAQ

**Q**: Where do I download the latest under development version of Opus?
**A**: You may download the latest unstable release of our application [here](../../../releases), but we strongly recommend using the stable build for your own sanity.

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with
       the file that contains the data of your previous Address Book folder.

## 4. Command Summary

* **Add**  `add NAME [n/NOTES] [p/PRIORITY] [b/STARTIME] [e/ENDTIME] [t/TAG]...` <br>
  e.g. `add Do laundry n/from the blue basket p/hi b/23/3/2018 12:00 e/23/3/2018 13:00 t/friend t/colleague`

* **Clear** : `clear`

* **Delete** : `delete INDEX` <br>
   e.g. `delete 3`

* **Edit**  `edit INDEX NAME [n/NOTES] [p/PRIORITY] [b/STARTIME] [e/ENDTIME] [t/TAG]...` <br>
  e.g. `edit 2 Prepare dinner n/for 4 pax p/hi b/23/4/2017 12:00 e/23/3/2018 13:00 t/friend t/colleague`

* **Find** : `find KEYWORD [MORE_KEYWORDS]` <br>
  e.g. `find Wash dishes`

* **Help** : `help` <br>

* **List** : `list` <br>

* **Mark** : `mark [INDEX]` <br>
  e.g. `mark 1`

* **Redo** : `redo` <br>

* **Schedule** : `schedule INDEX ENDTIME` <br>
  e.g. `schedule 6 30/3/2017`

* **Select** : `select INDEX` <br>
  e.g.`select 2`

* **Unmark** : `unmark [INDEX]` <br>
  e.g. `unmark 1`

* **Undo** : `undo` <br>
