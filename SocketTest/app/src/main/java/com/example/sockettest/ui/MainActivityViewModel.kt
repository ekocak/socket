package com.example.sockettest.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sockettest.domain.chat.ClearHistoryUseCase
import com.example.sockettest.domain.chat.ConnectUseCase
import com.example.sockettest.domain.chat.DisconnectUseCase
import com.example.sockettest.domain.chat.GetMessagesUseCase
import com.example.sockettest.domain.chat.SendMessageUseCase
import com.example.sockettest.domain.model.SocketState
import com.example.sockettest.domain.chat.GetLastMessagesUseCase
import com.example.sockettest.domain.chat.InsertMessageUseCase
import com.example.sockettest.extensions.checkResult
import com.example.sockettest.extensions.fromJson
import com.example.sockettest.extensions.mapContent
import com.example.sockettest.model.response.ChatResponse
import com.example.sockettest.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val sendMessageUseCase: SendMessageUseCase,
    private val getMessagesUseCase: GetMessagesUseCase,
    private val connectUseCase: ConnectUseCase,
    private val insertMessageUseCase: InsertMessageUseCase,
    private val disconnectUseCase: DisconnectUseCase,
    private val getLastMessages: GetLastMessagesUseCase,
    private val clearHistoryUseCase: ClearHistoryUseCase
) : BaseViewModel() {
    private var actions: List<ChatResponse?>? = null
    private val _lastMessages = MutableLiveData<List<ChatResponse>>(emptyList())
    val lastMessages : LiveData<List<ChatResponse>> = _lastMessages


    init {
        parseResponse()
        connect()
        receive()
        loadLastMessagesIfContains()
    }

    private fun loadLastMessagesIfContains() {
        viewModelLaunch {
            val result = getLastMessages.invoke(Unit)
            checkResult(result) {
                if (it.isNullOrEmpty().not()){
                    println("deneme : "+it)
                    _lastMessages.value = it?.mapContent()
                }else {
                    send("step_1")
                }

            }

        }
    }

    private fun connect() {
        viewModelLaunch {
            println("socket connect called")
            connectUseCase.invoke(Unit)
        }
    }

    private fun send(message: String) {
        viewModelLaunch {
            delay(1000)
            println("gönderildi")
            sendMessageUseCase.invoke(message)
        }
    }

    private fun receive() {
        println("receive çağırldı")
        viewModelLaunch {
            getMessagesUseCase.invoke().onStart {
                // loading
            }.onEach { event ->
                println("geldi"+event)
                when (event) {
                    is SocketState.Connected -> {
                        println("connected")
                    }

                    is SocketState.Disconnected -> {
                        println("disconnected")
                    }

                    is SocketState.Failed -> {
                        println("failure")
                    }

                    is SocketState.MessageReceived -> {
                        if (event.message == "end_conversation") {
                            closeChat()
                            return@onEach
                        }
                        val action = actions?.firstOrNull{ it?.step == event.message}
                        action?.let {
                            val newList = _lastMessages.value?.toMutableList().also { it?.add(action) }
                            insertMessageUseCase.invoke(it)
                            _lastMessages.value = newList?.toList()
                        }
                    }

                    else -> {

                    }
                }
            }.collect()
        }
    }

    private fun parseResponse() {
        actions = MockJson.chatMock.fromJson<List<ChatResponse>>().mapContent()
    }

    fun onActionButtonClicked(action: String) {
        send(action)
    }
    suspend fun closeChat() {
        clearHistoryUseCase.invoke(Unit)
        disconnectUseCase.invoke(Unit)
        _lastMessages.value = emptyList()
    }
}
