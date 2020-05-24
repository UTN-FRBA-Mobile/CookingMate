package edu.utn.frba.cookingmate.ui.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.utn.frba.cookingmate.R
import edu.utn.frba.cookingmate.models.Recipe
import edu.utn.frba.cookingmate.models.Story
import edu.utn.frba.cookingmate.ui.storiesthumbnail.StoriesThumbnailFragment
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment() {
    private var listener: OnFragmentInteractionListener? = null
    private lateinit var recyclerView: RecyclerView

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val myDataset =
            mutableListOf(
                Recipe(
                    "id2",
                    "Chocotorta",
                    "https://nitrocdn.com/UAvWdfoTcwEcpuKzJJEHxyeFHHtyYDjU/assets/static/source/rev-5a34344/wp-content/uploads/2017/06/vanilla-cake-4.jpg",
                    listOf(
                        "2 Paquetes de chocolinas",
                        "1 Kilogramo de dulce de leche",
                        "1 kilogramo de queso crema",
                        "1 taza de leche"
                    ),
                    mutableListOf(
                        Story(
                            "id11",
                            "Leandr0",
                            "https://images.unsplash.com/photo-1577219491135-ce391730fb2c?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=625&q=80"
                        ),
                        Story(
                            "id12",
                            "guille",
                            "https://images.unsplash.com/flagged/photo-1561350117-501b4661f8d4?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60"
                        ),
                        Story(
                            "id13",
                            "joaco",
                            "https://images.unsplash.com/photo-1566554273541-37a9ca77b91f?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60"
                        )
                    )
                ),
                Recipe(
                    "id1",
                    "Torta Rogel",
                    "https://nitrocdn.com/UAvWdfoTcwEcpuKzJJEHxyeFHHtyYDjU/assets/static/source/rev-5a34344/wp-content/uploads/2016/04/cookie_dough_chocolate_cake-2.jpg",
                    listOf(),
                    mutableListOf(
                        Story(
                            "id14",
                            "j04c0",
                            "https://images.unsplash.com/photo-1574966740429-6158cb530208?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60"
                        ),
                        Story(
                            "id15",
                            "guishe",
                            "https://images.unsplash.com/photo-1518148750009-25b2522df9c2?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60"
                        ),
                        Story(
                            "id11",
                            "Leandr0",
                            "https://images.unsplash.com/photo-1577219491135-ce391730fb2c?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=625&q=80"
                        ),
                        Story(
                            "id111",
                            "Braian",
                            "https://images.unsplash.com/photo-1562775897-0e19642acd57?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60"
                        ),
                        Story(
                            "id101",
                            "Ana",
                            "https://images.unsplash.com/photo-1565608087341-404b25492fee?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60"
                        ),
                        Story(
                            "id110",
                            "Rocio",
                            "https://images.unsplash.com/photo-1563233558-0015df629653?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=634&q=80"
                        ),
                        Story(
                            "id102",
                            "Pedro",
                            "https://images.unsplash.com/photo-1518148750009-25b2522df9c2?ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60"
                        )
                    )
                ),
                Recipe(
                    "id3",
                    "Pastafrola",
                    "https://nitrocdn.com/UAvWdfoTcwEcpuKzJJEHxyeFHHtyYDjU/assets/static/source/rev-5a34344/wp-content/uploads/2018/01/french-silk-pie-cake-3.jpg",
                    listOf(),
                    mutableListOf(
                        Story(
                            "id16",
                            "xxxxxxxx",
                            "https://images.unsplash.com/photo-1502364271109-0a9a75a2a9df?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60"
                        ),
                        Story(
                            "id17",
                            "wwwwwww",
                            "https://images.unsplash.com/photo-1542834291-c514e77b215f?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60"
                        ),
                        Story(
                            "id18",
                            "qweqweqe",
                            "https://images.unsplash.com/photo-1552302894-3b4f7275229c?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60"
                        )
                    )
                )
            )

        val viewManager = LinearLayoutManager(this.context)
        val viewAdapter = RecipesAdapter(
            myDataset,
            { listener!!.onViewRecipeSteps(it) },
            { fragmentContainer: View, recipe: Recipe ->
                parentFragmentManager.beginTransaction().replace(
                    fragmentContainer.id,
                    StoriesThumbnailFragment.newInstance(recipe)
                ).commit()
            })

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
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
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
