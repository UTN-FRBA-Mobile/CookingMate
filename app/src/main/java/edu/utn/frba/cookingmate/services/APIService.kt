package edu.utn.frba.cookingmate.services

import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import edu.utn.frba.cookingmate.models.Profile
import edu.utn.frba.cookingmate.models.Recipe

class APIService {
    companion object {
        private lateinit var firebaseAPP: FirebaseApp
        val profilesMap: MutableMap<String, Profile> = mutableMapOf()

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
                            .map { Recipe.fromDocument(it.id, it.data!!) })
                }
        }

        fun loadProfiles(fn: () -> Unit) {
            getDB().collection("profiles")
                .get()
                .addOnSuccessListener { query ->
                    query.documents
                        .map {
                            profilesMap.put(
                                it.id, Profile.fromDocument(it.id, it.data!!)
                            )
                        }

                    fn()
                }
        }
    }
}
