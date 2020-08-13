package com.timelysoft.tsjdomcom.service



import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*


interface ApiService {

    @Multipart
    @PUT("Invoices/Template")
    suspend fun saveFile(
        @Part file: List<MultipartBody.Part>
    ): Response<Unit>
}

