package org.qweco.dndproject.utils
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

class MapToJsonConverter {
    /*
     * @Description: Method to convert Map to JSON String
     * @param: map LinkedHashMap<String, Int?>
     * @return: json String
     */
    fun convert(map: LinkedHashMap<String, Int?>): String {
        val gson = Gson()
        return gson.toJson(map)
    }

    /*
     * @Description: Method to convert JSON String to Map
     * @param: json String
     * @return: map LinkedHashMap<String, Int?>
     */
    fun revert(json: String): LinkedHashMap<String, Int?> {
        val gson = Gson()
        val type = object : TypeToken<LinkedHashMap<String, Int?>>() {}.type
        return gson.fromJson(json, type)
    }
}