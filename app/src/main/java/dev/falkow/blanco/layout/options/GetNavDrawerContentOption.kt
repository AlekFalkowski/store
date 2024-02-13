package dev.falkow.blanco.layout.options

import dev.falkow.blanco.layout.resources.INavDrawerContentData
import dev.falkow.blanco.shared.types.NavDrawerGroup
import dev.falkow.blanco.shared.types.NavDrawerLink
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class GetNavDrawerContentOption @Inject constructor(
    private val resource: INavDrawerContentData
) {

    private val _isBusy = MutableStateFlow<Boolean>(false)
    val isBusy: StateFlow<Boolean> = _isBusy.asStateFlow()

    private val _result = MutableStateFlow<Result<List<NavDrawerGroup>>?>(null)
    val result: StateFlow<Result<List<NavDrawerGroup>>?> = _result.asStateFlow()

    fun invoke(): Unit {

        if (isBusy.value == true) {
            return
        }

        _isBusy.value = true
        try {
            _result.value = Result.success(
                value = resource.data.map {
                    NavDrawerGroup(
                        title = it.title,
                        linkList = it.linkList.map {
                            NavDrawerLink(
                                navDestination = it.navDestination,
                                iconSrc = it.iconSrc,
                                title = it.title,
                            )
                        }
                    )
                }
            )
        } catch (e: Exception) {
            _result.value = Result.failure(exception = Throwable(message = "Data not available"))
        } finally {
            _isBusy.value = false
        }
    }
}