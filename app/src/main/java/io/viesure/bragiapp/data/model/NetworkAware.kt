package io.viesure.bragiapp.data.model

interface INetworkAware {

    fun subscribeToNetworkEvents()
    fun unSubscribeFromNetworkEvents()
}