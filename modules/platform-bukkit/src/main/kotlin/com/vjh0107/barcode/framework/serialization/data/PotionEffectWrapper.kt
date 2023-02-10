package com.vjh0107.barcode.framework.serialization.data

import com.vjh0107.barcode.framework.serialization.SerializableData
import com.vjh0107.barcode.framework.serialization.serializers.PotionEffectTypeSerializer
import kotlinx.serialization.Serializable
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

@Serializable
data class PotionEffectWrapper(
    val amplifier: Int = 0,
    val duration: Int = 0,
    @Serializable(with = PotionEffectTypeSerializer::class) val type: PotionEffectType = PotionEffectType.SPEED,
    val ambient: Boolean = false,
    val particles: Boolean = true,
    val icon: Boolean = true
) : SerializableData {
    companion object {
        fun of(potionEffect: PotionEffect): PotionEffectWrapper {
            return PotionEffectWrapper(potionEffect.amplifier, potionEffect.duration, potionEffect.type, potionEffect.isAmbient, potionEffect.hasParticles(), potionEffect.hasIcon())
        }
    }
    fun get(): PotionEffect {
        return PotionEffect(type, duration, amplifier, ambient, particles, icon)
    }
}