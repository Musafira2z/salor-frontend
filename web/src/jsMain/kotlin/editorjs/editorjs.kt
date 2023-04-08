package editorjs

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ETResponse(
    val time: Long,
    val version: String,
    val blocks: List<EtBlock>
)

@Serializable
sealed class EtBlock {
    abstract val id: String
}

@Serializable
@SerialName("paragraph")
class EtBlockParagraph(
    override val id: String,
    val data: EtParagraphData
) : EtBlock()

@Serializable
data class EtParagraphData(
    val text: String
)

@Serializable
@SerialName("header")
class EtBlockHeader(
    val data: EtHeaderData,
    override val id: String
) : EtBlock()

@Serializable
data class EtHeaderData(
    val text: String,
    val level: Int
)