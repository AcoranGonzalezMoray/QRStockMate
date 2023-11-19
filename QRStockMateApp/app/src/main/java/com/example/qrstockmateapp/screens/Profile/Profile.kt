package com.example.qrstockmateapp.screens.Profile

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.qrstockmateapp.R
import com.example.qrstockmateapp.navigation.repository.DataRepository

@ExperimentalAnimationApi
@Composable
fun ProfileScreen(navController: NavController) {

    var user = DataRepository.getUser()


    val imageUrl = user?.url
    val userId = user?.id
    val userName = user?.name
    val userCode = user?.code
    val userEmail = user?.email
    val userPhone = user?.phone
    val userRole = user?.role

    val placeholderImage = painterResource(id = R.drawable.user)

    fun userRoleToString(roleId: Int): String {
        return when (roleId) {
            0 -> "Director"
            1 -> "Administrator"
            2 -> "Inventory Technician"
            3 -> "User"
            else -> "Unknown Role"
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        Column(     //Imagen de Perfil de Usuario & Nombre
            modifier = Modifier
                .fillMaxHeight(0.4f)
                .wrapContentSize(Alignment.Center)
        ) {

            if (imageUrl.isNullOrBlank()) {
                // Si la URL es nula o vacía, mostrar la imagen por defecto
                Image(
                    painter = placeholderImage,
                    contentDescription = "Default User Image",
                    modifier = Modifier
                        .width(100.dp)
                        .height(100.dp),
                    contentScale = ContentScale.Crop
                )
            } else {
                // Si hay una URL válida, cargar la imagen usando Coil
                val painter = rememberImagePainter(
                    data = imageUrl,
                    builder = {
                        crossfade(true)
                        placeholder(R.drawable.user)
                    }
                )
                Image(
                    painter = painter,
                    contentDescription = "User Image",
                    modifier = Modifier
                        .width(100.dp)
                        .height(100.dp),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(5.dp))

            if (userName != null) {
                Text(
                    text = userName,
                    fontSize = 15.sp,
                    style = TextStyle(fontWeight = FontWeight.Bold),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(15.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(30.dp))   //ESpacio entre Imagen & Nombre y Info de Usuario

        Column(     //Info de el Usuario(Role, Email, Telefono, Codigo, Id)
            modifier = Modifier
                .fillMaxSize(0.6f)
        ) {
            if (userRole != null) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("Role: ")
                        }
                        append(userRoleToString(userRole))
                    },
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(start = 10.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            if (userEmail != null) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("Email: ")
                        }
                        append(userEmail)
                    },
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(start = 10.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            if (userPhone != null) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("Phone Number: ")
                        }
                        append(userPhone)
                    },
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(start = 10.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            if (userCode != null) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("Warehouse Code: ")
                        }
                        append(userCode)
                    },
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(start = 10.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            if (userId != null) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("User Id: ")
                        }
                        append(userId.toString())
                    },
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(start = 10.dp)
                )
            }
        }
    }



}
