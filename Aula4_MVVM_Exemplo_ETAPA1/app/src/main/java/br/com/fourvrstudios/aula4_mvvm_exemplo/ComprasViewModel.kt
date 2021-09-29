package br.com.fourvrstudios.aula4_mvvm_exemplo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ComprasViewModel : ViewModel() {

    override fun onCleared() {
        super.onCleared()
    }

    private val _moedas = MutableLiveData<Int>()
    val moedas: LiveData<Int> // Método de encapsulamento para evitar alteração de MutableLiveData
        get() = _moedas

    private val _pontos = MutableLiveData<Int>()
    val pontos: LiveData<Int>
        get() = _pontos

    private val _valorCompra = MutableLiveData<Double>()
    val valorCompra: LiveData<Double>
        get() = _valorCompra

    fun addMoedas() {
        this._moedas.value = (this._moedas.value)?.plus(1)
        calcularValorCompra()
    }

    fun removerMoedas() {
        this._moedas.apply {
            if (value!! <= 0) {
                value = 0
            } else {
                value = value?.minus(1)
            }
        }
        calcularValorCompra()
    }

    fun addPontos() {
        this._pontos.value = (this._pontos.value)?.plus(1)
        calcularValorCompra()
    }

    fun removerPontos() {
        this._pontos.apply {
            if (value!! <= 0) {
                value = 0
            } else {
                value = (value)?.minus(1)
            }
        }
        calcularValorCompra()
    }

    fun calcularValorCompra() {
        this._valorCompra.value = ((this._pontos.value)?.times(2)?.plus((this._moedas.value!!)))?.toDouble()
    }

    fun resetCompra() {
        this._moedas.value = 0
        this._pontos.value = 0
        this._valorCompra.value = 00.0
    }

    fun getCodigoCompra(): String {
        var length = 10 // Informar quantos caracteres haverão
        val charset = "ABCDEFGHIJKLMNOPQRSTUVWXTZabcdefghijklmnopqrstuvwxyz0123456789"
        return (1..length).map { charset.random() }.joinToString ( "" )
    }

    init {
        resetCompra()
    }
}