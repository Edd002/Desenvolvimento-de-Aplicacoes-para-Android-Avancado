package com.example.app.trabalhofinal_worldwidepublicholiday

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app.trabalhofinal_worldwidepublicholiday.Network.Holiday
import com.example.app.trabalhofinal_worldwidepublicholiday.Network.RetrofitInstance
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

enum class RestApiStatus { START, LOADING, ERROR, DONE }

class RetrofitViewModel : ViewModel() {

    private val _status = MutableLiveData<RestApiStatus>();
    val status : LiveData<RestApiStatus>
        get() = _status;

    private val _response = MutableLiveData<List<Holiday>?>();
    val response: LiveData<List<Holiday>?>
        get() = _response;

    fun getByYearAndCountryCode(year: String, countryCode: String) : Job = viewModelScope.launch {
        try {
            _status.value = RestApiStatus.LOADING;
            _response.value = RetrofitInstance.retrofit.getByYearAndCountryCode(year, countryCode).body();
            _status.value = RestApiStatus.DONE;
            _status.value = RestApiStatus.START;
        } catch (e: Exception) {
            _status.value = RestApiStatus.ERROR;
            _status.value = RestApiStatus.START;
            _response.value = null;
        }
    }
}