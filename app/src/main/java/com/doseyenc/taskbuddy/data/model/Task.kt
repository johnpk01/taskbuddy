package com.doseyenc.taskbuddy.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "tasks")
data class Task(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @SerializedName("task")
    val task: String? = null,

    @SerializedName("title")
    val title: String? = null,

    @SerializedName("description")
    val description: String? = null,

    @SerializedName("sort")
    val sort: String? = null,

    @SerializedName("wageType")
    val wageType: String? = null,

    @SerializedName("BusinessUnitKey")
    val businessUnitKey: String? = null,

    @SerializedName("businessUnit")
    val businessUnit: String? = null,

    @SerializedName("parentTaskID")
    val parentTaskID: String? = null,

    @SerializedName("preplanningBoardQuickSelect")
    val prePlanningBoardQuickSelect: String? = null,

    @SerializedName("colorCode")
    val colorCode: String? = null,

    @SerializedName("workingTime")
    val workingTime: String? = null,

    @SerializedName("isAvailableInTimeTrackingKioskMode")
    val isAvailableInTimeTrackingKioskMode: Boolean = false,

    @SerializedName("isAbstract")
    val isAbstract: Boolean = false
)