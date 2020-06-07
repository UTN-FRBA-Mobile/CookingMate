package edu.utn.frba.cookingmate.models

class Profile(
    val name: String,
    val profileImageLink: String
) {
    companion object {
        fun fromDocument(document: Map<String, Any>): Profile {
            return Profile(
                document["name"].toString(),
                document["profileImageLink"].toString()
            )
        }
    }
}

