package com.example.primerparcial.ui.Dividir

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.primerparcial.data.local.entities.Dividir
import com.example.primerparcial.data.repository.DividirRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DividirViewModel @Inject constructor(
    private val repository: DividirRepository
) : ViewModel() {
    var nombre by mutableStateOf("")
    var dividendo by mutableStateOf(0)
    var divisor by mutableStateOf(0)
    var cociente by mutableStateOf(0)
    var residuo by mutableStateOf(0)

    var nombreError by mutableStateOf(true)
    var dividendoError by mutableStateOf(true)
    var divisorError by mutableStateOf(true)
    var cocienteError by mutableStateOf(true)
    var residuoError by mutableStateOf(true)
    var divisionEfectuada by mutableStateOf(true)

    var invalidDividendo by mutableStateOf("")
    var invalidDivisor by mutableStateOf("")
    var invalidCociente by mutableStateOf("")
    var invalidResiduo by mutableStateOf("")


    var divisores : StateFlow<List<Dividir>> = repository.getAll().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList()
    )

    private val _isMessageShown = MutableSharedFlow<Boolean>()
    val isMessageShownFlow = _isMessageShown.asSharedFlow()

    fun setMessageShown() {
        viewModelScope.launch {
            _isMessageShown.emit(true)
        }
    }

    fun Validar() : Boolean{
        var verficarDividendo:Int?=null

        //Verificamos si estan vacios algunos de los campos.
        nombreError = nombre.isNotEmpty()
        dividendoError = dividendo > 0
        divisorError = divisor > 0
        cocienteError = cociente > 0
        residuoError = residuo > -1
        if(divisor <= 0){
            divisorError = false
            return false
        }else{
            divisorError = true
        }


        //Antes de empezar a calcular, verificamos que el divisor NO sea mayor que el dividendo.
        if(divisor > dividendo){
            invalidDivisor = "El divisor es mayor que el dividendo"
            return false
        }

        //Cumplidos ya las validaciones anteriores, verificamos cual deberia ser el resultado de la division.
        verficarDividendo = cociente * divisor
        verficarDividendo += residuo
        divisionEfectuada = verficarDividendo == dividendo

        //Lo igualamos al dividendo, si son iguales Division efectuada sera true y seguira al return de la linea 102.
        if(verficarDividendo == dividendo){
            divisionEfectuada = true
        }else{
            //Si la condicion no se cumple, calculamos el cociente y el residuo para evaluar cuales son los datos erroneos en la division
            var resultado:Int

            resultado = dividendo / divisor

            if(resultado != cociente)
                invalidCociente = "El cociente tiene un valor incorrecto"

            resultado = dividendo % divisor

            if(resultado != residuo)
                invalidResiduo = "El residuo tiene un valor incorrecto"
        }

        return !(nombre == "" || dividendo <= 0 || divisor <= 0 || cociente <= 0 || residuo < 0 || !divisionEfectuada)


    }

    fun save(){
        viewModelScope.launch {
            val Dividir = Dividir(
                nombre = nombre,
                dividendo = dividendo,
                divisor = divisor,
                cociente = cociente,
                residuo = residuo
            )
            repository.save(Dividir)
            limpiar()
        }
    }

    fun delete(dividir: Dividir){
        viewModelScope.launch {
            repository.delete(dividir)
        }
    }

    fun limpiar(){
        nombre = ""
        dividendo = 0
        divisor = 0
        cociente = 0
        residuo = 0
        invalidDividendo = ""
        invalidDivisor = ""
        invalidCociente = ""
        invalidResiduo = ""
    }


}