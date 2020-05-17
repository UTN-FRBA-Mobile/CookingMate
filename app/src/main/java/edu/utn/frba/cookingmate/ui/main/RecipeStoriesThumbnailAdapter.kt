package edu.utn.frba.cookingmate.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.*
import com.bumptech.glide.request.RequestOptions
import edu.utn.frba.cookingmate.R
import edu.utn.frba.cookingmate.models.Recipe
import edu.utn.frba.cookingmate.models.Story
import edu.utn.frba.cookingmate.viewmodels.RecipeViewModel
import kotlinx.android.synthetic.main.fragment_recipe.view.*
import kotlinx.android.synthetic.main.story_thumbnail_fragment.view.*


class RecipeStoriesThumbnailAdapter(
    private val stories: MutableList<Story>
//    private val onClickViewStoryListener: (Recipe) -> Unit
) :
    RecyclerView.Adapter<RecipeStoriesThumbnailAdapter.MyViewHolder>() {

    private lateinit var recipeViewModels: Map<String, Story>

    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.story_thumbnail_fragment, parent, false)

        recipeViewModels = stories.map { story ->
            story.id to story
        }.toMap()

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val story = recipeViewModels.entries.toList()[position].value // TODO improve
        val view = holder.view

        view.authorName.text = story.authorName
        var requestOptions = RequestOptions()
        requestOptions = requestOptions.transforms(CircleCrop())

        Glide.with(view)
            .load(story.imageLink)
            .apply(requestOptions)
            .into(holder.view.thumbnailImage)
    }

    override fun getItemCount() = stories.size

}
