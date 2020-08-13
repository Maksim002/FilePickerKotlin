package com.timelysoft.tsjdomcom.service



import kotlinx.coroutines.Dispatchers
import okhttp3.MultipartBody
import androidx.lifecycle.liveData

class NetworkRepository {

    fun saveFile(file: List<MultipartBody.Part>) = liveData(Dispatchers.IO) {
        try {
            val response =
                RetrofitService.apiServiceNew().saveFile(file)
            when {
                response.isSuccessful -> {
                    emit(ResultStatus.success(null, "Ваше сообщение отправлено!"))
                }
                else -> {
                    emit(ResultStatus.error("Не известная ошибка"))
                }
            }
        } catch (e: Exception) {
            emit(ResultStatus.netwrok("Проблеммы с подключение интернета", null))
        }
    }
}