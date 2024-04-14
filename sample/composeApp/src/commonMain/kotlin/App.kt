import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import gemini_ai_cmp_chat_library.sample.composeapp.generated.resources.Res
import gemini_ai_cmp_chat_library.sample.composeapp.generated.resources.organiks_egg
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.teka.gemini_ai_cmp_chat_library.BuildKonfig
import presentation.chat_screens.ChatScreen


@OptIn(ExperimentalResourceApi::class)
@Composable
@Preview
fun App() {
    MaterialTheme {

        ChatScreen(
            geminiKey = BuildKonfig.API_KEY,
            mainTitle = "OrganiksKMP",
            subTitle = "by aricha",
            brandingImg = painterResource(Res.drawable.organiks_egg)
        )



    }
}

