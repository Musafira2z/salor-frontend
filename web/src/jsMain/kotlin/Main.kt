import com.musafira2z.store.di.appModule
import com.musafira2z.store.web.ui.app.AppScreen
import com.musafira2z.store.web.ui.di.webModule
import org.jetbrains.compose.web.renderComposable
import org.koin.core.context.startKoin

fun main() {
    startKoin {
        modules(webModule + appModule)
    }
    renderComposable(rootElementId = "root") {
        AppScreen()
    }
}

