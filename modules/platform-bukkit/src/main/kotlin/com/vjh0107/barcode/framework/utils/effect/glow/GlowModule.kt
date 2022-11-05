package com.vjh0107.barcode.framework.utils.effect.glow

import com.vjh0107.barcode.framework.component.BarcodeComponent
import com.vjh0107.barcode.framework.component.BarcodeKoinModule
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.ksp.generated.module

@ComponentScan
@Module
class GlowModule {
    val mod: org.koin.core.module.Module = module // okay
}