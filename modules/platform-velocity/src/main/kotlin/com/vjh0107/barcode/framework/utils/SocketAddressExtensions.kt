package com.vjh0107.barcode.framework.utils

import java.net.InetSocketAddress
import java.net.SocketAddress

fun SocketAddress.toInetSocketAddress(): InetSocketAddress {
    return this.uncheckedNonnullCast()
}