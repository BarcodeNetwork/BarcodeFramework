package com.vjh0107.barcode.framework.proxy.api.event

import kotlin.reflect.KClass

interface ProxyEventTypeDescriber {
    fun getEventName(): String {
        return EventNameGetter.getEventName(this::class)
    }

    companion object EventNameGetter {
        fun <T : ProxyEventTypeDescriber> getEventName(eventClass: KClass<T>): String {
            return eventClass.qualifiedName ?: throw NullPointerException("ProxyEventTypeDescriber 는 익명 클래스 일 수 없습니다.")
        }
    }
}

inline fun <reified T : ProxyEventTypeDescriber>ProxyEventTypeDescriber.sameAs(): Boolean {
    return this.getEventName() == ProxyEventTypeDescriber.getEventName(T::class)
}