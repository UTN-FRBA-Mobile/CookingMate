package edu.utn.frba.cookingmate

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import edu.utn.frba.cookingmate.models.Recipe
import edu.utn.frba.cookingmate.models.Story
import edu.utn.frba.cookingmate.services.APIService
import edu.utn.frba.cookingmate.ui.main.MainFragment
import edu.utn.frba.cookingmate.ui.steps.StepCommentFragment
import edu.utn.frba.cookingmate.ui.steps.StepsFragment
import edu.utn.frba.cookingmate.ui.stories.StoriesFragment

class MainActivity : AppCompatActivity(), MainFragment.OnFragmentInteractionListener,
    StoriesFragment.OnFragmentInteractionListener {
    private lateinit var mainFragment: MainFragment
    private lateinit var stepsFragment: StepsFragment
    private lateinit var storiesFragment: StoriesFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        APIService.initialize(this)

        this.supportActionBar?.hide()

        setContentView(R.layout.main_activity)

        mainFragment = MainFragment.newInstance()
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, mainFragment)
                .commitNow()
        }
    }

    override fun onViewRecipeSteps(recipe: Recipe) {
        if (recipe.steps.isEmpty()) {
            APIService.getSteps(recipe) { completeRecipe -> setStepFragment(completeRecipe) }
        } else {
            setStepFragment(recipe)
        }
    }

    private fun setCommentFragment(callback: (String) -> Unit) {
        val stepCommentFragment = StepCommentFragment { comment: String? ->
            comment?.let(callback)
            supportFragmentManager.popBackStackImmediate()
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, stepCommentFragment)
            .addToBackStack(stepCommentFragment.name)
            .commit()

    }

    private fun setStepFragment(recipe: Recipe) {
        stepsFragment = StepsFragment.newInstance(recipe, this::setCommentFragment)

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, stepsFragment)
            .addToBackStack(stepsFragment.name)
            .commit()

    }

    override fun onViewRecipeStories(stories: List<Story>, profileId: String) {
        storiesFragment = StoriesFragment.newInstance(stories, profileId)

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, storiesFragment)
            .addToBackStack(storiesFragment.name)
            .commit()
    }

    override fun onViewMainFeed() {
        supportFragmentManager.popBackStack()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0)
            supportFragmentManager.popBackStackImmediate()
        else super.onBackPressed()
    }
}
