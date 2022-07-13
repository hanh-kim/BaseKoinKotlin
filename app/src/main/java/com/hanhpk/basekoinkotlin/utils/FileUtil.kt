package com.hanhpk.basekoinkotlin.utils

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import com.hanhpk.basekoinkotlin.AndroidApplication
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

object FileUtil {
    fun saveBitmapToFile(
        path: String,
        bm: Bitmap?,
        name: String
    ): Boolean {
        //Create Path to save Image
        var saved: Boolean = false
        val file = File(path)
        if (file.exists()) file.delete()
        return try {
            //   val out = FileOutputStream(file)
            val out = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val resolver: ContentResolver = AndroidApplication.mInstance.contentResolver
                val contentValues = ContentValues()
                contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, path)
                contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
                contentValues.put(
                    MediaStore.MediaColumns.RELATIVE_PATH,
                    "DCIM/${System.currentTimeMillis()}"
                )

                val imageUri =
                    resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                resolver.openOutputStream(imageUri!!)
            } else FileOutputStream(file)

            bm?.let {
                saved = it.compress(Bitmap.CompressFormat.JPEG, 100, out)
            }

            out?.flush()
            out?.close()
            file.absolutePath
            saved
        } catch (e: Exception) {
            print("error io")
            false
        }
    }

    fun saveBitmapToDownloads(bitmap: Bitmap, name: String): String {
        var savedPath = ""
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val contentValues = ContentValues()
            contentValues.put(MediaStore.Downloads.DISPLAY_NAME, name)
            contentValues.put(MediaStore.Downloads.MIME_TYPE, "image/jpeg")
            contentValues.put(MediaStore.Downloads.IS_PENDING, true)
            contentValues.put(MediaStore.Downloads.RELATIVE_PATH, "Download/");
            val uri = MediaStore.Downloads.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
            val context = AndroidApplication.mInstance
            val mContentResolver = context.contentResolver
            val itemUri = mContentResolver.insert(uri, contentValues)
            if (itemUri != null) {
                try {
                    val outputStream = mContentResolver.openOutputStream(itemUri)
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                    outputStream?.flush()
                    outputStream?.close()
                    contentValues.put(MediaStore.Images.Media.IS_PENDING, false)
                    mContentResolver.update(itemUri, contentValues, null, null)
                    savedPath = File(itemUri.path).absolutePath
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        } else {
            val imagesDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS
            ).toString() + File.separator + Constants.IMAGE_IDENTIFY_PATH

            val file = File(imagesDir)
            if (!file.exists()) {
                file.mkdir()
            }
            val imageFile = File(imagesDir, "$name.jpg")
            val fos = FileOutputStream(imageFile)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
            savedPath = imageFile.absolutePath
        }

        return savedPath
    }

    fun getFileFromUri(context: Context?, contentURI: Uri): File? {
        return try {
            val inputStream = context?.contentResolver?.openInputStream(contentURI)
            val file = createTempFile()
            file.copyInputStreamToFile(inputStream!!)
            file
        } catch (e: java.lang.Exception) {
            null
        }
    }

    private fun File.copyInputStreamToFile(inputStream: InputStream) {
        inputStream.use { input ->
            this.outputStream().use { fileOut ->
                input.copyTo(fileOut)
            }
        }
    }

    fun deleteCache(context: Context) {
        try {
            val dir = context.cacheDir
            deleteDir(dir)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    private fun deleteDir(dir: File?): Boolean {
        return if (dir != null && dir.isDirectory) {
            val children = dir.list()
            for (i in children.indices) {
                deleteDir(File(dir, children[i]))
            }
            dir.deleteRecursively()
        } else if (dir != null && dir.isFile) {
            dir.deleteRecursively()
        } else {
            false
        }
    }
}