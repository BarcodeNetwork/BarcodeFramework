package com.vjh0107.barcode.framework.serialization

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 * @see SerializableData
 */
inline fun <reified T> T.serialize(json: Json = Json): String {
    return json.encodeToString(this)
}

/**
 * @see Collection
 */
inline fun <reified T : Collection<*>> T.serialize(json: Json = Json): String {
    return json.encodeToString(this)
}

/**
 * @see Map
 */
inline fun <reified T : Map<*, *>> T.serialize(json: Json = Json): String {
    return json.encodeToString(this)
}

/**
 * @see SerializableData
 */
inline fun <reified T> T.serialize(serializationStrategy: SerializationStrategy<T>, json: Json = Json): String {
    return json.encodeToString(serializationStrategy, this)
}

/**
 * @see Collection
 */
inline fun <reified T : Collection<*>> T.serialize(serializationStrategy: SerializationStrategy<T>, json: Json = Json): String {
    return json.encodeToString(serializationStrategy, this)
}

/**
 * @see Map
 */
inline fun <reified T : Map<*, *>> T.serialize(serializationStrategy: SerializationStrategy<T>, json: Json = Json): String {
    return json.encodeToString(serializationStrategy, this)
}


/**
 * @see SerializableData
 */
inline fun <reified T> String.deserialize(json: Json = Json): T {
    return json.decodeFromString(this)
}

/**
 * @see Collection
 */
inline fun <reified T : Collection<*>> String.deserializeCollection(json: Json = Json): T {
    return json.decodeFromString(this)
}

/**
 * @see Map
 */
inline fun <reified T : Map<*, *>> String.deserializeMap(json: Json = Json): T {
    return json.decodeFromString(this)
}

/**
 * @see SerializableData
 */
inline fun <reified T> String.deserialize(deserializationStrategy: DeserializationStrategy<T>, json: Json = Json): T {
    return json.decodeFromString(deserializationStrategy, this)
}

/**
 * @see Collection
 */
inline fun <reified T : Collection<*>> String.deserializeCollection(deserializationStrategy: DeserializationStrategy<T>, json: Json = Json): T {
    return json.decodeFromString(deserializationStrategy, this)
}

/**
 * @see Map
 */
inline fun <reified T : Map<*, *>> String.deserializeMap(deserializationStrategy: DeserializationStrategy<T>, json: Json = Json): T {
    return json.decodeFromString(deserializationStrategy, this)
}