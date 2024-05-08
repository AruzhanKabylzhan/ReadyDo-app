package kz.rdd.core.ui.ext

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kz.rdd.core.ui.DestinationController
import kz.rdd.core.ui.base.AcceptResult

suspend fun <T> DestinationController.returnToPrevDestination(
    key: String? = null,
    value: T?,
    additionalKey: String? = null,
) {
    returnToPrevDestination(
        AcceptResult.Data(
            key = key,
            value = value,
            additionalKey = additionalKey,
        )
    )
}

suspend fun DestinationController.returnToPrevDestination(
    data: AcceptResult.Data,
) {
    val entries = run {
        navController.backstack.entries +
                sheetNavController.backstack.entries +
                dialogNavController.backstack.entries
    }

    val previousDestination = entries.getOrNull(entries.lastIndex - 1)?.destination

    (previousDestination as? AcceptResult)?.setResult(data)
}

fun <T> DestinationController.returnToPrevDestination(
    key: String? = null,
    value: T?,
    additionalKey: String? = null,
    scope: CoroutineScope,
) {
    scope.launch {
        returnToPrevDestination(key = key, value = value, additionalKey = additionalKey)
    }
}

fun DestinationController.returnToPrevDestination(
    data: AcceptResult.Data,
    scope: CoroutineScope,
) {
    scope.launch {
        returnToPrevDestination(data)
    }
}
