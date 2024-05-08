package kz.rdd

import android.animation.Animator
import android.animation.Animator.AnimatorListener
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.animation.DecelerateInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.LaunchedEffect
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.splashscreen.SplashScreenViewProvider
import kz.rdd.core.ui.theme.AppTheme
import kz.rdd.core.utils.ext.checkIsNeedLocalizing
import kz.rdd.core.utils.ext.getLocale
import kz.rdd.core.utils.ext.setLocale
import kz.rdd.main.start.GlobalViewModel
import kz.rdd.main.start.StartNavigationScreen
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val viewModel by viewModel<GlobalViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT),
        )
        super.onCreate(savedInstanceState)

        setContent {
            val locale = getLocale()
            LaunchedEffect(locale) {
                viewModel.setLocaleChanged(locale)
            }
            AppTheme(
                currentLocale = locale
            ) {
                StartNavigationScreen(viewModel)
            }
        }
    }


    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
    }
}
