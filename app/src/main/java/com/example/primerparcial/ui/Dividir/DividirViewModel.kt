package com.example.primerparcial.ui.Dividir

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.primerparcial.data.local.entities.Dividir
import com.example.primerparcial.data.repository.DividirRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DividirViewModel @Inject constructor(
    private val repository: DividirRepository
) : ViewModel() {
    var Nombre by mutableStateOf("")
    var Dividendo by mutableStateOf(0)
    var Divisor by mutableStateOf(0)
    var Cociente by mutableStateOf(0)
    var Residuo by mutableStateOf(0.0)

    var Divisores : StateFlow<List<Dividir>> = repository.getAll().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList()
    )

    fun Validar() : Boolean{
        return !(Nombre == "" || Dividendo == 0 || Divisor == 0 || Cociente == 0 || Residuo == 0.0)
    }

    fun save(){
        viewModelScope.launch {
            val Dividir = Dividir(
                nombre = Nombre,
                dividendo = Dividendo,
                divisor = Divisor,
                cociente = Cociente,
                residuo = Residuo
            )
            repository.save(Dividir)
            limpiar()
        }
    }

    fun limpiar(){
        Nombre = ""
        Dividendo = 0
        Divisor = 0
        Cociente = 0
        Residuo = 0.0
    }


}