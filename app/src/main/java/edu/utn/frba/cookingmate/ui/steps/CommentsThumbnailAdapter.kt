package edu.utn.frba.cookingmate.ui.steps

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import edu.utn.frba.cookingmate.R
import edu.utn.frba.cookingmate.models.Comment
import kotlinx.android.synthetic.main.comment_thumbnail_fragment.view.*

class CommentsThumbnailAdapter(
    private val comments: List<Comment>
) :
    RecyclerView.Adapter<CommentsThumbnailAdapter.MyViewHolder>() {

    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.comment_thumbnail_fragment, parent, false)

        return MyViewHolder(
            view
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val comment = comments[position] // TODO improve
        val view = holder.view

        view.comment.text = comment.text

        Glide.with(view)
            .load(comment.imageLink)
            .into(view.commentPicture)
    }

    override fun getItemCount() = comments.size
}
