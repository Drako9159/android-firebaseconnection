package com.niojar.firebaseconnecion.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.niojar.firebaseconnecion.presentation.model.Artist
import com.niojar.firebaseconnecion.ui.theme.Black
import com.niojar.firebaseconnecion.ui.theme.Purple40

@Composable
fun HomeScreen(viewModel: HomeViewModel = HomeViewModel()){

    val artists: State<List<Artist>> = viewModel.artist.collectAsState()
    val player by viewModel.player.collectAsState()

    Column(Modifier.fillMaxSize().background(Black)) {
        Text("Popular artist", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 30.sp, modifier = Modifier.padding(16.dp))
        LazyRow {
            items(artists.value){
                ArtistItem(it)
            }
        }

        Spacer(modifier = Modifier.weight(1f))
        if(player!=null){
            val color = if(player?.play == true) Color.Green else Color.Red
            Row (Modifier.height(50.dp).fillMaxSize().background(Purple40)){
                Text(text = player?.artist?.name.orEmpty())
                Box(Modifier.size(20.dp).background(color))
            }
        }
    }
}



@Composable
fun ArtistItem(artist: Artist){
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        //Box(Modifier.size(60.dp).background(Color.Red))
        AsyncImage(modifier = Modifier.size(60.dp).clip(CircleShape), model = artist.image, contentDescription = "Artist image")
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = artist.name.orEmpty(), color = Color.White)
    }
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