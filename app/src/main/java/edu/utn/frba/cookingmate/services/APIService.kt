package edu.utn.frba.cookingmate.services

import android.content.Context
import android.net.Uri
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import edu.utn.frba.cookingmate.models.Profile
import edu.utn.frba.cookingmate.models.Recipe

class APIService {
    companion object {
        private lateinit var firebaseAPP: FirebaseApp

        fun initialize(context: Context) {
            firebaseAPP = FirebaseApp.initializeApp(
                context,
                FirebaseOptions.Builder()
                    .setApplicationId("1:803809299345:android:ca5dae186a385dcd05a830")
                    .setProjectId("cookingmate-d6d4b")
                    .build()
            )
        }

        private fun getDB(): FirebaseFirestore {
            return Firebase.firestore(firebaseAPP)
        }

        fun getRecipes(fn: (List<Recipe>) -> Unit) {
            getDB().collection("recipes")
                .get()
                .addOnSuccessListener { query ->
                    fn(
                        query.documents
                            .map { document ->
                                Recipe.fromDocument(document.id, document.data!!)
                            })
                }
        }

        fun getProfiles(fn: (List<Profile>) -> Unit) {
            getDB().collection("profiles")
                .get()
                .addOnSuccessListener { query ->
                    fn(query.documents
                        .map { Profile.fromDocument(it.data!!) })
                }
        }
    }
}
