package com.tanyayuferova.lifestylenews.data.network.exeption

import java.lang.Exception

/**
 * Author: Tanya Yuferova
 * Date: 8/7/19
 */
class BadRequestException : Exception("The request was unacceptable, often due to a missing or misconfigured parameter.")