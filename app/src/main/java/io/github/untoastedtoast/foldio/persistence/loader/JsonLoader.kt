package io.github.untoastedtoast.foldio.persistence.loader

import com.google.json.JsonSanitizer
import org.json.JSONObject

object JsonLoader {
    fun load(content: String): JSONObject {
        val sanitizedContent = JsonSanitizer.sanitize(content)
        return JSONObject(sanitizedContent)
    }
}
