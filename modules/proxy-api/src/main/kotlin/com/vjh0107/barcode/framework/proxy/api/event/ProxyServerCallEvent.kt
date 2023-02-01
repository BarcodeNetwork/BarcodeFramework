package com.vjh0107.barcode.framework.proxy.api.event

abstract class ProxyServerCallEvent : ProxyEvent {
    abstract val server: String
}