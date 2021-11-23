package com.footballmatch.sportselect.data.web.ball_interception

import com.footballmatch.sportselect.data.web.exception.NoInternetException
import com.footballmatch.sportselect.web.service.InternetConnectionService
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

class BallInterceptor @Inject constructor(
    private val connectionService: InternetConnectionService
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        try {
            if (!connectionService.isConnected()) {
                throw NoInternetException(RuntimeException())
            }
            return chain.proceed(chain.request())
        } catch (e: IOException) {
            if (!connectionService.isConnected()) {
                throw NoInternetException(e)
            }
            throw e
        }
    }

}