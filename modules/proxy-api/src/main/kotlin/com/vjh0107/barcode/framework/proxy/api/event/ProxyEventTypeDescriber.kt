package com.vjh0107.barcode.framework.proxy.api.event

interface ProxyEventTypeDescriber {
    val eventName: String
}

inline fun <reified T : ProxyEventTypeDescriber>ProxyEventTypeDescriber.sameAs(): Boolean {
    return this.eventName == proxyEventTypeDescriber.eventName
}