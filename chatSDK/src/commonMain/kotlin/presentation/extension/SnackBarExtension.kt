package presentation.extension

import androidx.compose.material3.SnackbarHostState
import domain.model.ChatStatusModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun SnackbarHostState.showSnackBar(scope: CoroutineScope, status: ChatStatusModel) {
    if (status is ChatStatusModel.Error) {
        scope.launch {
            showSnackbar(
                message = status.message,
                withDismissAction = true
            )
        }
    } else {
        this.currentSnackbarData?.dismiss()
    }
}