package dev.falkow.blanco.nodes.home.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.falkow.blanco.nodes.home.options.GetStableContentOption
import dev.falkow.blanco.nodes.home.model.states.StableContentState
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getStableContentOption: GetStableContentOption,
) : ViewModel() {

    /** Home State. */

    var stableContentState: StableContentState by mutableStateOf(StableContentState.Loading)
        private set

    private fun getHomeContent() {
        viewModelScope.launch {
            stableContentState = StableContentState.Loading
            // try {
            stableContentState = StableContentState.Success(
                stableContent = getStableContentOption()
            )
            // } catch (e: IOException) {
            //     catalogUiState = HomeState.Error
            // } catch (e: HttpException) {
            //     catalogUiState = HomeState.Error
            // }
        }
    }

    init {
        getHomeContent()
    }
}