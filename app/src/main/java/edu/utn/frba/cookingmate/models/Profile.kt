package edu.utn.frba.cookingmate.models

class Profile(
    val id: String,
    val name: String,
    val profileImageLink: String
) {
    companion object {
        fun fromDocument(id: String, document: Map<String, Any>): Profile {
            return Profile(
                id,
                document["name"].toString(),
                document["profileImageLink"].toString()
            )
        }
    }
}

