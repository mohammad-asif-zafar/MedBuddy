package com.hathway.medbuddy

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hathway.medbuddy.domain.repository.IDoctorRepository
import com.hathway.medbuddy.domain.repository.IGlucoseRepository
import com.hathway.medbuddy.presentation.components.blood_pressure_components.BloodPressureAddReadingScreen
import com.hathway.medbuddy.presentation.components.blood_pressure_components.BloodPressureCalendarScreen
import com.hathway.medbuddy.presentation.components.blood_pressure_components.BloodPressureHistoryScreen
import com.hathway.medbuddy.presentation.components.blood_pressure_components.BloodPressureInsightsScreen
import com.hathway.medbuddy.presentation.components.blood_pressure_components.BloodPressureViewReadingScreen
import com.hathway.medbuddy.presentation.components.blood_pressure_components.BpDashboardScreen
import com.hathway.medbuddy.presentation.components.home_components.NavigationDrawerContent
import com.hathway.medbuddy.presentation.components.family_share_components.FamilyMemberData
import com.hathway.medbuddy.presentation.components.family_share_components.FamilyMemberManagement
import com.hathway.medbuddy.presentation.navigation.NavigationDestination
import com.hathway.medbuddy.presentation.navigation.SimpleBottomNavigationBar
import com.hathway.medbuddy.presentation.navigation_content.*
import com.hathway.medbuddy.presentation.ui.detailed_reports.*
import com.hathway.medbuddy.presentation.ui.*
import com.hathway.medbuddy.presentation.theme.MedBuddyTheme
import com.hathway.medbuddy.presentation.viewmodel.BloodPressureHomeViewModel
import com.hathway.medbuddy.presentation.viewmodel.BpDashboardViewModel
import medbuddy.composeapp.generated.resources.*
import org.jetbrains.compose.resources.*
import kotlinx.coroutines.launch

