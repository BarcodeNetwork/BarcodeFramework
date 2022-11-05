package com.vjh0107.barcode.framework.component

/**
 * IBarcodeComponent 를 구현한 클래스에 사용해주세요.
 * @see IBarcodeComponent
 *
 * @param order 우선순위, 낮을수록 먼저 초기화됨
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class BarcodeComponent(val order: Int = defaultComponentOrder)