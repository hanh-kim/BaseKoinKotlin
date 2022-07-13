package com.hanhpk.basekoinkotlin.utils

import android.content.res.AssetManager
import com.hanhpk.basekoinkotlin.AndroidApplication
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.nio.charset.Charset

object CSVFileUtil {
    fun readCsvFile(fileName: String): List<Array<String>> {
        val keywordsList: MutableList<Array<String>> = mutableListOf()
        val assetManager: AssetManager = AndroidApplication.mInstance.assets
        try {
            val csvStream: InputStream = assetManager.open(fileName)
            val csvStreamReader = InputStreamReader(csvStream, Charset.forName("Shift-JIS"))
            val reader = BufferedReader(csvStreamReader)
            var line: String? = ""
            // Step over headers
            reader.readLine()
            // If buffer is not empty
            while (reader.readLine().also { line = it } != null) {
                // use comma as separator columns of CSV
                line?.let {
                    val tokens = it.split(",").toTypedArray()
                    keywordsList.add(tokens)
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return keywordsList
    }
}