package edu.utn.frba.cookingmate.services

import android.content.Context
import android.graphics.Bitmap
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import edu.utn.frba.cookingmate.models.Profile
import edu.utn.frba.cookingmate.models.Recipe
import edu.utn.frba.cookingmate.models.Step
import java.io.ByteArrayOutputStream
import java.util.*

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
                    .setStorageBucket("cookingmate-d6d4b.appspot.com")
                    .build()
            )
        }

        private fun getDB(): FirebaseFirestore {
            return Firebase.firestore(firebaseAPP)
        }

        private fun getStorage(): FirebaseStorage {
            return Firebase.storage(firebaseAPP)
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

        fun getRecipe(recipeId: String, fn: (Recipe) -> Unit) {
            getDB().collection("recipes")
                .document(recipeId)
                .get()
                .addOnSuccessListener {
                    fn(Recipe.fromDocument(it.id, it.data!!))
                }
        }

        fun getSteps(recipe: Recipe, fn: (Recipe) -> Unit) {
            getDB().collection("recipes")
                .document(recipe.id)
                .collection("steps")
                .get()
                .addOnSuccessListener { query ->
                    recipe.steps = query.documents.map { Step.fromDocument(it.data!!) }
                    fn(recipe)
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

        fun addStory(recipeId: String, profile: Profile, image: Bitmap, fn: () -> Unit) {
            val baos = ByteArrayOutputStream()
            image.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val data = baos.toByteArray()

            getStorage()
                .reference
                .child("storiesImages/photo-${UUID.randomUUID()}.jpg")
                .putBytes(data)
                .addOnSuccessListener { r ->
                    r.metadata?.reference?.downloadUrl?.addOnSuccessListener { uri ->
                        val newStory = hashMapOf(
                            "profileId" to profile.id,
                            "imageLink" to uri.toString()
                        )

                        getDB().collection("recipes")
                            .document(recipeId)
                            .update("stories", FieldValue.arrayUnion(newStory))
                            .addOnSuccessListener { fn() }
                    }
                }
        }

        fun addComment(recipeId: String, profile: Profile, image: Bitmap, text: String, stepNumber: Int, fn: () -> Unit) {
            val baos = ByteArrayOutputStream()
            image.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val data = baos.toByteArray()

            getStorage()
                .reference
                .child("commentsImages/photo-${UUID.randomUUID()}.jpg")
                .putBytes(data)
                .addOnSuccessListener { r ->
                    r.metadata?.reference?.downloadUrl?.addOnSuccessListener { uri ->
                        val newComment = hashMapOf(
                            // TODO guarda mal el id
                            "authorId" to profile.id,
                            "imageLink" to uri.toString(),
                            "text" to text
                        )

                        val steps = getDB().collection("recipes")
                            .document(recipeId)
                            .collection("steps")
                        val comment = steps
                            .document(stepNumber.toString())
                        comment
                            .update("comments", FieldValue.arrayUnion(newComment))
                            .addOnSuccessListener { fn() }
                    }
                }
        }
    }
}
