package presentation.chat_screens

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import domain.model.ChatMessageModel
import domain.model.ChatStatusModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import presentation.component.CustomAppBar
import presentation.component.CustomBottomSearchBar
import presentation.component.CustomDialog
import presentation.component.CustomSnackBar
import presentation.component.MessageBubble
import presentation.component.MessageImagesStack
import presentation.extension.showSnackBar
//import presentation.extension.showSnackBar
import presentation.theme.LightGreen
import presentation.theme.LightRed

@Composable
fun ChatScreen(
    mainTitle: String = "Gemini AI Chat",
    subTitle: String = "by Teka",
    geminiKey: String = "",
    brandingImg: Painter? = null
    ) {
    val viewModel: ChatViewModel = ChatViewModel(geminiKey = geminiKey)
    val chatUiState = viewModel.uiState
    val focusManager = LocalFocusManager.current
    val coroutineScope = rememberCoroutineScope()
    val apiKeySnackBarHostState = remember { SnackbarHostState() }
    val errorSnackBarHostState = remember { SnackbarHostState() }
    val showDialog = remember { mutableStateOf(false) }


    Scaffold(
        topBar = {
            CustomAppBar(
                onActionClick = { showDialog.value = true },
                mainTitle = mainTitle,
                subTitle = subTitle,
                img = brandingImg
            )
        },
        bottomBar = {
            CustomBottomSearchBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
                    .padding(bottom = 30.dp, top = 5.dp),
                status = chatUiState.value.status,
                onSendClick = { text, images ->
                    coroutineScope.launch(Dispatchers.IO) {
                        viewModel.generateContent(text, images)
                    }
                },
            )
        },
        snackbarHost = {
            SnackbarHost(errorSnackBarHostState) { data ->
                CustomSnackBar(
                    data = data,
                    containerColor = LightRed
                )
            }
            SnackbarHost(apiKeySnackBarHostState) { data ->
                CustomSnackBar(
                    data = data,
                    containerColor = LightGreen
                )
            }
        },
        modifier = Modifier.pointerInput(Unit) {
            detectTapGestures(onTap = { focusManager.clearFocus() })
        },
    ) {
        ChatList(
            messages = chatUiState.value.messages
        )

        if (showDialog.value) {
            CustomDialog(
                value = chatUiState.value.apiKey,
                onVisibilityChanged = { showDialog.value = it },
                onSaveClicked = {
                    coroutineScope.launch {
                        viewModel.setApiKey(it)
                        apiKeySnackBarHostState.currentSnackbarData?.dismiss()
                        if(chatUiState.value.status is ChatStatusModel.Success){
                            apiKeySnackBarHostState.showSnackbar(
                                message = (chatUiState.value.status as ChatStatusModel.Success).data,
                                withDismissAction = true
                            )
                        }

                    }
                }
            )
        }

        errorSnackBarHostState.showSnackBar(coroutineScope, chatUiState.value.status)
    }
}


@Composable
fun ChatList(
    messages: List<ChatMessageModel>
) {
    val listState = rememberLazyListState()

    if (messages.isNotEmpty()) {
        LaunchedEffect(messages) {
            listState.animateScrollToItem(messages.lastIndex)
        }
    }
    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(messages.size) {
            val message = messages[it]
            if (message.images.isNotEmpty()) {
                MessageImagesStack(message = message)
                Spacer(modifier = Modifier.height(4.dp))
            }
            MessageBubble(message = message)
        }
    }
}