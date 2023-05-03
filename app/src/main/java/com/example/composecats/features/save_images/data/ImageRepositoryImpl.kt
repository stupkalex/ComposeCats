package com.example.composecats.features.save_images.data

import android.Manifest
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Environment
import android.widget.Toast
import com.example.composecats.core.database.CatsDao
import com.example.composecats.core.entity.CatEntity
import com.example.composecats.core.utils.permissionManager
import com.example.composecats.features.save_images.domain.ImageRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.*
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val database: CatsDao,
    private val context: Context
) : ImageRepository {

    private val ioCoroutineScope = CoroutineScope(Dispatchers.IO)

    override fun saveImageToCache(drawable: Drawable, catEntity: CatEntity) {
        ioCoroutineScope.launch {
            val bitmap = (drawable as BitmapDrawable).bitmap
            val contextWrapper = ContextWrapper(context)
            val directory = contextWrapper.getDir("imageDir", Context.MODE_PRIVATE)
            val file = File(directory, "${catEntity.id}.jpg")
            val fos = FileOutputStream(file)
            try {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
                fos.flush()
                database.updateCat(
                    catEntity.copy(
                        localUrl = file.absolutePath,
                        isDownload = true
                    )
                )
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                fos.close()
            }

        }
    }

    override fun saveImageToDownload(catEntity: CatEntity) {
        ioCoroutineScope.launch {
            permissionManager.requestPermission(
                arrayOf(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ),
                actionGranted = {
                    downloadImage(catEntity)
                }
            )
        }

    }

    private fun downloadImage(catEntity: CatEntity) {
        ioCoroutineScope.launch {
            var inputStream: InputStream? = null
            var outputStream: OutputStream? = null
            try {
                val directory =
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                val cacheFile = File(catEntity.localUrl)
                val newFile = File(directory, "${catEntity.id}.jpg")
                outputStream = newFile.outputStream()
                inputStream = cacheFile.inputStream()
                val fileReader = ByteArray(1024 * 4)
                var isRead = true
                while (isRead) {
                    val read = inputStream.read(fileReader)
                    if (read == -1) {
                        isRead = false
                        withContext(Dispatchers.Main) {
                            Toast.makeText(context, "Downloaded -> ${newFile.absolutePath}", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        outputStream.write(fileReader, 0, read)
                    }
                }
            } catch (e: IOException) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show()
                }
            } finally {
                inputStream?.close()
                outputStream?.close()
            }
        }
    }
}