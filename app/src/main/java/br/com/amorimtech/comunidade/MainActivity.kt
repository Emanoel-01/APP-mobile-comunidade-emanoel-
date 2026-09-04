package br.com.amorimtech.comunidade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import br.com.amorimtech.comunidade.ui.screens.MainScreen
import br.com.amorimtech.comunidade.ui.theme.ComunidadeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComunidadeTheme {
                MainScreen()
            }
        }
    }
}
