package dev.falkow.blanco.nodes.order.model

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.falkow.blanco.nodes.order.model.states.StableContentState
import dev.falkow.blanco.nodes.order.options.GetStableContentOption
import dev.falkow.blanco.shared.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class OrderViewModel @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    savedStateHandle: SavedStateHandle,
    private val getStableContentOption: GetStableContentOption,
) : ViewModel() {

    /** Arguments From Navigation. */

    private val orderNavId: String = checkNotNull(savedStateHandle["orderNavId"])
    private val queryString: String = checkNotNull(savedStateHandle["queryString"])

    // init {
    //     println(queryString)
    // }


    /** Stable Content. */

    private val _stableContentState = MutableStateFlow<StableContentState?>(null)
    val stableContentState: StateFlow<StableContentState?> = _stableContentState.asStateFlow()


    /** Initialization Block. */

    fun doStartInitialization(): Unit {
        if (_stableContentState.value is StableContentState.Loading) return
        _stableContentState.value = StableContentState.Loading
        viewModelScope.launch(ioDispatcher) {
            try {
                // println("START")
                // delay(1_000)
                // throw Exception("CRASH SIMULATION")
                val stableContent = getStableContentOption(orderNavId, null)
                _stableContentState.value = StableContentState.Success(
                    success = stableContent
                )
                // println("END")
            } catch (e: Throwable) {
                println(e)
                _stableContentState.value = StableContentState.Error
            }
        }
    }

    init {
        doStartInitialization()
    }
}