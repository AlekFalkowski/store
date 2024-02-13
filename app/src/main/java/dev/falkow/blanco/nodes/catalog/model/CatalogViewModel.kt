package dev.falkow.blanco.nodes.catalog.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.falkow.blanco.nodes.catalog.model.states.CardListDisplayFormState
import dev.falkow.blanco.nodes.catalog.model.states.DynamicContentState
import dev.falkow.blanco.nodes.catalog.model.states.StableContentState
import dev.falkow.blanco.nodes.catalog.options.ObserveCatalogCardListDisplayFormOption
import dev.falkow.blanco.nodes.catalog.options.SetCatalogCardListDisplayFormOption
import dev.falkow.blanco.nodes.catalog.types.CardListDisplayForms
import dev.falkow.blanco.nodes.catalog.options.GetDynamicContentOption
import dev.falkow.blanco.nodes.catalog.options.GetStableContentOption
import dev.falkow.blanco.nodes.catalog.types.FieldSet
import dev.falkow.blanco.nodes.catalog.options.ObserveQueryStringOption
import dev.falkow.blanco.nodes.catalog.options.SetQueryStringOption
import dev.falkow.blanco.shared.config.Features
import dev.falkow.blanco.shared.config.PROJECT_URI_BASE
import dev.falkow.blanco.shared.di.IoDispatcher
import dev.falkow.blanco.shared.options.IActionState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class CatalogViewModel @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    savedStateHandle: SavedStateHandle,
    private val getDynamicContentOption: GetDynamicContentOption,
    observeQueryStringOption: ObserveQueryStringOption,
    private val setQueryStringOption: SetQueryStringOption,
    private val getStableContentOption: GetStableContentOption,
    observeCatalogCardListDisplayFormOption: ObserveCatalogCardListDisplayFormOption,
    private val setCatalogCardListDisplayFormOption: SetCatalogCardListDisplayFormOption,
) : ViewModel() {

    /** Arguments From Navigation. */

    private val catalogNavId: String = checkNotNull(savedStateHandle["catalogNavId"])


    /** Query String - Query Map. */

    // Начальные условия:
    // Состояние полей фильтра храниться в БД в виде JSON или QueryString.
    // При переходе на данную страницу по внешней ссылке состояние полей фильтра прередаётся
    //      внутри QueryString этой ссылки.
    // Android Приложение знают свою схему, домен и версию API.
    // Этот класс знает свой шаблон сущности и номер сущности.
    // Этот класс собирает URI исходя из начальных условий
    // val mainUri = "$PROJECT_BASE_URL/${Features.CATALOG}/$catalogNavId/$queryString"
    // private val staticContentUri = "$PROJECT_BASE_URL/$PROJECT_VERSION/static/${Features.CATALOG}/$catalogNavId/$queryString"
    // private val dynamicContentUri = "$PROJECT_BASE_URL/$PROJECT_VERSION/dynamic/${Features.CATALOG}/$catalogNavId/$queryString"

    val uriPrefix = "$PROJECT_URI_BASE${Features.CATALOG}/$catalogNavId/"
    val queryStringState: StateFlow<String> =
        observeQueryStringOption(catalogNavId)
            .map {
                it ?: ""
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = ""
            )

    private fun convertQueryStringToQueryMap(queryString: String): Map<String, Set<String>> {
        val queryMap: MutableMap<String, MutableSet<String>> = mutableMapOf()
        queryString.trim().split("&").forEach {
            val keyAndValue: MutableList<String> = it.trim().split("=").toMutableList()
            if (keyAndValue.size > 1) {
                keyAndValue[0] = keyAndValue[0].replace("%5B", "[").replace("%5D", "]")
                if (queryMap[keyAndValue[0]]?.add(keyAndValue[1]) == null) {
                    queryMap[keyAndValue[0]] = mutableSetOf(keyAndValue[1])
                }
            }
        }
        return queryMap
    }

    private fun convertQueryMapToQueryString(): String {
        val queryList = mutableListOf<String>()
        textFieldsStates.forEach {
            if (it.value.value != "") queryList.add(it.key + "=" + it.value.value)
        }
        singleChoiceFieldsStates.forEach {
            if (it.value.value != "") queryList.add(it.key + "=" + it.value.value)
        }
        multiChoiceFieldsStates.forEach {
            it.value.value.forEach { value ->
                if (value != "") queryList.add(it.key + "=" + value)
            }
        }
        return queryList.joinToString("&")
    }

    private suspend fun setQueryString(queryString: String) {
        setQueryStringOption(catalogNavId, queryString)
    }


    /** Stable Content. */

    private val _stableContentState = MutableStateFlow<StableContentState?>(null)
    val stableContentState: StateFlow<StableContentState?> = _stableContentState.asStateFlow()


    /** Fields States. */

    // @OptIn(ExperimentalFoundationApi::class)
    // val textFieldsStates = mutableMapOf<String, TextFieldState>()
    val textFieldsStates = mutableMapOf<String, MutableState<String>>()
    val singleChoiceFieldsStates = mutableMapOf<String, MutableState<String>>()
    val multiChoiceFieldsStates = mutableMapOf<String, MutableState<Set<String>>>()

    // @OptIn(ExperimentalFoundationApi::class)
    private fun initFieldsStates(filterConfig: List<FieldSet>, queryString: String) {
        val queryMap = convertQueryStringToQueryMap(queryString)
        filterConfig.map { fieldSet ->
            fieldSet.fieldList.map { field ->
                when (field.type) {
                    "singleChoice" -> {
                        singleChoiceFieldsStates[field.name] =
                            mutableStateOf(queryMap[field.name]?.first() ?: "")
                    }

                    "multiChoice" -> {
                        multiChoiceFieldsStates[field.name] = mutableStateOf(
                            field.options?.map { option ->
                                option.value
                            }?.filter { optionValue ->
                                queryMap[field.name]?.contains(optionValue) ?: false
                            }?.toSet() ?: setOf()
                        )
                    }

                    // text
                    // range
                    else -> {
                        textFieldsStates[field.name] =
                                // TextFieldState(initialText = queryMap[field.name]?.get(0) ?: "")
                            mutableStateOf(queryMap[field.name]?.first() ?: "")
                        field.endName?.let {
                            textFieldsStates[field.endName] =
                                    // TextFieldState(initialText = queryMap[field.endName]?.get(0) ?: "")
                                mutableStateOf(queryMap[field.endName]?.first() ?: "")
                        }
                    }
                }
            }
        }
    }

    fun resetFieldStates() {
        textFieldsStates.forEach {
            it.value.value = ""
        }
        singleChoiceFieldsStates.forEach {
            it.value.value = ""
        }
        multiChoiceFieldsStates.forEach {
            it.value.value = setOf()
        }
    }


    /** Dynamic Content. */

    private val _dynamicContentState = MutableStateFlow<DynamicContentState?>(null)
    val dynamicContentState: StateFlow<DynamicContentState?> = _dynamicContentState.asStateFlow()

    fun getDynamicContent(): Unit {
        if (_dynamicContentState.value is DynamicContentState.Loading) return
        _dynamicContentState.value = DynamicContentState.Loading
        viewModelScope.launch(ioDispatcher) {
            try {
                val queryString = convertQueryMapToQueryString()
                setQueryString(queryString)
                _dynamicContentState.value = DynamicContentState.Success(
                    success = getDynamicContentOption(catalogNavId, queryString)
                )
            } catch (e: Throwable) {
                _dynamicContentState.value = DynamicContentState.Error
            }
        }
    }


    /** Catalog Card List Display Form. */

    private val _setPreferredCatalogCardListDisplayFormIActionState =
        MutableStateFlow<IActionState?>(null)

    fun setCardListDisplayForm(catalogCardListDisplayForm: CardListDisplayForms): Unit {
        if (_setPreferredCatalogCardListDisplayFormIActionState.value is IActionState.InProgress) return
        _setPreferredCatalogCardListDisplayFormIActionState.value = IActionState.InProgress()
        viewModelScope.launch(ioDispatcher) {
            try {
                // delay(1_000)
                // throw Exception("CRASH SIMULATION")
                setCatalogCardListDisplayFormOption(
                    catalogCardListDisplayForm
                )
                _setPreferredCatalogCardListDisplayFormIActionState.value = IActionState.Success()
            } catch (e: Throwable) {
                _setPreferredCatalogCardListDisplayFormIActionState.value = IActionState.Error()
            }
        }
    }

    private fun resetSetPreferredCatalogCardListDisplayFormActionError(): Unit {
        _setPreferredCatalogCardListDisplayFormIActionState.value = null
    }

    val cardListDisplayFormState: StateFlow<CardListDisplayFormState> =
        combine(
            observeCatalogCardListDisplayFormOption.value,
            _setPreferredCatalogCardListDisplayFormIActionState
        ) { form, actionState ->
            when (actionState) {
                is IActionState.Success, null -> {
                    CardListDisplayFormState.Calm(
                        form = form ?: CardListDisplayForms.entries.first(),
                    )
                }

                is IActionState.InProgress -> {
                    CardListDisplayFormState.TryingSetNewValue(
                        form = form ?: CardListDisplayForms.entries.first(),
                    )
                }

                is IActionState.Error -> {
                    CardListDisplayFormState.ErrorSettingNewValue(
                        form = form ?: CardListDisplayForms.entries.first(),
                        errorMessage = "Error",
                        resetError = ::resetSetPreferredCatalogCardListDisplayFormActionError
                    )
                }
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = CardListDisplayFormState.Calm(
                form = CardListDisplayForms.entries.first(),
            )
        )


    /** Initialization Block. */

    fun doStartInitialization(): Unit {
        if (_stableContentState.value is StableContentState.Loading) return
        _stableContentState.value = StableContentState.Loading
        viewModelScope.launch(ioDispatcher) {
            try {
                // println("START")
                // delay(1_000)
                // throw Exception("CRASH SIMULATION")
                val stableContent = getStableContentOption(catalogNavId, queryStringState.value)
                initFieldsStates(
                    filterConfig = stableContent.filterConfig,
                    queryString = queryStringState.value,
                )
                getDynamicContent()
                _stableContentState.value = StableContentState.Success(
                    success = stableContent
                )
                // println("END")
            } catch (e: Throwable) {
                _stableContentState.value = StableContentState.Error
            }
        }
    }

    init {
        doStartInitialization()
    }
}