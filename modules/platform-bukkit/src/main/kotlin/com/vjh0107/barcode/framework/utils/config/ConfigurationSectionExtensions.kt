package com.vjh0107.barcode.framework.utils.config

import org.bukkit.configuration.ConfigurationSection

fun ConfigurationSection.getStringNotNull(key: String, default: String): String {
    return this.getString(key, default) ?: throw IllegalStateException("null 이 아니어야 하는 값이 null 입니다.")
}