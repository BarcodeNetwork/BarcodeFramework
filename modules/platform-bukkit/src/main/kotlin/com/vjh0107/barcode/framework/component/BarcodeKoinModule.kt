package com.vjh0107.barcode.framework.component

import org.koin.core.module.Module

interface BarcodeKoinModule : IBarcodeComponent {
    /**
     * 코인 모듈 입니다. 자동으로 load, unload 됩니다.
     */
    val targetModule: KoinModule
}

/**
 * Koin 의 Module 어노테이션과 이름이 같아, 패키지 명으로 뜨는것을 방지해준다.
 * 또한, 필드에 코인 모듈 타입임을 꼭 명시해줘야한다.
 *
 * 관련 오류: https://github.com/InsertKoinIO/koin-annotations/issues/58
 */
typealias KoinModule = Module