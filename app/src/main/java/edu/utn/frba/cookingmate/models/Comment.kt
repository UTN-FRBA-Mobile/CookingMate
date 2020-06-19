package edu.utn.frba.cookingmate.models

class Comment(
    val authorId: String,
    val text: String,
    val imageLink: String
) {
    companion object {
        fun fromDocument(document: Map<String, Any>): Comment {
            return Comment(
                document["authorId"].toString(),
                document["text"].toString(),
                document["imageLink"].toString()
            )
        }
    }
}