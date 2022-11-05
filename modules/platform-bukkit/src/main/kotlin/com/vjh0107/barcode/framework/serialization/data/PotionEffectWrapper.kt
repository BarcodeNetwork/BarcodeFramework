package com.vjh0107.barcode.framework.serialization.data

import com.vjh0107.barcode.framework.serialization.SerializableData
import com.vjh0107.barcode.framework.serialization.serializers.PotionEffectTypeSerializer
import kotlinx.serialization.Serializable
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

@Serializable
data class PotionEffectWrapper(
    @Serializable(with = PotionEffectTypeSerializer::class) val type: PotionEffectType,
    val duration: Double,
    val level: Int
) : SerializableData {
    /**
     * 버킷 이펙트로 변환합니다.
     */
    fun toEffect(ambient: Boolean = true, particles: Boolean = false): PotionEffect {
        return PotionEffect(type, (duration * 20).toInt(), level - 1, ambient, particles)
    }
}