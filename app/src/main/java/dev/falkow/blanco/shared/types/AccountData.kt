package dev.falkow.blanco.shared.types

import android.net.Uri

data class AccountData (
    val displayName: String?,
    val email: String?,
    val photoUrl: Uri?,
    val emailVerified: Boolean,

    // The user's ID, unique to the Firebase project. Do NOT use this value to
    // authenticate with your backend server, if you have one. Use
    // FirebaseUser.getIdToken() instead.
    val uid: String,
)