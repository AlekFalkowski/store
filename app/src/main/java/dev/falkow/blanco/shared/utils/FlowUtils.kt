package dev.falkow.blanco.shared.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn

fun <T, K> StateFlow<T>.mapState(
    scope: CoroutineScope,
    transform: (data: T) -> K
): StateFlow<K> {
    return mapLatest {
        transform(it)
    }
        .stateIn(scope, SharingStarted.Eagerly, transform(value))
}

fun <T, K> StateFlow<T>.mapState(
    scope: CoroutineScope,
    initialValue: K,
    transform: suspend (data: T) -> K
): StateFlow<K> {
    return mapLatest {
        transform(it)
    }
        .stateIn(scope, SharingStarted.Eagerly, initialValue)
}

// Example
// val title = movie.mapState(viewModelScope) {
//     it.title
// }

// Важно отметить, что мы используем SharingStarted.Eagerly здесь,
// а не SharingStarted.WhileSubscribed().
// Это связано с тем, что мы хотим, чтобы этот StateFlow отображал каждое значение,
// исходящее из источника, независимо от того, есть ли у нашего целевого StateFlow подписчик.