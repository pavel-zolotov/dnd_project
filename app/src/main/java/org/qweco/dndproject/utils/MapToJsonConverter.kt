package org.qweco.dndproject.utils
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

class MapToJsonConverter {
    /*
     * @Description: Method to convert Map to JSON String
     * @param: map Map<String, Int?>
     * @return: json String
     */
    fun convert(map: Map<String, Int?>): String {
        val gson = Gson()
        return gson.toJson(map)
    }

    /*
     * @Description: Method to convert JSON String to Map
     * @param: json String
     * @return: map Map<String, Int?>
     */
    fun revert(json: String): Map<String, Int?> {
        val gson = Gson()
        val type = object : TypeToken<Map<String, Int?>>() {}.type
        return gson.fromJson(json, type)
    }
}