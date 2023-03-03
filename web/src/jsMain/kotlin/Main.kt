import com.musafira2z.store.web.ui.app.AppScreen
import org.jetbrains.compose.web.renderComposable

fun main() {
    renderComposable(rootElementId = "root") {
        AppScreen()
    }
}

