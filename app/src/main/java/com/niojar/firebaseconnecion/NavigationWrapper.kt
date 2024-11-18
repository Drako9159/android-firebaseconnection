package com.niojar.firebaseconnecion

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.firebase.auth.FirebaseAuth
import com.niojar.firebaseconnecion.presentation.initial.InitialScreen
import com.niojar.firebaseconnecion.presentation.login.LoginScreen
import com.niojar.firebaseconnecion.presentation.signup.SignUpScreen

@Composable
fun NavigationWrapper(navHostController: NavHostController, auth: FirebaseAuth) {
    NavHost(navController = navHostController, startDestination = "initial") {
        composable("initial") {
            InitialScreen(
                navigateToLogin = {navHostController.navigate("login")},
                navigateToSignUp = {navHostController.navigate("signUp")}
            )
        }
        composable("logIn") {
            LoginScreen(auth)
        }
        composable("signUp") {
            SignUpScreen(auth)
        }
    }
}