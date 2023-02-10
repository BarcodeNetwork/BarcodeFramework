package com.vjh0107.barcode.framework.database.repository

import com.vjh0107.barcode.framework.database.datasource.BarcodeDataSource

interface RegisteredDataSourcesRepository {
    fun registerDataSource(id: String, dataSource: BarcodeDataSource)

    fun findDataSource(id: String): BarcodeDataSource?

    fun removeDataSource(id: String)

    fun getAllDataSources(): List<BarcodeDataSource>
}