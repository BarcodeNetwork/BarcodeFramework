package com.vjh0107.barcode.framework.dependencies.economy

import net.milkbowl.vault.economy.Economy
import net.milkbowl.vault.economy.EconomyResponse
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer

internal fun initEconomy() {
    ECONOMY = Bukkit.getServer().servicesManager.getRegistration(Economy::class.java)?.provider
        ?: throw NullPointerException("Vault 를 가져올 수 없습니다.")
}

lateinit var ECONOMY: Economy

val OfflinePlayer.money
    get() = ECONOMY.getBalance(this)

fun OfflinePlayer.giveMoney(value: Double): EconomyResponse {
    return ECONOMY.depositPlayer(this, value)
}

fun OfflinePlayer.takeMoney(value: Double): EconomyResponse {
    return ECONOMY.withdrawPlayer(this, value)
}