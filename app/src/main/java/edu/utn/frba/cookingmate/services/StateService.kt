package edu.utn.frba.cookingmate.services

import edu.utn.frba.cookingmate.models.Profile

object StateService {
    fun getCurrentProfile(): Profile = Profile(
        "a-user-id",
        "Current User ID",
        "https://firebasestorage.googleapis.com/v0/b/cookingmate-d6d4b.appspot.com/o/profilesImages%2Fphoto_2020-03-17_17-44-05.jpg?alt=media"
    )
}