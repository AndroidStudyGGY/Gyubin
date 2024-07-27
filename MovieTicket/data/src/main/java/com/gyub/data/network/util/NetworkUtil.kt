package com.gyub.data.network.util

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject

/**
 *
 *
 * @author   Gyub
 * @created  2024/07/25
 */
object NetworkUtil {
    fun getPrettyLog(text: String?): String {
        text ?: return ""

        return try {
            val json = Json { prettyPrint = true }
            val jsonElement = json.decodeFromString<JsonObject>(text)
            json.encodeToString(JsonObject.serializer(), jsonElement)
        } catch (e: Exception) {
            text
        }
    }
}