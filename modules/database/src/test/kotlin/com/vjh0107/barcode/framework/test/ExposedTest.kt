package com.vjh0107.barcode.framework.test

import com.vjh0107.barcode.framework.database.config.impl.HikariDatabaseConfig
import com.vjh0107.barcode.framework.database.datasource.impl.HikariBarcodeDataSource
import com.vjh0107.barcode.framework.database.exposed.entity.BarcodePlayerEntity
import com.vjh0107.barcode.framework.database.exposed.entity.BarcodeEntityClass
import com.vjh0107.barcode.framework.database.exposed.table.BarcodeIDTable
import com.vjh0107.barcode.framework.database.player.MinecraftPlayerID
import com.vjh0107.barcode.framework.database.player.multiprofile.ProfileID
import com.vjh0107.barcode.framework.utils.print
import io.kotest.core.spec.style.AnnotationSpec
import kotlinx.coroutines.*
import kotlinx.coroutines.test.runTest
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.SchemaUtils.createMissingTablesAndColumns
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime
import java.util.*

class ExposedTest : AnnotationSpec() {
    val host = TestDatabaseHost.get

    object TestTable : BarcodeIDTable("test_table1") { // 1
        val content = text("content")
        val done = bool("done").default(false)
        val createdAt = datetime("created_at").index().default(LocalDateTime.now())
        val updatedAt = datetime("updated_at").default(LocalDateTime.now())
    }

    class TestEntity(id: EntityID<Int>) : BarcodePlayerEntity<TestTable>(id, TestTable) { // 2
        companion object : BarcodeEntityClass<TestEntity>(TestTable)

        var content by TestTable.content
        var done by TestTable.done
        var createdAt by TestTable.createdAt
        var updatedAt by TestTable.updatedAt
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun databaseTest() = runTest {
        val barcodeHikariConfig = HikariDatabaseConfig(host)
        val hikariDataSource = HikariBarcodeDataSource(barcodeHikariConfig)
        println("entering coroutine scope...")
        val mutableList = mutableListOf<String>()

        val profileID = ProfileID.of(UUID.randomUUID())
        val profileID2 = ProfileID.of(UUID.randomUUID())

        withContext(Dispatchers.IO) {
            hikariDataSource.query {
                createMissingTablesAndColumns(TestTable)
            }
            hikariDataSource.query {
                val playerID = MinecraftPlayerID(UUID.randomUUID())

                TestEntity.new(profileID, playerID) { this.content = "testContentS12112412" }
                TestEntity.new(profileID2, playerID) { this.content = "testContentS12212412562" }

                TestEntity.findByMinecraftPlayerID(playerID).forEach {
                    it.content = "asdfasdfas4taw4a34"
                }
            }
        }
        withContext(Dispatchers.IO) {
            hikariDataSource.query {
                val content = TestEntity.findByProfileID(profileID)!!.content.print("got: ")
                mutableList.add(content)
            }
        }
        println("values...")
        mutableList.forEach{
            println(it)
        }
    }
}
