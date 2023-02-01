package com.vjh0107.barcode.framework.netty

import com.vjh0107.barcode.framework.utils.uncheckedNonnullCast
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter
import io.netty.channel.ChannelPipeline
import java.nio.charset.Charset

/**
 * kotlin dsl 스러운 방법으로 adapter 를 추가합니다.
 */
class ChannelPipelineDelegate(val pipeline: ChannelPipeline) : ChannelPipeline by pipeline {
    fun addChannelReadHandler(handler: (context: ChannelHandlerContext, message: String) -> Unit) {
        addLast(object : ChannelInboundHandlerAdapter() {
            override fun channelRead(context: ChannelHandlerContext, message: Any) {
                handler(context, message.uncheckedNonnullCast<ByteBuf>().toString(Charset.defaultCharset()))
            }
        })
    }

    fun addChannelRegisteredHandler(handler: (context: ChannelHandlerContext) -> Unit) {
        addLast(object : ChannelInboundHandlerAdapter() {
            override fun channelRegistered(ctx: ChannelHandlerContext) {
                handler(ctx)
            }
        })
    }

    fun addChannelUnregisteredHandler(handler: (context: ChannelHandlerContext) -> Unit) {
        addLast(object : ChannelInboundHandlerAdapter() {
            override fun channelUnregistered(ctx: ChannelHandlerContext) {
                handler(ctx)
            }
        })
    }

    fun addChannelActiveHandler(handler: (context: ChannelHandlerContext) -> Unit) {
        addLast(object : ChannelInboundHandlerAdapter() {
            override fun channelActive(ctx: ChannelHandlerContext) {
                handler(ctx)
            }
        })
    }

    fun addChannelInactiveHandler(handler: (context: ChannelHandlerContext) -> Unit) {
        addLast(object : ChannelInboundHandlerAdapter() {
            override fun channelInactive(ctx: ChannelHandlerContext) {
                handler(ctx)
            }
        })
    }

    fun addExceptionCaughtHandler(handler: (context: ChannelHandlerContext, cause: Throwable) -> Unit) {
        addLast(object : ChannelInboundHandlerAdapter() {
            @Deprecated("Deprecated in Java", ReplaceWith("handler(ctx, cause)"))
            override fun exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable) {
                handler(ctx, cause)
            }
        })
    }
}