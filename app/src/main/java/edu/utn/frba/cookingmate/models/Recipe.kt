package edu.utn.frba.cookingmate.models

class Recipe(
    val id: String,
    val name: String,
    val imageLink: String,
    val ingredients: List<String>,
    val stories: MutableList<Story>,
    val steps: List<Step>
) {
    companion object {
        fun fromDocument(id: String, document: MutableMap<String, Any>): Recipe {
            return Recipe(
                id,
                document["name"].toString(),
                document["imageLink"].toString(),
                (document["ingredients"] as List<String>).toList(),
                (document["stories"] as List<Map<String, Any>>).map { Story.fromDocument(it) }
                    .toMutableList(),
                (document["steps"] as List<Map<String, Any>>).map { Step.fromDocument(it) }.toMutableList()
            )
        }
    }
}
