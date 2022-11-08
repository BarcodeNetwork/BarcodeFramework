package com.vjh0107.barcode.framework.modulepublisher

import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.extra

fun ExtensionAware.getExtra(key: String) = extra[key]?.toString()