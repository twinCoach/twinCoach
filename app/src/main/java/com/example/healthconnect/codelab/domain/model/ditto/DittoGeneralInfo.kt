package com.example.healthconnect.codelab.domain.model.ditto

import com.example.healthconnect.codelab.BuildConfig
import com.example.healthconnect.codelab.data.model.ditto.DittoGeneralInfoModel
import java.io.Serializable
import java.time.LocalDate

class DittoGeneralInfo {

    data class Thing(
        var thingId: String,
        var policyId: String? = null,
        var attributes: Attributes,
        var features: Features
    )

    data class Attributes(
        var gender: Int,
        var height: Int,
        var weight: Double,
        var birthYear: Int,
        var runningYear: Int
    )

    data class Features(
        var goal: Goal,
        val trainingPlan: TrainingPlan,
        var suggestions: Suggestions,
        var preferences: Preferences
    )

    data class Goal(
        var distance: Int,
        var seconds: Int,
        var estimation: Int,
        var date: LocalDate
    )

    data class TrainingPlan(
        val sessions: List<TrainingSession>
    )

    data class TrainingSession(
        val day: LocalDate,
        val distance: Int,
        val times: Int,
        val rest: Int,
        val expectedTime: Int,
        val meanHeartRate: Int
    ) : Serializable

    data class Suggestions(
        val suggestions: List<Goal>
    )

    data class Preferences(
        var trainingDays: List<Int>
    )

    companion object {
        fun thingId(googleId: String): String {
            return BuildConfig.DITTO_THING_PREFIX + ":" + googleId
        }
    }
}

fun DittoGeneralInfoModel.Thing.toDomain(): DittoGeneralInfo.Thing =
    DittoGeneralInfo.Thing(thingId!!, policyId, attributes.toDomain(), features.toDomain())

fun DittoGeneralInfoModel.Attributes.toDomain(): DittoGeneralInfo.Attributes =
    DittoGeneralInfo.Attributes(gender, height, weight, birthYear, runningYear)

fun DittoGeneralInfoModel.Features.toDomain(): DittoGeneralInfo.Features =
    DittoGeneralInfo.Features(
        goal.properties.toDomain(),
        trainingPlan.toDomain(),
        suggestions.toDomain(),
        preferences.toDomain()
    )

fun DittoGeneralInfoModel.GoalProperties.toDomain(): DittoGeneralInfo.Goal =
    DittoGeneralInfo.Goal(
        distance,
        seconds,
        estimation,
        LocalDate.parse(date)
    )

fun DittoGeneralInfoModel.TrainingPlan.toDomain(): DittoGeneralInfo.TrainingPlan =
    DittoGeneralInfo.TrainingPlan(properties.sessions.map { it.toDomain() })

fun DittoGeneralInfoModel.TrainingPlanProperties.toDomain(): DittoGeneralInfo.TrainingPlan =
    DittoGeneralInfo.TrainingPlan(sessions.map { it.toDomain() })

fun DittoGeneralInfoModel.TrainingSession.toDomain(): DittoGeneralInfo.TrainingSession =
    DittoGeneralInfo.TrainingSession(
        LocalDate.parse(day), distance, times, rest, expectedTime, meanHeartRate
    )

fun DittoGeneralInfoModel.Suggestions.toDomain(): DittoGeneralInfo.Suggestions =
    DittoGeneralInfo.Suggestions(properties.suggestions.map { it.toDomain() })

fun DittoGeneralInfoModel.Preferences.toDomain(): DittoGeneralInfo.Preferences =
    DittoGeneralInfo.Preferences(properties.trainingDays)