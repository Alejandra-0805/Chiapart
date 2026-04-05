package com.alejandra.chiapart

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.alejandra.chiapart.core.navigation.FeatureNavGraph
import com.alejandra.chiapart.core.navigation.Login
import com.alejandra.chiapart.core.navigation.NavigationWrapper
import com.alejandra.chiapart.ui.theme.ChiapartTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navGraphs: Set<@JvmSuppressWildcards FeatureNavGraph>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChiapartTheme {
                NavigationWrapper(
                    navGraphs = navGraphs,
                    startDestination = Login
                )
            }
        }
    }
}