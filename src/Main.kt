package src

import java.io.File
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun main() {
    // Loop for running program until done
    while (true) {
        // print menu
        println("Task Manager Menu:")
        println("1. Add a New Task")
        println("2. Delete a Task")
        println("3. Show all tasks")
        println("4. Mark a Task Complete")
        println("5. Show all Completed Tasks")
        println("6. Exit")
        println("Enter Your Choice:")

        // Logic switch for handling user input
        when (readln()) {
            "1" -> addTask()
            "2" -> deleteTask()
            "3" -> getTasks()
            "4" -> markCompleted()
            "5" -> getCompleted()
            "6" -> return
            else -> {
                println("Invalid Choice. Please select a valid menu item. \nPress any key to continue...")
                readln()
            }
        }
    }
}

// Write to file function adding task
fun addTask() {
    val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")

    println("Enter the task name:")
    var taskName: String = readln()

    while (taskName.isEmpty()) {
        println("Please enter a Task Name:")
        taskName = readln()
    }

    var validDate = false

    println("Enter task Due Date (dd-MM-yyyy):")
    var dateInput = readln()

    var taskDate: LocalDate = LocalDate.now()

    // Check user input
    while (!validDate) {
        try {
            taskDate = LocalDate.parse(dateInput, dateFormatter)
            validDate = true
        } catch (e: Exception) {
            println("Invalid date Format. \nPlease enter a proper date:")
            dateInput = readln()
        }
    }

    val newTask = Task(taskName, taskDate)
    // Write task to file
    File("C:\\Users\\mckay\\IdeaProjects\\kotlin-task-tracker\\src\\tasks.txt").appendText("${newTask.name},${newTask.dueDate},${newTask.completed}\n")
}

// Read File function Viewing tasks
fun getTasks() {
    val tasks = mutableListOf<Task>()
    val file = File("C:\\Users\\mckay\\IdeaProjects\\kotlin-task-tracker\\src\\tasks.txt")
    file.bufferedReader().useLines { lines ->
        lines.forEach { line ->
            val parts = line.split(",")
            if (parts.size == 3) {
                val name = parts[0]
                val dueDate = LocalDate.parse(parts[1])
                val isCompleted = parts[2].toBoolean()
                tasks.add(Task(name, dueDate, isCompleted))
            }
        }
    }

    tasks.forEachIndexed { index, task ->
        println("${index + 1}: ${task.name} | Due: ${task.dueDate} | ${if (!task.completed) "Incomplete" else "Complete"}")
    }
    println("Press Any Key to Continue...")
    readln()
}

// Deleting task
fun deleteTask() {
    println("Task number to delete:")
    val deletedTaskIndex = readlnOrNull()?.toIntOrNull()

    if (deletedTaskIndex != null) {
        // Get tasks
        val tasks = mutableListOf<Task>()
        val file = File("C:\\Users\\mckay\\IdeaProjects\\kotlin-task-tracker\\src\\tasks.txt")

        file.bufferedReader().useLines { lines ->
            lines.forEach { line ->
                val parts = line.split(",")
                if (parts.size == 3) {
                    val name = parts[0]
                    val dueDate = LocalDate.parse(parts[1])
                    val isCompleted = parts[2].toBoolean()
                    tasks.add(Task(name, dueDate, isCompleted))
                }
            }
        }

        if (deletedTaskIndex >= 1 && deletedTaskIndex <= tasks.size) {
            val updatedTasks = tasks.filterIndexed { index, _ -> index != deletedTaskIndex - 1 }

            // Write the updated tasks back to the file
            file.writeText(updatedTasks.joinToString("\n") { "${it.name},${it.dueDate},${it.completed}\n" })

            println("Task deleted successfully.")
            println("Enter any key to continue...")
            readln()
        } else {
            println("Invalid task number. No task deleted.")
            println("Enter any key to continue...")
            readln()
        }
    } else {
        println("Invalid input. Please enter a valid task number.")
        println("Enter any key to continue...")
        readln()
    }
}


// Completing task
fun markCompleted() {
    // Get input
    val completedTask = readln().toIntOrNull()

    // Get tasks
    val tasks = mutableListOf<Task>()
    val file = File("C:\\Users\\mckay\\IdeaProjects\\kotlin-task-tracker\\src\\tasks.txt")

    file.bufferedReader().useLines { lines ->
        lines.forEach { line ->
            val parts = line.split(",")
            if (parts.size == 3) {
                val name = parts[0]
                val dueDate = LocalDate.parse(parts[1])
                val isCompleted = parts[2].toBoolean()
                tasks.add(Task(name, dueDate, isCompleted))
            }
        }
    }

    if (completedTask != null) {
        if (completedTask >= 1 && completedTask <= tasks.size) {
            tasks[completedTask - 1].completed = true

            // Write the updated tasks back to the file
            file.writeText(tasks.joinToString("\n") { "${it.name},${it.dueDate},${it.completed}\n" })

            println("Task marked as completed.")
            println("Enter any key to continue...")
            readln()
        } else {
            println("Invalid task number. No task marked as completed.")
            println("Enter any key to continue...")
            readln()
        }
    }
}


// Reading completed Tasks
fun getCompleted() {
    val tasks = mutableListOf<Task>()
    val file = File("C:\\Users\\mckay\\IdeaProjects\\kotlin-task-tracker\\src\\tasks.txt")
    file.bufferedReader().useLines { lines ->
        lines.forEach { line ->
            val parts = line.split(",")
            if (parts.size == 3) {
                val name = parts[0]
                val dueDate = LocalDate.parse(parts[1])
                val isCompleted = parts[2].toBoolean()
                tasks.add(Task(name, dueDate, isCompleted))
            }
        }
    }

    tasks.forEachIndexed { index, task ->
        if (task.completed) println("${index + 1}: ${task.name} | Due: ${task.dueDate} | Completed")
    }
    println("Press Any Key to Continue...")
    readln()
}

