package com.sameerasw.doodlist.data

import kotlinx.coroutines.flow.Flow

class Repository(private val db: AppDatabase) {
    fun getTasks(): Flow<List<TaskEntity>> = db.taskDao().getAll()
    suspend fun insertTask(task: TaskEntity): Long = db.taskDao().insert(task)
    suspend fun updateTask(task: TaskEntity) = db.taskDao().update(task)
    suspend fun deleteTask(id: Long) = db.taskDao().delete(id)

    fun getStrokesForTask(taskId: Long): Flow<List<StrokeEntity>> = db.strokeDao().getForTask(taskId)
    suspend fun insertStroke(stroke: StrokeEntity): Long = db.strokeDao().insert(stroke)
    suspend fun deleteStrokesForTask(taskId: Long) = db.strokeDao().deleteForTask(taskId)
}

