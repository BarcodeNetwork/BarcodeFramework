package com.vjh0107.barcode.framework.dependencies.placeholderapi.extensions

import me.clip.placeholderapi.PlaceholderAPI
import org.bukkit.entity.Player

internal var isPlaceholderAPIEnabled = false

fun String.parseWithPAPI(player: Player): String {
    if (!isPlaceholderAPIEnabled) {
        throw NullPointerException("PlaceholderAPI 를 찾을 수 없습니다.")
    }
    return PlaceholderAPI.setPlaceholders(player, this)
}
