package edu.utn.frba.cookingmate.models

class Step(
    val comments: List<Comment>,
    val description: String,
    val videoUrl: String
) {
    companion object {
        fun fromDocument(document: Map<String, Any>): Step {
            return Step(
                (document["comments"] as List<Map<String, Any>>).map { Comment.fromDocument(it) }.toMutableList(),
                document["description"].toString(),
                document["videoUrl"].toString()
            )
        }

    }
}