package com.vjh0107.barcode.framework.serialization.serializers

import com.vjh0107.barcode.framework.serialization.data.PotionEffectWrapper
import kotlinx.serialization.KSerializer
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encodeToString
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import org.bukkit.potion.PotionEffect

object PotionEffectSerializer : KSerializer<PotionEffect> {
    override val descriptor = PrimitiveSerialDescriptor("Bukkit.PotionEffectSerializer", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: PotionEffect) {
        val potionEffectWrapper = PotionEffectWrapper.of(value)
        encoder.encodeString(Json.encodeToString(potionEffectWrapper))
    }

    override fun deserialize(decoder: Decoder): PotionEffect {
        val potionEffectWrapper = Json.decodeFromString<PotionEffectWrapper>(decoder.decodeString())
        return potionEffectWrapper.get()
    }
}