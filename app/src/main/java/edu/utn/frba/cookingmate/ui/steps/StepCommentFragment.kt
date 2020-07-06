package edu.utn.frba.cookingmate.ui.steps

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import edu.utn.frba.cookingmate.R
import edu.utn.frba.cookingmate.extensions.hideKeyboard
import edu.utn.frba.cookingmate.extensions.showKeyboard
import kotlinx.android.synthetic.main.fragment_step_comment.*

class StepCommentFragment(val callback: (String?) -> Unit) : Fragment() {
    val name = "StepCommentFragment"

//    companion object {
//        @JvmStatic
//        fun newInstance() =
//            StoriesFragment(recipe, profileId)
//    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_step_comment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        view!!.requestFocus()
        showKeyboard()

        addCommentButton.setOnClickListener {
            callback(newComment.text.toString())
            hideKeyboard()
        }
        cancelButton.setOnClickListener {
            callback(null)
            hideKeyboard()
        }
    }
}