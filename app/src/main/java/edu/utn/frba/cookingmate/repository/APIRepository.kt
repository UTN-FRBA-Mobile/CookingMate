package edu.utn.frba.cookingmate.repository

import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.auth.internal.InternalAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.FirebaseAuthCredentialsProvider
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import edu.utn.frba.cookingmate.models.Recipe

class APIRepository {
    companion object {
        fun initialize(context: Context) {
            FirebaseApp.initializeApp(
                context,
                FirebaseOptions.Builder()
                    .build()
            )
        }

        private fun getDB(): FirebaseFirestore {
            return Firebase.firestore
        }

        fun getRecipes(fn: (List<Recipe>) -> Unit) {
            getDB().collection("recipes")
                .get()
                .addOnSuccessListener { query ->
                    fn(query.documents
                        .map { Recipe.fromDocument(it.data!!) })
                }
        }
    }
}