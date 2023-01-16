package com.vjh0107.barcode.framework.netty

import com.vjh0107.barcode.framework.component.BarcodeComponent
import com.vjh0107.barcode.framework.component.BarcodeKoinModule
import com.vjh0107.barcode.framework.component.KoinModule
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.ksp.generated.module

@ComponentScan
@Module
@BarcodeComponent
class NettyModule : BarcodeKoinModule {
    override val targetModule: KoinModule
        get() = module
}