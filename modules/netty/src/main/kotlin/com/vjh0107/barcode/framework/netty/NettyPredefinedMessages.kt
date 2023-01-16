package com.vjh0107.barcode.framework.netty

enum class NettyPredefinedMessages(val message: String) {
    ACTIVE("channel_active$$"),
    INACTIVE("channel_inactive$$"),
    SEPARATOR("$$")
}