package dev.falkow.blanco.layout.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.falkow.blanco.layout.options.GetNavDrawerContentOption
import dev.falkow.blanco.shared.types.NavDrawerGroup
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppLayoutViewModel @Inject constructor(
    private val getNavDrawerContentOption: GetNavDrawerContentOption,
) : ViewModel() {

    /** App Nav Drawer Content. */

    val navDrawerContentIsLoading: StateFlow<Boolean> =
        getNavDrawerContentOption.isBusy

    val navDrawerContentResult: StateFlow<Result<List<NavDrawerGroup>>?> =
        getNavDrawerContentOption.result

    fun updateNavDrawerContent(): Unit {
        viewModelScope.launch { getNavDrawerContentOption.invoke() }
    }

    init {
        updateNavDrawerContent()
    }
}