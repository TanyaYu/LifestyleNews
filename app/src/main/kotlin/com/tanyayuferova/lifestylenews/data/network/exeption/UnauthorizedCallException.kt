package com.tanyayuferova.lifestylenews.data.network.exeption

/**
 * Author: Tanya Yuferova
 * Date: 8/7/19
 */
class UnauthorizedCallException : Exception("Your API key was missing from the request, or wasn't correct.")