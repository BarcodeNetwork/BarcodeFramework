package com.vjh0107.barcode.framework.serialization.serializers

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.bukkit.potion.PotionEffectType

object PotionEffectTypeSerializer : KSerializer<PotionEffectType> {
    override val descriptor = PrimitiveSerialDescriptor("Bukkit.PotionEffectTypeSerializer", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: PotionEffectType) {
        encoder.encodeString(value.name)
    }

    override fun deserialize(decoder: Decoder): PotionEffectType {
        val string = decoder.decodeString()
        return PotionEffectType.getByName(string) ?: throw NullPointerException("$string 은 없는 포션이름입니다.")
    }
}