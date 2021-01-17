package com.example.translator.model.dataSource.remote

import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(
    user: String,
    password: String,
    private val credentials: String = Credentials.basic(user, password)
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val authenticatedRequest = request
            .newBuilder()
            .header("Authorization", credentials)
            .build()

        return chain.proceed(authenticatedRequest)
    }
}