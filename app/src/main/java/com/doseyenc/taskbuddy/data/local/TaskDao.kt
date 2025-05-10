package com.doseyenc.taskbuddy.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.doseyenc.taskbuddy.data.model.Task

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTasks(tasks: List<Task>)

    @Query("SELECT * FROM tasks")
    suspend fun getAllTasks(): List<Task>

    @Query("DELETE FROM tasks")
    suspend fun deleteAllTasks()

    @Query(
        """
    SELECT * FROM tasks WHERE 
    LOWER(task) LIKE '%' || LOWER(:query) || '%' OR
    LOWER(title) LIKE '%' || LOWER(:query) || '%' OR
    LOWER(description) LIKE '%' || LOWER(:query) || '%' OR
    LOWER(colorCode) LIKE '%' || LOWER(:query) || '%' OR
    LOWER(businessUnitKey) LIKE '%' || LOWER(:query) || '%' OR
    LOWER(parentTaskID) LIKE '%' || LOWER(:query) || '%'
"""
    )
    suspend fun searchTasks(query: String): List<Task>
}