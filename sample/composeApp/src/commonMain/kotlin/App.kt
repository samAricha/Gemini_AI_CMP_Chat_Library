import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import org.jetbrains.compose.ui.tooling.preview.Preview
import presentation.chat_screens.ChatScreen


@Composable
@Preview
fun App() {
    MaterialTheme {
        ChatScreen()

    }
}