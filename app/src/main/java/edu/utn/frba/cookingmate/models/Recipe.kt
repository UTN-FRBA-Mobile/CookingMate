package edu.utn.frba.cookingmate.models

class Recipe(
    val id: String,
    val name: String,
    val imageLink: String,
    val ingredientes: List<String>,
    val stories: MutableList<Story>
) {
    companion object {
        fun fromDocument(document: MutableMap<String, Any>): Recipe {
            return Recipe(
                document["id"].toString(),
                document["name"].toString(),
                document["imageLink"].toString(),
                listOf(),
                mutableListOf()
            )
        }
    }
}
