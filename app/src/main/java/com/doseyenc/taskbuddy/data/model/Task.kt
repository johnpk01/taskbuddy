package com.doseyenc.taskbuddy.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "tasks")
data class Task(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @SerializedName("task")
    val task: String,

    @SerializedName("title")
    val title: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("sort")
    val sort: String,

    @SerializedName("wageType")
    val wageType: String,

    @SerializedName("BusinessUnitKey")
    val businessUnitKey: String,

    @SerializedName("businessUnit")
    val businessUnit: String,

    @SerializedName("parentTaskID")
    val parentTaskID: String,

    @SerializedName("preplanningBoardQuickSelect")
    val prePlanningBoardQuickSelect: String?,

    @SerializedName("colorCode")
    val colorCode: String,

    @SerializedName("workingTime")
    val workingTime: String?,

    @SerializedName("isAvailableInTimeTrackingKioskMode")
    val isAvailableInTimeTrackingKioskMode: Boolean,

    @SerializedName("isAbstract")
    val isAbstract: Boolean
)