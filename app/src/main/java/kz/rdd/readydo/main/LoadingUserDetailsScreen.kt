package kz.rdd.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kz.rdd.core.ui.DestinationController
import kz.rdd.core.ui.LocalDestinationController
import kz.rdd.core.ui.widgets.LoadingProgress

@Composable
fun LoadingUserDetailsScreen(
    destinationController: DestinationController = LocalDestinationController,
) {
    val isFetched = true
    LaunchedEffect(isFetched) {
        if (isFetched) {
            destinationController.navigateToMain(replace = true)
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        LoadingProgress(
            modifier = Modifier.align(Alignment.Center),
            isVisible = !isFetched,
        )
    }
}
