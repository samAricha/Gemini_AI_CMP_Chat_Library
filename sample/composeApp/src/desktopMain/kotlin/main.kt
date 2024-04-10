import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "Gemini_AI_CMP_Chat_Library") {
        App()
    }
}