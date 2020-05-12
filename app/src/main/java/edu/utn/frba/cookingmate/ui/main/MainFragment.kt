package edu.utn.frba.cookingmate.ui.main

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.utn.frba.cookingmate.R
import edu.utn.frba.cookingmate.models.Recipe
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment() {
    private var listener: OnFragmentInteractionListener? = null
    private lateinit var recyclerView: RecyclerView

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel

        val myDataset =
            mutableListOf(
                Recipe(
                    "id2",
                    "Chocotorta",
                    "https://nitrocdn.com/UAvWdfoTcwEcpuKzJJEHxyeFHHtyYDjU/assets/static/source/rev-5a34344/wp-content/uploads/2017/06/vanilla-cake-4.jpg",
                    listOf("2 Paquetes de chocolinas", "1 Kilogramo de dulce de leche", "1 kilogramo de queso crema", "1 taza de leche")
                ),
                Recipe(
                    "id1",
                    "Torta Rogel",
                    "https://nitrocdn.com/UAvWdfoTcwEcpuKzJJEHxyeFHHtyYDjU/assets/static/source/rev-5a34344/wp-content/uploads/2016/04/cookie_dough_chocolate_cake-2.jpg",
                    listOf()
                ),
                Recipe(
                    "id3",
                    "Pastafrola",
                    "https://nitrocdn.com/UAvWdfoTcwEcpuKzJJEHxyeFHHtyYDjU/assets/static/source/rev-5a34344/wp-content/uploads/2018/01/french-silk-pie-cake-3.jpg",
                    listOf()
                )
            )

        val viewManager = LinearLayoutManager(this.context)
        val viewAdapter = RecipesAdapter(myDataset) { listener!!.onViewRecipeSteps(it) }

        recyclerView = feedRecycler.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener {
        fun onViewRecipeSteps(recipe: Recipe)
    }
}
