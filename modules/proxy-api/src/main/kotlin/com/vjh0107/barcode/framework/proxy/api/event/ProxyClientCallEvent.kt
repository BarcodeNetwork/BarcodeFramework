package com.vjh0107.barcode.framework.proxy.api.event

abstract class ProxyClientCallEvent : ProxyEvent {
    abstract val serverPort: Int
}