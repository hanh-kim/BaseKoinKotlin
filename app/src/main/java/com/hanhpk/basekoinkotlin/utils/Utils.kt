package com.dmm.ptown.utils

import com.hanhpk.basekoinkotlin.AndroidApplication
import com.hanhpk.basekoinkotlin.utils.PreferencesUtil
import com.squareup.moshi.*
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.json.JSONException
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.util.regex.Matcher
import java.util.regex.Pattern


object Utils {
    @Throws(JSONException::class, IOException::class)
    fun loadJsonAsset(fileName: String): String? {
        val inputStream: InputStream =
            AndroidApplication.mInstance.assets.open(fileName)
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))
        val data = StringBuilder()
        var str = bufferedReader.readLine()
        while (str != null) {
            data.append(str)
            str = bufferedReader.readLine()
        }
        inputStream.close()
        bufferedReader.close()
        return data.toString()
    }

    fun <T> getArrayDataFromFile(fileName: String, cls: Class<T>): List<T> {
        val fileContent = loadJsonAsset(fileName)
        return getArrayDataFromString(fileContent, cls)
    }

    private fun <T> getArrayDataFromString(content: String?, cls: Class<T>): List<T> {
        content ?: return listOf()
        val type = Types.newParameterizedType(List::class.java, cls)
        val listAdapter: JsonAdapter<ArrayList<T>> = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
            .adapter(type)
        return listAdapter.fromJson(content) ?: listOf()
    }

    fun <T> getObjectDataFromFile(fileName: String, cls: Class<T>): T {
        val fileContent = loadJsonAsset(fileName)!!
        val adapter = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
            .adapter(cls)
        return adapter.fromJson(fileContent)!!
    }

    fun <T> getArrayDataFromStringWithJsonAdapters(fileName: String, cls: Class<T>, adapters: List<Any>): List<T> {
        val fileContent = loadJsonAsset(fileName)!!
        val type = Types.newParameterizedType(List::class.java, cls)
        val builder: Moshi.Builder = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
        adapters.forEach {
            val toJson = ToJson::class.java
            val fromJson = FromJson::class.java
            var hasToJson = false
            var hasFromJson = false
            it::class.java.methods.forEach { method ->
                if (method.isAnnotationPresent(toJson)) {
                    hasToJson = true
                }
                if (method.isAnnotationPresent(fromJson)) {
                    hasFromJson = true
                }
            }
            if (hasToJson && hasFromJson) {
                builder.add(it)
            }
        }
        val listAdapter: JsonAdapter<ArrayList<T>> = builder.build().adapter(type)
        return listAdapter.fromJson(fileContent) ?: listOf()
    }

    inline fun <reified T> getObjectDataFromStringWithJsonAdapters(
        fileName: String,
        cls: Class<T>,
        adapters: List<Any>
    ): T? {
        val fileContent = loadJsonAsset(fileName)!!
        val type = Types.newParameterizedType(T::class.java, cls)
        val builder: Moshi.Builder = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
        adapters.forEach {
            val toJson = ToJson::class.java
            val fromJson = FromJson::class.java
            var hasToJson = false
            var hasFromJson = false
            it::class.java.methods.forEach { method ->
                if (method.isAnnotationPresent(toJson)) {
                    hasToJson = true
                }
                if (method.isAnnotationPresent(fromJson)) {
                    hasFromJson = true
                }
            }
            if (hasToJson && hasFromJson) {
                builder.add(it)
            }
        }
        val jsonAdapter: JsonAdapter<T> = builder.build().adapter(type)
        return jsonAdapter.fromJson(fileContent)
    }

    fun handleRating(value: Double) : Float{
        var outputValue = value.toFloat()
        var intValue = value.toInt()

        if ( outputValue.div(intValue) != 1f){
            if (value < intValue + 0.5) {
                outputValue = intValue.toFloat()

            } else if (value >= intValue + 0.5 && value < intValue + 1) {
                outputValue = (intValue + 0.5).toFloat()
            }
        }

        return outputValue
    }

    fun isValidHexColor(hexColorStr : String) :Boolean{
        val colorPattern: Pattern = Pattern.compile("#([0-9a-fA-F]{3}|[0-9a-fA-F]{6}|[0-9a-fA-F]{8})$")
        val m: Matcher = colorPattern.matcher(hexColorStr)
        return m.matches()
    }
}