package com.niojar.firebaseconnecion.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.firestore.FirebaseFirestore
import com.niojar.firebaseconnecion.presentation.model.Artist
import com.niojar.firebaseconnecion.ui.theme.Black

@Composable
fun HomeScreen(db:FirebaseFirestore){

    Column(Modifier.fillMaxWidth().background(Black)) {
        Text("Popular artist", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 30.sp, modifier = Modifier.padding(16.dp))
    }

    val artists = emptyList<Artist>()

    LazyRow {
        items(artists){

        }
    }
}



@Composable
fun ArtistItem(artist: Artist){
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(Modifier.size(60.dp).background(Color.Red))
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = artist.name)
    }
}

@Preview
@Composable
fun ArtistItemPreview(){
    val artist = Artist("Daddy Yankke", "Reggaeton artist", "", emptyList())
    ArtistItem(artist = artist)
}

/*
fun createArtist(db:FirebaseFirestore){
    val random = (1 .. 1000).random()
    val artist = Artist(name = "Random $random", numberOfSongs = random)
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
}*/