package br.com.fourvrstudios.aula6_retrofitdemo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fourvrstudios.aula6_retrofitdemo.Network.Photo
import br.com.fourvrstudios.aula6_retrofitdemo.Network.RetrofitInstance
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

enum class RestApiStatus { LOADING, ERROR, DONE }

class RetrofitViewModel : ViewModel() {

    private val _status = MutableLiveData<RestApiStatus>()
    val status : LiveData<RestApiStatus>
        get() = _status

    private val _response = MutableLiveData<List<Photo>?>()
    val response: LiveData<List<Photo>?>
        get() = _response

    private val _reqResponse = MutableLiveData<Photo?>()
    val reqResponse: LiveData<Photo?>
        get() = _reqResponse

    init {
        //getRestApiResponse()
        //getAllPhotos()
        getPhotosByAlbum()
        //postPhoto()
        //replacePhoto()
        //updatePhoto()
        //deletePhoto()
        //getRestApiResponse()
        //postField()
        //getById()
    }

    private fun deletePhoto() : Job = viewModelScope.launch {
        try {
            _status.value = RestApiStatus.LOADING
            RetrofitInstance.retrofit.deletePhoto(5)
            Log.i("Deletou", "Deletado")
            _status.value = RestApiStatus.DONE
        } catch (e: Exception) {
            _status .value = RestApiStatus.ERROR
            Log.i("MYTAG", "Generic Exeption")
            _response.value = null
        }
    }

    private fun updatePhoto() : Job = viewModelScope.launch {
        try {
            _status.value = RestApiStatus.LOADING
            val foto = Photo(2, 0, null, "Meu Título", null)
            _reqResponse.value = RetrofitInstance.retrofit.patchPhoto(10, foto).body()
            _status.value = RestApiStatus.DONE
        } catch (e: Exception) {
            _status .value = RestApiStatus.ERROR
            Log.i("MYTAG", "Generic Exeption")
            _response.value = null
        }
    }

    private fun replacePhoto() : Job = viewModelScope.launch {
        try {
            _status.value = RestApiStatus.LOADING
            val foto = Photo(2, 0, "https://www.123456.com.br/img.png", "Meu Título", "https://www.123456.com.br/img2s.png")
            _reqResponse.value = RetrofitInstance.retrofit.overwritePhoto(10, foto).body()
            _status.value = RestApiStatus.DONE
        } catch (e: Exception) {
            _status .value = RestApiStatus.ERROR
            Log.i("MYTAG", "Generic Exeption")
            _response.value = null
        }
    }

    private fun postField() : Job = viewModelScope.launch {
        try {
            _status.value = RestApiStatus.LOADING
            _reqResponse.value = RetrofitInstance.retrofit.postPhotoField(2, 0, "https://www.123456.com.br/img.png", "Meu Título", "https://www.123456.com.br/img2s.png").body()
            _status.value = RestApiStatus.DONE
        } catch (e: Exception) {
            _status .value = RestApiStatus.ERROR
            Log.i("MYTAG", "Generic Exeption")
            _response.value = null
        }
    }

    private fun postPhoto() : Job = viewModelScope.launch {
        try {
            _status.value = RestApiStatus.LOADING
            val foto = Photo(2, 0, "https://www.123456.com.br/img.png", "Meu Título", "https://www.123456.com.br/img2s.png")
            _reqResponse.value = RetrofitInstance.retrofit.salvarFoto(foto).body()
            _status.value = RestApiStatus.DONE
        } catch (e: Exception) {
            _status .value = RestApiStatus.ERROR
            Log.i("MYTAG", "Generic Exeption")
            _response.value = null
        }
    }

    private fun getById() : Job = viewModelScope.launch {
        try {
            _status.value = RestApiStatus.LOADING
            _reqResponse.value = RetrofitInstance.retrofit.getByIdPath(1).body()
            _status.value = RestApiStatus.DONE
        } catch (e: Exception) {
            _status .value = RestApiStatus.ERROR
            Log.i("MYTAG", "Generic Exeption")
            _response.value = null
        }
    }

    private fun getPhotosByAlbum() : Job = viewModelScope.launch {
        try {
            _status.value = RestApiStatus.LOADING
            _response.value = RetrofitInstance.retrofit.getPhotosByAlbumId(7).body()
            _status.value = RestApiStatus.DONE
        } catch (e: Exception) {
            _status .value = RestApiStatus.ERROR
            Log.i("MYTAG", "Generic Exeption")
            _response.value = null
        }
    }

    // Thread
    private fun getAllPhotos() : Job = viewModelScope.launch {
        try {
            _status .value = RestApiStatus.LOADING
            _response.value = RetrofitInstance.retrofit.getAll().body()
            _status .value = RestApiStatus.DONE
        } catch (e: IOException) {
            _status .value = RestApiStatus.ERROR
            Log.i("MYTAG", "IOExeption - Sem internet, URL incorreta, etc.")
            _response.value = null
        } catch (e: HttpException) {
            _status .value = RestApiStatus.ERROR
            Log.i("MYTAG", "HttpException - Status Codes não iniciados com 2xx")
            _response.value = null
        } catch (e: Exception) {
            _status .value = RestApiStatus.ERROR
            Log.i("MYTAG", "Generic Exeption")
            _response.value = null
        }
    }

    private fun getRestApiResponse() {
        RetrofitInstance.retrofit.getAllCall().enqueue(object : Callback<List<Photo>>{
            override fun onResponse(call: Call<List<Photo>>, response: Response<List<Photo>>) {
                if (response.isSuccessful) {
                    _response.value = response.body() // JSon advindo o corpo (body) da resposta

                    Log.i(
                        "RETROFIT_CALL",
                        "Código de resposta: ${response.code()} - Registros retornados: ${response.body()?.size}"
                    )
                }
            }

            override fun onFailure(call: Call<List<Photo>>, t: Throwable) {
                Log.i("RETROFIT_CALL",  "Falha: ${t.message}")
            }
        })
    }

    override fun onCleared() {
        super.onCleared()
        //getData().cancel() - As corrotinas em viewModelScope já são canceladas automaticamente.
    }
}