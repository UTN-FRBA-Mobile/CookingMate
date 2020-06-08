package edu.utn.frba.cookingmate.models

class Story(
    val profileId: String,
    val imageLink: String
) {
    companion object {
        fun fromDocument(document: Map<String, Any>): Story {
            return Story(
                document["profileId"].toString(),
                document["imageLink"].toString()
            )
        }
    }
}

