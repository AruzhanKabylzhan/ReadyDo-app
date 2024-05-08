package kz.rdd.chats.presentation.chat_ai

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.IconButton
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.parcelize.Parcelize
import kz.rdd.core.ui.DestinationController
import kz.rdd.core.ui.base.ComposeEffectHandler
import kz.rdd.core.ui.base.ComposeEventHandler
import kz.rdd.core.ui.base.Destination
import kz.rdd.core.ui.ext.rememberKeyboardOpenState
import kz.rdd.core.ui.ext.safeNavigationPadding
import kz.rdd.core.ui.theme.LocalAppTheme
import org.koin.androidx.compose.getViewModel

@Parcelize
class ChatDestination : Destination {
    @Composable
    override fun Content(controller: DestinationController) {
        ChatScreen()
    }
}

@Composable
fun ChatScreen() {
    val viewModel = getViewModel<ChatViewModel>()

    val isKeyboardOpen = rememberKeyboardOpenState()

    ComposeEffectHandler(effect = viewModel.effect)
    ComposeEventHandler(event = viewModel.event)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LocalAppTheme.colors.gray7.copy(alpha = 0.5f))
            .safeNavigationPadding()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(16.dp),
            reverseLayout = true
        ) {
            items(viewModel.messages.reversed()) { message ->
                if (message.isUser) {
                    MessageBubble(message.content, Alignment.End)
                } else {
                    MessageBubble(message.content, Alignment.Start)
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            var inputText by remember { mutableStateOf("") }
            TextField(
                value = inputText,
                onValueChange = { inputText = it },
                modifier = Modifier.weight(1f),
                label = { Text("Type a message") },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Send),
                keyboardActions = KeyboardActions(onSend = {
                    viewModel.sendMessage(inputText)
                    inputText = ""
                }),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = LocalAppTheme.colors.white,

                )
            )

            IconButton(
                onClick = {
                    viewModel.sendMessage(inputText)
                    inputText = ""
                },
                modifier = Modifier.padding(start = 8.dp),
            ) {
                Icon(Icons.Default.Send, contentDescription = "Send message", tint = LocalAppTheme.colors.main)
            }
        }

        if (isKeyboardOpen.value) {
            Spacer(modifier = Modifier.height(320.dp))
        }
    }
}

@Composable
fun MessageBubble(text: String, alignment: Alignment.Horizontal) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        tonalElevation = 4.dp,
        color = LocalAppTheme.colors.main10,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .wrapContentWidth(alignment)
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(16.dp),
            fontSize = 16.sp
        )
    }
}