package com.vjh0107.barcode.framework

import com.vjh0107.barcode.framework.component.handler.BukkitComponentHandlerModule
import com.vjh0107.barcode.framework.database.BukkitDatabaseModule
import com.vjh0107.barcode.framework.koin.KoinContextualApplication
import com.vjh0107.barcode.framework.koin.initKoin
import com.vjh0107.barcode.framework.nms.NMSModule
import org.koin.ksp.generated.*

class BarcodeFrameworkPlugin : AbstractBarcodePlugin(), KoinContextualApplication {
    /**
     * 플러그인이 enable 되기 전과 외부 프로젝트 모듈의 Koin 모듈입니다.
     */
    override fun getModules() = listOf(
        NMSModule().module,
        BukkitComponentHandlerModule().module,
        BukkitDatabaseModule().module,
    )

    /**
     * 플러그인이 enable 되어 component 들이 초기화 되기 전에 Koin 을 실행합니다.
     */
    override fun onPreLoad() { initKoin() }

    override fun onPreEnable() {
        saveDefaultConfig()
    }
}