package com.tanyayuferova.lifestylenews.data.network.exeption

import java.lang.Exception

/**
 * Author: Tanya Yuferova
 * Date: 8/7/19
 */
class TooManyRequestsException : Exception("You made too many requests within a window of time and have been rate " +
        "limited. Back off for a while.")