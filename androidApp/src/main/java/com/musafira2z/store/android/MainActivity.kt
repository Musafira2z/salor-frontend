package com.musafira2z.store.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.musafira2z.store.android.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                AppScreen()
            }
        }
    }
}

@Composable
fun AppScreen() {
    Surface {
        Text(text = "Hello Android")
    }
}

@Preview
@Composable
fun DefaultPreview() {
    AppTheme {
        AppScreen()
    }
}
