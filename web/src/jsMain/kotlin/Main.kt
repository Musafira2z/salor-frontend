import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.renderComposable


@JsModule("./input.css")
external val cssFile: dynamic

fun main() {
    renderComposable(rootElementId = "root") {
        Div({ classes("container", "mx-auto") }) {
            H1({ classes("text-3xl", "font-bold", "underline") }) {
                Text("Hello World")
            }
        }
    }
}

@Composable
fun Home() {

}