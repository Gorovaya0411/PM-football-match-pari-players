package com.footballmatch.sportselect.data.web.exception

import java.io.IOException

class NoInternetException(cause: Throwable) : IOException(cause)

class ClientException(val errorTitle: String) : Exception()