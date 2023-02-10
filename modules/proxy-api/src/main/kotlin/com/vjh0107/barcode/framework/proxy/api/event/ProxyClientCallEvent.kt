package com.vjh0107.barcode.framework.proxy.api.event

interface ProxyClientCallEvent : ProxyEvent {
    val serverPort: Int
}