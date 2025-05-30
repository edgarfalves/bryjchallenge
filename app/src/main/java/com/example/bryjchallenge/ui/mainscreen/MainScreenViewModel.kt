package com.example.bryjchallenge.ui.mainscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bryjchallenge.data.ErrorMessage
import com.example.bryjchallenge.data.ListRepository
import com.example.bryjchallenge.data.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface MainScreenUIState {
    val isLoading: Boolean
    val errorMessages: List<ErrorMessage>

    data class NoFeed(
        override val isLoading: Boolean,
        override val errorMessages: List<ErrorMessage>
    ) :
        MainScreenUIState

    data class HasFeed(
        val listFeed: List<String>,
        override val isLoading: Boolean,
        override val errorMessages: List<ErrorMessage>,
    ) : MainScreenUIState
}

private data class MainScreenViewModelState(
    val listFeed: List<String>? = null,
    val isLoading: Boolean = false,
    val errorMessages: List<ErrorMessage> = emptyList(),
) {
    fun toUiState(): MainScreenUIState = if (listFeed == null) {
        MainScreenUIState.NoFeed(
            isLoading = isLoading,
            errorMessages = errorMessages,
        )
    } else {
        MainScreenUIState.HasFeed(
            listFeed = listFeed,
            // Determine the selected post. This will be the post the user last selected.
            // If there is none (or that post isn't in the current feed), default to the
            // highlighted post
            isLoading = isLoading,
            errorMessages = errorMessages,
        )
    }
}

@HiltViewModel
class MainScreenViewModel @Inject constructor(private val listRepository: ListRepository) :
    ViewModel() {
    private val viewModelState = MutableStateFlow(
        MainScreenViewModelState(
            isLoading = true,
        )
    )

    // UI state exposed to the UI
    val uiState = viewModelState
        .map(MainScreenViewModelState::toUiState)
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toUiState(),
        )

    init {
        refreshList()
    }

    private fun refreshList() {
        viewModelState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val result = listRepository.provideList()
            /*val result = listRepository.provideErrorList()*/
            viewModelState.update {
                when (result) {
                    is Result.Success -> it.copy(listFeed = result.data, isLoading = false)
                    is Result.Error -> {
                        it.copy(
                            errorMessages = listOf(
                                ErrorMessage(result.exception.toString())
                            ),
                            isLoading = false,
                        )
                    }
                }
            }
        }
    }
}