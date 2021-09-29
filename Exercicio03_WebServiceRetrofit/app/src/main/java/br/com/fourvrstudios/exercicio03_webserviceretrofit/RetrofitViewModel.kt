package br.com.fourvrstudios.exercicio03_webserviceretrofit

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fourvrstudios.exercicio03_webserviceretrofit.Network.Endereco
import br.com.fourvrstudios.exercicio03_webserviceretrofit.Network.RetrofitInstance
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

enum class RestApiStatus { LOADING, ERROR, DONE }

class RetrofitViewModel : ViewModel() {

    private val _status = MutableLiveData<RestApiStatus>()
    val status : LiveData<RestApiStatus>
        get() = _status

    private val _response = MutableLiveData<Endereco?>()
    val response: LiveData<Endereco?>
        get() = _response

    fun getByCEP(CEP: String) : Job = viewModelScope.launch {
        try {
            _status.value = RestApiStatus.LOADING
            _response.value = RetrofitInstance.retrofit.getByCepPath(CEP).body()
            _status.value = RestApiStatus.DONE
        } catch (e: Exception) {
            _status.value = RestApiStatus.ERROR
            Log.i("MYTAG", "Generic Exeption")
            _response.value = null
        }
    }
}