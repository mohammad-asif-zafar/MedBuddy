package com.hathway.medbuddy

import com.hathway.medbuddy.domain.model.Language
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object LanguageManager {
    private val _language = MutableStateFlow(FirebaseManager.getLanguage())
    val language: StateFlow<Language> = _language.asStateFlow()

    fun setLanguage(language: Language) {
        FirebaseManager.setLanguage(language)
        _language.value = language
    }
}
