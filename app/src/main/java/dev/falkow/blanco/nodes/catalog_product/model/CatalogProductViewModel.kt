package dev.falkow.blanco.nodes.catalog_product.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.falkow.blanco.nodes.catalog_product.display.navigation.CatalogProductArgs
import dev.falkow.blanco.nodes.catalog_product.model.states.CatalogProductState
import dev.falkow.blanco.nodes.catalog_product.options.GetStableContentOption
import dev.falkow.blanco.nodes.catalog_product.options.ObserveGalleryButtonsDisplayStateOption
import dev.falkow.blanco.nodes.catalog_product.options.SetGalleryButtonsDisplayStateOption
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class CatalogProductViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getStableContentOption: GetStableContentOption,
    observeGalleryButtonsDisplayStateOption: ObserveGalleryButtonsDisplayStateOption,
    private val setGalleryButtonsDisplayStateOption: SetGalleryButtonsDisplayStateOption,
) : ViewModel() {

    /** Arguments From Navigation. */

    private val catalogProductArgs = CatalogProductArgs(savedStateHandle)
    var catalogNavId = catalogProductArgs.catalogNavId
        private set
    var catalogProductNavId = catalogProductArgs.catalogProductNavId
        private set


    /** CatalogProduct State. */

    var catalogProductState: CatalogProductState by mutableStateOf(CatalogProductState.Loading)
        private set

    private fun getCatalogProductContent() {
        viewModelScope.launch {
            catalogProductState = CatalogProductState.Loading
            catalogProductState =
                try {
                    CatalogProductState.Success(
                        stableContent = getStableContentOption(
                            catalogNavId = catalogNavId,
                            catalogProductNavId = catalogProductNavId,
                        )
                    )
                } catch (cancellationException: CancellationException) {
                    throw cancellationException
                } catch (e: Exception) {
                    CatalogProductState.Error
                }
        }
    }

    init {
        getCatalogProductContent()
    }


    /** Preferred Is CatalogProduct Gallery Buttons Display Enable. */

    val preferredIsGalleryButtonsDisplayEnable: StateFlow<Boolean> =
        observeGalleryButtonsDisplayStateOption.value
            .map { it ?: true }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = true
            )

    fun setPreferredIsGalleryButtonsDisplayEnable(
        isGalleryButtonsDisplayEnable: Boolean
    ) {
        viewModelScope.launch {
            setGalleryButtonsDisplayStateOption(
                isGalleryButtonsDisplayEnable
            )
        }
    }
}