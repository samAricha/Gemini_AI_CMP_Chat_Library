import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.teka.gemini_ai_cmp_chat_library.BuildKonfig
import presentation.chat_screens.ChatScreen


@Composable
@Preview
fun App() {
    MaterialTheme {
        ChatScreen(
            geminiKey = BuildKonfig.API_KEY,
            mainTitle = "OrganiksKMP",
            subTitle = "by aricha"
        )



    }
}