@Composable
fun App(
    repository: IGlucoseRepository? = null,
    doctorRepository: IDoctorRepository? = null,
    initialDestination: NavigationDestination = NavigationDestination.SPLASH,
    onGoogleSignInClick: () -> Unit = {}
) {
    val themeMode by ThemeManager.themeMode.collectAsState()
    val currentDestination = remember { mutableStateOf(initialDestination) }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // Handle Auth State changes to navigate to HOME or LOGIN
    LaunchedEffect(repository, doctorRepository) {
        if (currentDestination.value == NavigationDestination.LOADING && repository != null && doctorRepository != null) {
            currentDestination.value = NavigationDestination.HOME
        }
    }

    MedBuddyTheme(themeMode = themeMode) {
        ModalNavigationDrawer(
            drawerState = drawerState,
            gesturesEnabled = currentDestination.value.isVisibleInBottomBar,
            drawerContent = {
                NavigationDrawerContent(
                    currentDestination = currentDestination.value,
                    onDestinationSelected = { destination ->
                        currentDestination.value = destination
                        scope.launch { drawerState.close() }
                    },
                    onLogout = {
                        currentDestination.value = NavigationDestination.LOGIN
                        scope.launch { drawerState.close() }
                    })
            }) {
            Scaffold(
                bottomBar = {
                    if (currentDestination.value.isVisibleInBottomBar) {
                        SimpleBottomNavigationBar(
                            currentDestination = currentDestination.value,
                            onDestinationSelected = { currentDestination.value = it })
                    }
                }) { paddingValues ->
                Box(
                    modifier = Modifier.fillMaxSize().padding(paddingValues)
                ) {
                    when (currentDestination.value) {
                        NavigationDestination.SPLASH -> SplashScreen(onSplashFinished = {
                            if (FirebaseManager.currentUser != null) {
                                currentDestination.value = NavigationDestination.HOME
                            } else {
                                currentDestination.value = NavigationDestination.ONBOARDING
                            }
                        })

                        NavigationDestination.ONBOARDING -> OnboardingScreen(onFinished = {
                            currentDestination.value = NavigationDestination.LOGIN
                        })

                        NavigationDestination.LOGIN -> LoginScreen(
                            errorMessage = null, onGoogleSignInClick = {
                                currentDestination.value = NavigationDestination.LOADING
                                onGoogleSignInClick()
                            })

                        NavigationDestination.LOADING -> LoadingScreen()
                        NavigationDestination.HOME -> {
                            if (repository != null && doctorRepository != null) {
                                HomeContent(
                                    repository = repository,
                                    doctorRepository = doctorRepository,
                                    onOpenNotifications = {
                                        currentDestination.value =
                                            NavigationDestination.NOTIFICATIONS
                                    },
                                    onMenuClick = {
                                        scope.launch { drawerState.open() }
                                    },
                                    onViewAllHistory = {
                                        currentDestination.value =
                                            NavigationDestination.FULL_HISTORY
                                    })
                            }
                        }

                        NavigationDestination.HISTORY -> HistoryContent(
                            repository = repository,
                            onNavigateToAdd = {
                                currentDestination.value = NavigationDestination.ADD
                            },
                            onMenuClick = {
                                scope.launch { drawerState.open() }
                            })

                        NavigationDestination.ADD -> AddContent(
                            repository = repository,
                            onSaveSuccess = {
                                currentDestination.value = NavigationDestination.HISTORY
                            },
                            onCancel = {
                                currentDestination.value = NavigationDestination.HISTORY
                            })

                        NavigationDestination.REPORTS -> ReportsContent(
                            repository = repository,
                            onFeatureClick = { currentDestination.value = it },
                            onMenuClick = {
                                scope.launch { drawerState.open() }
                            })

                        NavigationDestination.PROFILE -> ProfileContent(
                            repository = repository,
                            doctorRepository = doctorRepository,
                            onBack = {
                                currentDestination.value = NavigationDestination.HOME
                            },
                            logout = {
                                currentDestination.value = NavigationDestination.LOGIN
                            })

                        NavigationDestination.FULL_HISTORY -> {
                            if (repository != null) {
                                FullHistoryContent(
                                    repository = repository, onBack = {
                                        currentDestination.value = NavigationDestination.HOME
                                    })
                            }
                        }

                        // Detailed Report Screens
                        NavigationDestination.AI_INSIGHTS -> AIInsightsScreen(onBack = {
                            currentDestination.value = NavigationDestination.HOME
                        })

                        NavigationDestination.MEAL_TRACKING -> MealTrackingScreen(onBack = {
                            currentDestination.value = NavigationDestination.HOME
                        })

                        NavigationDestination.MEDICATION_ADHERENCE -> MedicationAdherenceScreen(
                            onBack = { currentDestination.value = NavigationDestination.HOME })

                        NavigationDestination.HEALTH_REPORTS_DETAIL -> HealthReportsDetailScreen(
                            onBack = { currentDestination.value = NavigationDestination.HOME })

                        NavigationDestination.FAMILY_CARE -> FamilyShareContent(
                            onBack = { currentDestination.value = NavigationDestination.HOME },
                            navigation = {
                                currentDestination.value =
                                    NavigationDestination.FAMILY_MEMBER_MANAGEMENT
                            })

                        NavigationDestination.FAMILY_MEMBER_MANAGEMENT -> {
                            val mockMembers = listOf(
                                FamilyMemberData(
                                    "Sara Zafar", stringResource(Res.string.relationship_daughter)
                                ), FamilyMemberData(
                                    "Ali Zafar", stringResource(Res.string.relationship_son)
                                ), FamilyMemberData(
                                    "Noor Zafar", stringResource(Res.string.relationship_daughter)
                                )
                            )
                            FamilyMemberManagement(
                                members = mockMembers,
                                onBack = { currentDestination.value = NavigationDestination.HOME },
                                onAddNewMember = { /* Handle new member */ },
                                onMemberClick = { /* Handle member selection */ })
                        }

                        NavigationDestination.EMERGENCY_ALERTS -> EmergencyAlertsScreen(onBack = {
                            currentDestination.value = NavigationDestination.HOME
                        })

                        NavigationDestination.EXERCISE_TRACKING -> ExerciseTrackingScreen(onBack = {
                            currentDestination.value = NavigationDestination.HOME
                        })

                        NavigationDestination.WEIGHT_BMI -> WeightBMIScreen(onBack = {
                            currentDestination.value = NavigationDestination.HOME
                        })

                        // BLOOD_PRESSURE
                        NavigationDestination.BLOOD_PRESSURE -> BloodPressureContent(
                            currentDestination
                        )

                        NavigationDestination.BLOOD_PRESSURE_DASHBOARD_SCREEN -> BpDashboardScreen(
                            onBack = {
                                currentDestination.value = NavigationDestination.BLOOD_PRESSURE
                            },
                            onViewHistoryClick = {
                                currentDestination.value =
                                    NavigationDestination.BLOOD_PRESSURE_HISTORY_SCREEN
                            },
                            navigationToCalendar = {
                                currentDestination.value =
                                    NavigationDestination.BLOOD_PRESSURE_CALENDAR_SCREEN
                            },
                            navigationToAddReading ={currentDestination.value =
                                NavigationDestination.BLOOD_PRESSURE_ADD_READING_SCREEN},
                            viewModel = viewModel { BpDashboardViewModel() },
                            )

                        //
                        NavigationDestination.BLOOD_PRESSURE_ADD_READING_SCREEN -> BloodPressureAddReadingScreen(
                            onBack = {
                                currentDestination.value = NavigationDestination.BLOOD_PRESSURE_DASHBOARD_SCREEN
                            },
                            viewReading = {
                                currentDestination.value =
                                    NavigationDestination.BLOOD_PRESSURE_VIEW_READING_SCREEN
                            })

                        // View BP Reading
                        NavigationDestination.BLOOD_PRESSURE_VIEW_READING_SCREEN -> BloodPressureViewReadingScreen(
                            navigateToHomeBP = {
                                currentDestination.value =
                                    NavigationDestination.BLOOD_PRESSURE_DASHBOARD_SCREEN
                            },
                            navigateToAdd = {
                                currentDestination.value =
                                    NavigationDestination.BLOOD_PRESSURE_ADD_READING_SCREEN
                            })

                        // history
                        NavigationDestination.BLOOD_PRESSURE_HISTORY_SCREEN -> BloodPressureHistoryScreen(
                            back = {
                                currentDestination.value =
                                    NavigationDestination.BLOOD_PRESSURE_DASHBOARD_SCREEN
                            },
                            navigateToCalendar = {
                                currentDestination.value =
                                    NavigationDestination.BLOOD_PRESSURE_CALENDAR_SCREEN
                            })

                        // Insights
                        NavigationDestination.BLOOD_PRESSURE_INSIGHTS_SCREEN -> BloodPressureInsightsScreen(
                            back = {
                                currentDestination.value =
                                    NavigationDestination.BLOOD_PRESSURE
                            },
                            navigateToCalendar = {
                                currentDestination.value =
                                    NavigationDestination.BLOOD_PRESSURE_CALENDAR_SCREEN
                            })

                        NavigationDestination.BLOOD_PRESSURE_CALENDAR_SCREEN -> BloodPressureCalendarScreen(
                            back = {
                                currentDestination.value =
                                    NavigationDestination.BLOOD_PRESSURE_DASHBOARD_SCREEN
                            })
                        // End of Blood Pressure

                        // Lab Reports
                        NavigationDestination.LAB_RESULTS -> LabReportsContent(
                            onBack = { currentDestination.value = NavigationDestination.HOME },
                            navigation = { /* Handle any external lab-related navigation */ })

                        NavigationDestination.DOCTOR_APPOINTMENTS_DETAIL -> DoctorAppointmentsDetailScreen(
                            onBack = { currentDestination.value = NavigationDestination.HOME })

                        // Map screen entry cleanly into state framework
                        NavigationDestination.NOTIFICATIONS, NavigationDestination.EMERGENCY_ALERTS, NavigationDestination.ALERT_DETAILS, NavigationDestination.TAKE_ACTION, NavigationDestination.ALERT_SETTINGS, NavigationDestination.SNOOZE_REMINDER, NavigationDestination.NOTIFICATION_CHANNELS -> {
                            NotificationContent(
                                repository = repository,
                                doctorRepository = doctorRepository,
                                onBack = {
                                    // Return to previous standard screen route
                                    currentDestination.value = NavigationDestination.HOME
                                })
                        }

                        else -> {}
                    }
                }
            }
        }
    }
}
