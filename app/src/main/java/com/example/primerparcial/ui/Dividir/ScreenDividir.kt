package com.example.primerparcial.ui.Dividir

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.primerparcial.data.local.entities.Dividir
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun ScreenDividir(viewModel: DividirViewModel = hiltViewModel()) {
    val divisores by viewModel.Divisores.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.isMessageShownFlow.collectLatest {
            if (it) {
                snackbarHostState.showSnackbar(
                    message = "Division efectuada con exito",
                    duration = SnackbarDuration.Short
                )
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        val keyboardController = LocalSoftwareKeyboardController.current

        Text(text = "Aprende a dividir", style = MaterialTheme.typography.titleLarge)
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = viewModel.Nombre,
            label = { Text(text = "Nombre") },
            singleLine = true,
            onValueChange = { viewModel.Nombre = it },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
        )
        if (!viewModel.NombreError) {
            Text(text = "El nombre es un campo requerido", color = Color.Red)
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row {
            OutlinedTextField(
                modifier = Modifier.weight(1f),
                value = viewModel.Dividendo.toString(),
                label = { Text(text = "Dividendo") },
                singleLine = true,
                onValueChange = {
                    val newValue = it.toIntOrNull()
                    if (newValue != null) {
                        viewModel.Dividendo = newValue
                    }
                },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
            )

            Spacer(modifier = Modifier.width(30.dp))

            OutlinedTextField(
                modifier = Modifier.weight(1f),
                value = viewModel.Divisor.toString(),
                label = { Text(text = "Divisor") },
                singleLine = true,
                onValueChange = {
                    val newValue = it.toIntOrNull()
                    if (newValue != null) {
                        viewModel.Divisor = newValue
                    }
                },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row {
            OutlinedTextField(
                modifier = Modifier.weight(1f),
                value = viewModel.Cociente.toString(),
                label = { Text(text = "Cociente") },
                singleLine = true,
                onValueChange = {
                    val newValue = it.toIntOrNull()
                    if (newValue != null) {
                        viewModel.Cociente = newValue
                    }
                },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
            )

            Spacer(modifier = Modifier.width(30.dp))

            OutlinedTextField(
                modifier = Modifier.weight(1f),
                value = viewModel.Residuo.toString(),
                label = { Text(text = "Residuo") },
                singleLine = true,
                onValueChange = {
                    val newValue = it.toDoubleOrNull()
                    if (newValue != null) {
                        viewModel.Residuo = newValue
                    }
                },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedButton(onClick = {
            keyboardController?.hide()
            if (viewModel.Validar()) {
                viewModel.save()
                viewModel.setMessageShown()
            }
        }, modifier = Modifier.fillMaxWidth())
        {
            Icon(imageVector = Icons.Default.CheckCircle, contentDescription = "Guardar")
            Text(text = "Calcular")
        }

        Consult(divisores)
    }
}

@Composable
fun Consult(divisiones: List<Dividir>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(text = "Historial de resultados", style = MaterialTheme.typography.titleMedium)

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(divisiones) { dividir ->
                ItemConsult(dividir)
            }
        }
    }
}

@Composable
fun ItemConsult(dividir: Dividir, viewModel: DividirViewModel = hiltViewModel()) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = "Nombre: ${dividir.nombre}", style = MaterialTheme.typography.titleMedium)
            Row {
                Text(
                    text = "Dividendo: ${dividir.dividendo.toString()}",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.width(30.dp))
                Text(
                    text = "Divisor: ${dividir.divisor.toString()}",
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Row {
                Text(
                    text = "Cociente: ${dividir.cociente.toString()}",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.width(30.dp))
                Text(
                    text = "Residuo: ${dividir.residuo.toString()}",
                    style = MaterialTheme.typography.titleMedium
                )
            }

            Button(
                onClick = {
                    viewModel.delete(dividir)
                }
            ) {
                Icon(imageVector = Icons.Default.Clear, contentDescription = "")
            }
        }
    }
}