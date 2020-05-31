package edu.utn.frba.cookingmate.models

class Story(
    val authorName: String,
    val imageLink: String
) {
    companion object {
        fun fromDocument(document: Map<String, Any>): Story {
            return Story(
                document["name"].toString(),
                document["imageLink"].toString()
            )
        }
    }
}

