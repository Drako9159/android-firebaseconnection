package com.niojar.firebaseconnecion

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.niojar.firebaseconnecion.presentation.initial.InitialScreen
import com.niojar.firebaseconnecion.presentation.login.LoginScreen
import com.niojar.firebaseconnecion.presentation.signup.SignUpScreen

@Composable
fun NavigationWrapper(navHostController: NavHostController) {
    NavHost(navController = navHostController, startDestination = "initial") {
        composable("initial") {
            InitialScreen()
        }
        composable("logIn") {
            LoginScreen()
        }
        composable("signUp") {
            SignUpScreen()
        }
    }
}