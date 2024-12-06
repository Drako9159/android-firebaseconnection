package com.niojar.firebaseconnecion.presentation.home


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.niojar.firebaseconnecion.presentation.model.Artist
import com.niojar.firebaseconnecion.presentation.model.Player
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class HomeViewModel: ViewModel() {
    private var db: FirebaseFirestore = Firebase.firestore
    private val database = Firebase.database // realtime database

    private val _artist = MutableStateFlow<List<Artist>>(emptyList())
    val artist: StateFlow<List<Artist>> = _artist

    private val _player = MutableStateFlow<Player?>(null)
    val player: StateFlow<Player?> = _player

    init {
        // loadData()
        getArtist()
        getPlayer()
    }

    private fun getPlayer(){
        viewModelScope.launch {
            collectPlayer().collect{
                val player = it.getValue(Player::class.java)
                _player.value = player
            }
        }
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

    private suspend fun getAllArtist():List<Artist>{
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

    private fun collectPlayer(): Flow<DataSnapshot> = callbackFlow{
        val listener = object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                trySend(snapshot).isSuccess
            }
            override fun onCancelled(error: DatabaseError) {
                Log.i("master", "Error: ${error.message}")
                close(error.toException())
            }
        }
        val ref = database.reference.child("player")
        ref.addValueEventListener(listener)
        awaitClose{ ref.removeEventListener(listener)}
    }

    fun onPlaySelected(){
        if(player.value != null) {
            val currentPlayer = _player.value?.copy(play = !player.value?.play!!)
            val ref = database.reference.child("player")
            ref.setValue(currentPlayer)
        }
    }

    fun onCancelSelected() {
        val ref = database.reference.child("player")
        ref.setValue(null)
    }

    fun addPlayer(artist: Artist) {
        val ref = database.reference.child("player")
        val player = Player(artist = artist, play = true)
        ref.setValue(player)
    }

}

