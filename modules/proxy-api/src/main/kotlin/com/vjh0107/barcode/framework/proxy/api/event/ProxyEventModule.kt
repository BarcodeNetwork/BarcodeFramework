package com.vjh0107.barcode.framework.proxy.api.event

import com.vjh0107.barcode.framework.component.BarcodeComponent
import com.vjh0107.barcode.framework.component.BarcodeKoinModule
import com.vjh0107.barcode.framework.component.KoinModule
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.ksp.generated.module

@BarcodeComponent
@Module
@ComponentScan
class ProxyEventModule : BarcodeKoinModule {
    override val targetModule: KoinModule
        get() = module
}