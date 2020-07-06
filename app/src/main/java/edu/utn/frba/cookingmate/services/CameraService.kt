package edu.utn.frba.cookingmate.services

import android.Manifest
import android.content.Intent
import android.provider.MediaStore
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.tbruyelle.rxpermissions2.RxPermissions

class CameraService {
    companion object {
        const val TAKE_PICTURE_REQUEST_CODE: Int = 1998

        fun takePicture(fragment: Fragment, fn: (Intent) -> Unit) {
            val rxPermissions = RxPermissions(fragment)

            rxPermissions
                .request(Manifest.permission.CAMERA)
                .subscribe { granted ->
                    if (granted) {
                        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
                            fn(takePictureIntent)
                        }
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
    }
}
