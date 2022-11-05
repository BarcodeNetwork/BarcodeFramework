package com.vjh0107.barcode.framework.exceptions

class LogicException : RuntimeException {
    constructor(): super("설계 결함입니다.")
    constructor(message: String): super(message)
}