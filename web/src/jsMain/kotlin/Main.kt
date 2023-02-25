import androidx.compose.runtime.*
import dev.petuska.kmdc.checkbox.MDCCheckbox
import dev.petuska.kmdc.form.field.MDCFormField
import org.jetbrains.compose.web.renderComposable

fun main() {
    renderComposable(rootElementId = "root") {
        Showcase()
    }
}

@Composable
fun Showcase() {
    var checked by remember { mutableStateOf(false) } // Declaring controlled state

    MDCFormField { // Using implicit `content` argument to wrap MDCCheckbox inside MDCFormField UI as recommended by the MDC docs
        MDCCheckbox(
            checked = checked,
            label = "Yes/No",
            attrs = { // Overriding underlying HTMLInput element attributes
                onInput { checked = !checked }
            }
        ) // MDCCheckbox doesn't allow for additional inner content
    }
}

