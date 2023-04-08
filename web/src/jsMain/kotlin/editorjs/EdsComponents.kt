package editorjs

import androidx.compose.runtime.Composable
import com.musafira2z.store.web.ui.utils.toClasses
import org.jetbrains.compose.web.dom.*


@Composable
fun EditorJs(
    blocks: List<EtBlock>
) {
    blocks.forEach {
        when (it) {
            is EtBlockHeader -> {
                ETHeader(it.data)
            }
            is EtBlockParagraph -> {
                ETParagraph(it.data)
            }
        }
    }
}

@Composable
fun ETParagraph(data: EtParagraphData) {
    P {
        Text(data.text)
    }
}

@Composable
fun ETHeader(data: EtHeaderData) {
    when (data.level) {
        1 -> H1(attrs = {
            toClasses("text-5xl font-extrabold dark:text-white")
        }) {
            Text(data.text)
        }
        2 -> H2(attrs = {
            toClasses("text-4xl font-bold dark:text-white")
        }) {
            Text(data.text)
        }
        3 -> H3(
            attrs = {
                toClasses("text-3xl font-bold dark:text-white")
            }
        ) {
            Text(data.text)
        }
        4 -> H4(
            attrs = {
                toClasses("text-2xl font-bold dark:text-white")
            }
        ) {
            Text(data.text)
        }
        5 -> H5(
            attrs = {
                toClasses("text-xl font-bold dark:text-white")
            }
        ) {
            Text(data.text)
        }
        6 -> H6(
            attrs = {
                toClasses("text-lg font-bold dark:text-white")
            }
        ) {
            Text(data.text)
        }
        else -> H6(
            attrs = {
                toClasses("text-lg font-bold dark:text-white")
            }
        ) {
            Text(data.text)
        }
    }
}