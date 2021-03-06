package edu.utn.frba.cookingmate.ui.storiesthumbnail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import edu.utn.frba.cookingmate.R
import edu.utn.frba.cookingmate.models.Story
import edu.utn.frba.cookingmate.services.APIService
import edu.utn.frba.cookingmate.services.StateService
import kotlinx.android.synthetic.main.story_thumbnail_fragment.view.*

class StoriesThumbnailAdapter(
    stories: List<Story>,
    private val onClickViewRecipeStoriesListener: (List<Story>, String) -> Unit
) :
    RecyclerView.Adapter<StoriesThumbnailAdapter.MyViewHolder>() {

    private var recipeViewModels: List<Story> = stories.sortedBy { it.profileId != StateService.getCurrentProfile().id }

    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.story_thumbnail_fragment, parent, false)

        return MyViewHolder(
            view
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val story = recipeViewModels[position] // TODO improve
        val view = holder.view
        val profile = APIService.profilesMap[story.profileId]!!

        view.authorName.text = profile.name
        var requestOptions = RequestOptions()
        requestOptions = requestOptions.transforms(CircleCrop())

        Glide.with(view)
            .load(profile.profileImageLink)
            .apply(requestOptions)
            .into(holder.view.thumbnailImage)

        holder.view.setOnClickListener { onClickViewRecipeStoriesListener(recipeViewModels, profile.id) }
    }

    override fun getItemCount() = recipeViewModels.size
}
