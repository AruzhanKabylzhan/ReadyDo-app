package kz.rdd.core.utils.delegates

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class ChannelFlowWrapper<T>(private val scope: CoroutineScope) {

    private val _channel = Channel<T>(Channel.BUFFERED)

    val flow: Flow<T> = _channel.receiveAsFlow()

    fun setValue(value: T) {
        scope.launch {
            _channel.send(value)
        }
    }
}
