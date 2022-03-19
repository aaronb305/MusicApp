package com.example.itunesapp.restapi

import okhttp3.Interceptor
import okhttp3.Response

/**
 * intercepts request from [ItunesApi] and shows in logcat
 */
class RequestInterceptor : Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        return chain.proceed(request)
    }
}