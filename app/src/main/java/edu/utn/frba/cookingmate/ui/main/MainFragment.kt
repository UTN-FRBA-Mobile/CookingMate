package edu.utn.frba.cookingmate.ui.main

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.utn.frba.cookingmate.R
import edu.utn.frba.cookingmate.models.Recipe
import edu.utn.frba.cookingmate.services.APIService
import edu.utn.frba.cookingmate.services.CameraService
import edu.utn.frba.cookingmate.services.StateService
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment() {
    val name = "MainFragment"
    private var listener: OnFragmentInteractionListener? = null
    private lateinit var recyclerView: RecyclerView
    private var recipes: List<Recipe> = listOf()

    companion object {
        fun newInstance() = MainFragment()

        var recipeIdCamera: String? = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        APIService.loadProfiles {
            APIService.getRecipes {
                recipes = it
                updateRecipes()
            }
        }
    }

    private fun updateRecipes() {
        val viewManager = LinearLayoutManager(this.context)
        val viewAdapter = RecipesAdapter(
            listener,
            recipes,
            listener!!::onViewRecipeSteps
        ) { recipeId ->
            CameraService.takePicture(this) {
                recipeIdCamera = recipeId

                startActivityForResult(
                    it,
                    CameraService.TAKE_PICTURE_REQUEST_CODE
                )
            }
        }

        recyclerView = feedRecycler.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            CameraService.TAKE_PICTURE_REQUEST_CODE ->
                if (resultCode == Activity.RESULT_OK) {
                    val profile = StateService.getCurrentProfile()

                    val imageBitmap = data!!.extras.get("data") as Bitmap

                    APIService.addStory(
                        recipeIdCamera!!,
                        profile,
                        imageBitmap
                    ) {
                        APIService.getRecipe(recipeIdCamera!!) { updatedRecipe ->
                            recipes =
                                recipes.map { if (it.id == recipeIdCamera) updatedRecipe else it }

                            updateRecipes()
                        }
                    }
                }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener {
        fun onViewRecipeSteps(recipe: Recipe)
        fun onViewRecipeStories(recipe: Recipe, profileId: String)
    }
}
