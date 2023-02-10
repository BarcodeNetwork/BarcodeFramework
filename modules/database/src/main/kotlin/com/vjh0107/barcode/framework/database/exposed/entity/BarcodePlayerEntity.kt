package com.vjh0107.barcode.framework.database.exposed.entity

import com.vjh0107.barcode.framework.database.exposed.table.BarcodeIDTable
import com.vjh0107.barcode.framework.database.player.MinecraftPlayerID
import com.vjh0107.barcode.framework.database.player.multiprofile.ProfileID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.id.EntityID

abstract class BarcodePlayerEntity<T : BarcodeIDTable>(id: EntityID<Int>, barcodeIDTable: T) : IntEntity(id) {
    /**
     * ProfileID UUID
     */
    var profileID: ProfileID by barcodeIDTable.profileID

    /**
     * PlayerID (마인크래프트 UUID)
     */
    var playerID: MinecraftPlayerID by barcodeIDTable.playerID
}