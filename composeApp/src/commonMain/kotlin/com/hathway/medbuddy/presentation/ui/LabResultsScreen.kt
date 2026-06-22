package com.hathway.medbuddy.presentation.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.hathway.medbuddy.domain.model.LabFlowState
import com.hathway.medbuddy.domain.model.LabResultData
import com.hathway.medbuddy.presentation.components.lab_reports_components.AddLabResultChoice
import com.hathway.medbuddy.presentation.components.lab_reports_components.AddLabResultForm
import com.hathway.medbuddy.presentation.components.lab_reports_components.LabExportScreen
import com.hathway.medbuddy.presentation.components.lab_reports_components.LabFilterScreen
import com.hathway.medbuddy.presentation.components.lab_reports_components.LabInsightsScreen
import com.hathway.medbuddy.presentation.components.lab_reports_components.LabOCRScreen
import com.hathway.medbuddy.presentation.components.lab_reports_components.LabResultDetails
import com.hathway.medbuddy.presentation.components.lab_reports_components.LabResultSuccess
import com.hathway.medbuddy.presentation.components.lab_reports_components.LabResultsHome
import com.hathway.medbuddy.presentation.components.lab_reports_components.LabResultsList
import com.hathway.medbuddy.presentation.components.lab_reports_components.LabShareScreen
import com.hathway.medbuddy.presentation.components.lab_reports_components.LabTopBar
import com.hathway.medbuddy.presentation.components.lab_reports_components.LabTrendsScreen
import com.hathway.medbuddy.presentation.components.lab_reports_components.LabUploadScreen
import medbuddy.composeapp.generated.resources.Res
import medbuddy.composeapp.generated.resources.lab_action_upload
import medbuddy.composeapp.generated.resources.lab_add_title
import medbuddy.composeapp.generated.resources.lab_details_title
import medbuddy.composeapp.generated.resources.lab_export_title
import medbuddy.composeapp.generated.resources.lab_filter_title
import medbuddy.composeapp.generated.resources.lab_insights_title
import medbuddy.composeapp.generated.resources.lab_ocr_title
import medbuddy.composeapp.generated.resources.lab_results_title_main
import medbuddy.composeapp.generated.resources.lab_share_title
import medbuddy.composeapp.generated.resources.lab_trends_title
import org.jetbrains.compose.resources.stringResource


@Composable
fun LabResultsScreen(onBack: () -> Unit, navigation: () -> Unit) {
    var currentState by remember { mutableStateOf(LabFlowState.HOME) }
    var selectedResult by remember { mutableStateOf<LabResultData?>(null) }

    val backHandler = {
        if (currentState == LabFlowState.HOME) {
            onBack()
        } else {
            currentState = when (currentState) {
                LabFlowState.ADD_CHOICE -> LabFlowState.HOME
                LabFlowState.ADD_FORM -> LabFlowState.ADD_CHOICE
                LabFlowState.SUCCESS -> LabFlowState.HOME
                LabFlowState.LIST -> LabFlowState.HOME
                LabFlowState.DETAILS -> LabFlowState.LIST
                LabFlowState.TRENDS -> LabFlowState.HOME
                LabFlowState.FILTER -> LabFlowState.LIST
                LabFlowState.EXPORT -> LabFlowState.LIST
                LabFlowState.SHARE -> LabFlowState.DETAILS
                LabFlowState.INSIGHTS -> LabFlowState.DETAILS
                LabFlowState.UPLOAD -> LabFlowState.ADD_CHOICE
                LabFlowState.OCR -> LabFlowState.UPLOAD
                else -> LabFlowState.HOME
            }
        }
    }

    Scaffold(
        topBar = {
            LabTopBar(
                title = when (currentState) {
                    LabFlowState.HOME -> stringResource(Res.string.lab_results_title_main)
                    LabFlowState.ADD_CHOICE -> stringResource(Res.string.lab_add_title)
                    LabFlowState.ADD_FORM -> stringResource(Res.string.lab_add_title)
                    LabFlowState.SUCCESS -> ""
                    LabFlowState.LIST -> stringResource(Res.string.lab_results_title_main)
                    LabFlowState.DETAILS -> stringResource(Res.string.lab_details_title)
                    LabFlowState.TRENDS -> stringResource(Res.string.lab_trends_title)
                    LabFlowState.FILTER -> stringResource(Res.string.lab_filter_title)
                    LabFlowState.EXPORT -> stringResource(Res.string.lab_export_title)
                    LabFlowState.SHARE -> stringResource(Res.string.lab_share_title)
                    LabFlowState.INSIGHTS -> stringResource(Res.string.lab_insights_title)
                    LabFlowState.UPLOAD -> stringResource(Res.string.lab_action_upload)
                    LabFlowState.OCR -> stringResource(Res.string.lab_ocr_title)
                }, onBack = backHandler, showBack = currentState != LabFlowState.SUCCESS
            )
        }, containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Box(modifier = Modifier.padding(padding).fillMaxSize()) {
            AnimatedContent(
                targetState = currentState, transitionSpec = {
                    fadeIn() togetherWith fadeOut()
                }) { state ->
                when (state) {
                    LabFlowState.HOME -> LabResultsHome(
                        onAddClick = {
                            currentState = LabFlowState.ADD_CHOICE
                        },
                        onViewAllClick = { currentState = LabFlowState.LIST },
                        onTrendsClick = { currentState = LabFlowState.TRENDS })

                    LabFlowState.ADD_CHOICE -> AddLabResultChoice(
                        onManualClick = {
                            currentState = LabFlowState.ADD_FORM
                        },
                        onUploadClick = { currentState = LabFlowState.UPLOAD },
                        onOCRClick = { currentState = LabFlowState.OCR },
                        onCancel = { currentState = LabFlowState.HOME })

                    LabFlowState.ADD_FORM -> AddLabResultForm(
                        onSave = { currentState = LabFlowState.SUCCESS })

                    LabFlowState.SUCCESS -> LabResultSuccess(
                        onViewResults = {
                            currentState = LabFlowState.LIST
                        },
                        onAddAnother = { currentState = LabFlowState.ADD_CHOICE })

                    LabFlowState.LIST -> LabResultsList(
                        onResultClick = {
                            selectedResult = it
                            currentState = LabFlowState.DETAILS
                        },
                        onAddClick = { currentState = LabFlowState.ADD_CHOICE },
                        onFilterClick = { currentState = LabFlowState.FILTER })

                    LabFlowState.DETAILS -> LabResultDetails(
                        result = selectedResult,
                        onShareClick = { currentState = LabFlowState.SHARE },
                        onInsightsClick = { currentState = LabFlowState.INSIGHTS })

                    LabFlowState.TRENDS -> LabTrendsScreen()
                    LabFlowState.FILTER -> LabFilterScreen(onApply = {
                        currentState = LabFlowState.LIST
                    })

                    LabFlowState.EXPORT -> LabExportScreen()
                    LabFlowState.SHARE -> LabShareScreen()
                    LabFlowState.INSIGHTS -> LabInsightsScreen()
                    LabFlowState.UPLOAD -> LabUploadScreen(onNext = {
                        currentState = LabFlowState.OCR
                    })

                    LabFlowState.OCR -> LabOCRScreen(onSave = {
                        currentState = LabFlowState.SUCCESS
                    })
                }
            }
        }
    }
}



