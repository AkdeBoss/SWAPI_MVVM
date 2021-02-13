package com.happinessinc.getshwifty.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.happinessinc.getshwifty.repo.LaunchRepo
import com.happinessinc.getshwifty.repo.api.ApiResponse
import com.happinessinc.getshwifty.repo.models.LaunchEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.sql.Ref
import javax.inject.Inject

@HiltViewModel
class MainViewModel  @Inject constructor(
    private val repository: LaunchRepo
) : ViewModel() {

    val isOnline: MutableLiveData<Boolean> = MutableLiveData(false)
      val mutableData:MutableLiveData<ApiResponse<List<LaunchEvent>>> by lazy {
        repository.getLaunches() as MutableLiveData
    }


    private var mutableEventData:MutableLiveData<ApiResponse<LaunchEvent>> = MutableLiveData()

    val eventData:LiveData<ApiResponse<LaunchEvent>> get() = mutableEventData





    fun forceFetchNetworkData(){
        viewModelScope.launch {
           mutableData.postValue(repository.getEvents())
        }
    }

    fun getSingleEventData(id:String){
        viewModelScope.launch {
            mutableEventData.postValue(repository.getEvent(id))
        }
    }
}

