package com.hathway.medbuddy.presentation.navigation

import medbuddy.composeapp.generated.resources.*
import org.jetbrains.compose.resources.DrawableResource

enum class NavigationDestination(
    val icon: DrawableResource? = null,
    val isFloatingActionButton: Boolean = false,
    val isVisibleInBottomBar: Boolean = true
) {
    HOME(
        icon = Res.drawable.ic_nav_home
    ),
    HISTORY(
        icon = Res.drawable.ic_nav_history
    ),

    ADD(
        icon = Res.drawable.ic_nav_add, isFloatingActionButton = true
    ),
    REPORTS(
        icon = Res.drawable.ic_nav_reports
    ),
    PROFILE(
        icon = Res.drawable.ic_nav_profile
    ),

    FULL_HISTORY(isVisibleInBottomBar = false),

    NOTIFICATIONS(
        icon = null, isVisibleInBottomBar = false
    ),

    // Detailed Report Screens
    AI_INSIGHTS(isVisibleInBottomBar = false),
    MEAL_TRACKING(isVisibleInBottomBar = false),
    MEDICATION_ADHERENCE(isVisibleInBottomBar = false),
    HEALTH_REPORTS_DETAIL(isVisibleInBottomBar = false),
    FAMILY_CARE(isVisibleInBottomBar = false),
    EMERGENCY_ALERTS(isVisibleInBottomBar = false),
    EXERCISE_TRACKING(isVisibleInBottomBar = false),
    WEIGHT_BMI(isVisibleInBottomBar = false),
    BLOOD_PRESSURE(isVisibleInBottomBar = false),
    //BpDashboardScreen
    BLOOD_PRESSURE_DASHBOARD_SCREEN(isVisibleInBottomBar = false),

    // BloodPressureAddReadingScreen
    BLOOD_PRESSURE_ADD_READING_SCREEN(isVisibleInBottomBar = false),

    //BloodPressureInsightsScreen
    BLOOD_PRESSURE_INSIGHTS_SCREEN(isVisibleInBottomBar = false),

    //BloodPressureHistoryScreen
    BLOOD_PRESSURE_HISTORY_SCREEN(isVisibleInBottomBar = false),

    //BloodPressureHistoryScreen
    BLOOD_PRESSURE_CALENDAR_SCREEN(isVisibleInBottomBar = false),

    //BloodPressureViewReadingScreen
    BLOOD_PRESSURE_VIEW_READING_SCREEN(isVisibleInBottomBar = false),


    LAB_RESULTS(isVisibleInBottomBar = false),
    DOCTOR_APPOINTMENTS_DETAIL(isVisibleInBottomBar = false),
    FAMILY_MEMBER_MANAGEMENT(isVisibleInBottomBar = false),

    // Support & Preferences
    PREFERENCES_HELP(isVisibleInBottomBar = false),

    // App Startup Flow
    SPLASH(isVisibleInBottomBar = false),
    ONBOARDING(isVisibleInBottomBar = false),
    LOGIN(isVisibleInBottomBar = false),
    LOADING(isVisibleInBottomBar = false),

    // Notification & Alerts Flow
    ALERT_DETAILS(isVisibleInBottomBar = false),
    TAKE_ACTION(isVisibleInBottomBar = false),
    ALERT_SETTINGS(isVisibleInBottomBar = false),
    SNOOZE_REMINDER(isVisibleInBottomBar = false),
    NOTIFICATION_CHANNELS(isVisibleInBottomBar = false)
}
