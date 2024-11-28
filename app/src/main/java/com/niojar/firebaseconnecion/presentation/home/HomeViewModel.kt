package com.niojar.firebaseconnecion.presentation.home


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.niojar.firebaseconnecion.presentation.model.Artist
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class HomeViewModel: ViewModel() {
    private var db: FirebaseFirestore = Firebase.firestore
    private val _artist = MutableStateFlow<List<Artist>>(emptyList())
    val artist: StateFlow<List<Artist>> = _artist

    init {
        // loadData()
        getArtist()
    }

    fun loadData() {
        val random = (1..1000).random()
        val artist = Artist(
            name = "Random $random",
            description = "Description number $random",
            image = "https://th.bing.com/th/id/OIP.FZ1cK8Ee-M6WLLHiz2c3-wHaFj?w=224&h=180&c=7&r=0&o=5&pid=1.7"
        )
        db.collection("artists").add(artist)
            .addOnSuccessListener {
                Log.i("master", "SUCCESS")
            }
            .addOnFailureListener {
                Log.i("master", "FAILURE")
            }
            .addOnCompleteListener {
                Log.i("master", "COMPLETE")
            }
    }


    private fun getArtist(){
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO){
                getAllArtist()
            }
            _artist.value = result
        }
    }

    suspend fun getAllArtist():List<Artist>{
        return try {
            db.collection("artists")
                .get()
                .await()
                .documents
                .mapNotNull { snapshot -> snapshot.toObject(Artist::class.java) }
        } catch (e:Exception) {
            Log.i("master", e.toString())
            emptyList()
        }
    }
}

