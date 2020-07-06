package edu.utn.frba.cookingmate.services

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.tbruyelle.rxpermissions2.RxPermissions
import edu.utn.frba.cookingmate.BuildConfig
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class CameraService {
    companion object {
        const val TAKE_PICTURE_STORY_REQUEST_CODE: Int = 1998
        const val TAKE_PICTURE_COMMENT_REQUEST_CODE: Int = 1999

        fun takePicture(fragment: Fragment, fn: (Intent, Uri) -> Unit) {
            val rxPermissions = RxPermissions(fragment)

            rxPermissions
                .request(Manifest.permission.CAMERA)
                .subscribe { granted ->
                    if (granted) {
                        dispatchTakePictureIntent(fragment.context!!, fn)
                    } else {
                        Toast.makeText(
                            fragment.context,
                            //TODO Remove hardcoded text
                            "Debe habilitar los permisos para utilizar la camara",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
                .dispose()
        }

        private fun dispatchTakePictureIntent(context: Context, fn: (Intent, Uri) -> Unit) {
            Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
                    val photoFile: File? = createImageFile(context)
                    // Continue only if the File was successfully created
                    photoFile?.also {
                        val currentURI = FileProvider.getUriForFile(
                            context,
                            BuildConfig.APPLICATION_ID,
                            it
                        )
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, currentURI)
                        fn(takePictureIntent, currentURI)
                    }
                }
            }

        private fun createImageFile(context: Context): File {
            // Create an image file name
            val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            val storageDir: File = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
            return File.createTempFile(
                "JPEG_${timeStamp}_", /* prefix */
                ".jpg", /* suffix */
                storageDir /* directory */
            )
        }
    }
}
