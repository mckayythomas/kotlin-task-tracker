package src

import java.time.LocalDate

data class Task(val name: String, val dueDate: LocalDate, var completed: Boolean = false)
