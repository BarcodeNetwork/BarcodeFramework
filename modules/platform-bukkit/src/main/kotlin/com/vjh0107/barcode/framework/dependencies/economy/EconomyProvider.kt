package com.vjh0107.barcode.framework.dependencies.economy

import com.vjh0107.barcode.framework.AbstractBarcodePlugin
import com.vjh0107.barcode.framework.component.BarcodeComponent
import com.vjh0107.barcode.framework.component.BarcodeRegistrable
import com.vjh0107.barcode.framework.component.handler.impl.registrable.Registrar

@BarcodeComponent
class EconomyProvider(private val plugin: AbstractBarcodePlugin) : BarcodeRegistrable {
    override val id: String = "EconomyProvider"

    @Registrar(depend = "Vault")
    fun initialize() {
        plugin.logger.info("Vault 를 발견하였습니다.")
        initEconomy()
    }
}
