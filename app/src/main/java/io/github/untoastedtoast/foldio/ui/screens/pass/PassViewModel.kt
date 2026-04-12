package io.github.untoastedtoast.foldio.ui.screens.pass

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import io.github.untoastedtoast.foldio.model.Pass
import io.github.untoastedtoast.foldio.model.Tag
import io.github.untoastedtoast.foldio.persistence.BarcodePosition
import io.github.untoastedtoast.foldio.persistence.PassStore
import io.github.untoastedtoast.foldio.persistence.SettingsStore
import io.github.untoastedtoast.foldio.persistence.tag.TagRepository

@HiltViewModel
class PassViewModel
    @Inject
    constructor(
        application: Application,
        private val passStore: PassStore,
        private val tagRepository: TagRepository,
        val settingsStore: SettingsStore,
    ) : AndroidViewModel(application) {
        val allTags = tagRepository.all()

        fun passFlowById(id: String) = passStore.passFlowById(id)

        fun addTag(tag: Tag) = viewModelScope.launch(Dispatchers.IO) { tagRepository.insert(tag) }

        fun tag(
            pass: Pass,
            tag: Tag,
        ) = viewModelScope.launch(Dispatchers.IO) { passStore.tag(pass, tag) }

        fun untag(
            pass: Pass,
            tag: Tag,
        ) = viewModelScope.launch(Dispatchers.IO) { passStore.untag(pass, tag) }

        suspend fun update(pass: Pass) = passStore.update(pass)

        fun delete(pass: Pass) = viewModelScope.launch(Dispatchers.IO) { passStore.delete(pass) }

        fun archive(pass: Pass) = viewModelScope.launch(Dispatchers.IO) { passStore.archive(pass) }

        fun unarchive(pass: Pass) = viewModelScope.launch(Dispatchers.IO) { passStore.unarchive(pass) }

        fun barcodePosition(): BarcodePosition = settingsStore.barcodePosition()

        fun increasePassViewBrightness(): Boolean = settingsStore.increasePassViewBrightness()

        fun toggleLegacyRendering(pass: Pass) = viewModelScope.launch(Dispatchers.IO) { passStore.toggleLegacyRendering(pass) }
    }
