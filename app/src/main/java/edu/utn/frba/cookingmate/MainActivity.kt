package edu.utn.frba.cookingmate

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import edu.utn.frba.cookingmate.models.Recipe
import edu.utn.frba.cookingmate.models.Story
import edu.utn.frba.cookingmate.ui.main.MainFragment
import edu.utn.frba.cookingmate.ui.steps.StepsFragment

class MainActivity : AppCompatActivity(), MainFragment.OnFragmentInteractionListener {
    private lateinit var mainFragment: MainFragment
    private lateinit var stepsFragment: StepsFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // TODO find better way to do this
        try {
            this.supportActionBar!!.hide()
        } catch (e: NullPointerException) {
        }

        setContentView(R.layout.main_activity)

        mainFragment = MainFragment.newInstance()
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, mainFragment)
                .commitNow()
        }
    }

    override fun onViewRecipeSteps(recipe: Recipe) {
        stepsFragment = StepsFragment.newInstance(recipe)

        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)

        supportFragmentManager.beginTransaction().remove(mainFragment)
            .add(R.id.container, stepsFragment).commitNow()
    }

//    override fun onViewRecipeStories(recipeStories: List<Story>, startFromId: String) {
//
//    }
}
