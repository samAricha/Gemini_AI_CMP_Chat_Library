package presentation.chat_screens

import domain.model.ChatMessageModel
import domain.model.ChatStatusModel
import org.teka.gemini_ai_cmp_chat_library.BuildKonfig


data class ChatUiState(
    val messages: List<ChatMessageModel> = emptyList(),
    val status: ChatStatusModel = ChatStatusModel.Idle,
    val apiKey: String = BuildKonfig.API_KEY
)