package com.vjh0107.barcode.framework.database.repository.impl

import com.vjh0107.barcode.framework.database.datasource.BarcodeDataSource
import com.vjh0107.barcode.framework.database.repository.RegisteredDataSourcesRepository
import org.koin.core.annotation.Single
import java.util.*

@Single(binds = [RegisteredDataSourcesRepository::class])
class RegisteredDataSourcesRepositoryImpl : RegisteredDataSourcesRepository {
    private val dataSources: MutableMap<String, BarcodeDataSource> = Collections.synchronizedMap(HashMap())

    override fun registerDataSource(id: String, dataSource: BarcodeDataSource) {
        dataSources[id] = dataSource
    }

    override fun findDataSource(id: String): BarcodeDataSource? {
        dataSources[id]
        return dataSources[id]
    }

    override fun removeDataSource(id: String) {
        dataSources.remove(id)
    }

    override fun getAllDataSources(): List<BarcodeDataSource> {
        return dataSources.values.toList()
    }
}