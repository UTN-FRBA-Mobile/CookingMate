package edu.utn.frba.cookingmate.ui.steps

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import edu.utn.frba.cookingmate.R
import edu.utn.frba.cookingmate.models.Recipe
import kotlinx.android.synthetic.main.fragment_steps.*

class StepsFragment(val recipe: Recipe) : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        stepsVideo.setVideoURI(Uri.parse("android.resource://" + activity!!.packageName + "/" + R.raw.foo))
        stepsVideo.start()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_steps, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(recipe: Recipe) =
            StepsFragment(recipe).apply {
                arguments = Bundle().apply {}
            }
    }
}
