package com.example.micolornote.auxiliar

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import java.io.ByteArrayOutputStream

class Utiles {
    /**
     * Convierte un Bitmap a una cadena Base64
     *
     * @param bitmap Bitmap
     * @return Cadena Base64
     */
    fun bitmapToBase64(bitmap: Bitmap): String? {
        // Comrimimos al 60 % la imagen
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 60, stream)
        val byteArray = stream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    /**
     * Convierte una cadena Base64 a Bitmap
     *
     * @param b64String cadena Base 64
     * @return Bitmap
     */
    fun base64ToBitmap(b64String: String): Bitmap? {
        val imageAsBytes: ByteArray = Base64.decode(b64String, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.size)
    }

}