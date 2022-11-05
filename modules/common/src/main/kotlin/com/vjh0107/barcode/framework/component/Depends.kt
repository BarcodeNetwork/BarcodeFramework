package com.vjh0107.barcode.framework.component

import kotlin.reflect.KClass

annotation class Depends(val depends: Array<KClass<*>> = [])
