package com.happinessinc.getshwifty.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.happinessinc.getshwifty.repo.LaunchRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel  @Inject constructor(
    private val repository: LaunchRepo
) : ViewModel() {
    var data = repository.getLaunches()

    fun forceFetchNetworkData(){
        viewModelScope.launch {
            repository.clearLocalRepo()
            data=repository.getLaunches()
        }
    }
}
