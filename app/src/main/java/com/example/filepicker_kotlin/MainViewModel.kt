package com.example.filepicker_kotlin

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.timelysoft.tsjdomcom.service.NetworkRepository
import com.timelysoft.tsjdomcom.service.ResultStatus
import okhttp3.MultipartBody

class MainViewModel : ViewModel(){
    private val repository = NetworkRepository()

    fun saveFile(file: List<MultipartBody.Part>): LiveData<ResultStatus<Nothing>> {
        return repository.saveFile(file)
    }
}