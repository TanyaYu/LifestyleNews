package com.tanyayuferova.lifestylenews.domain.baseviewmodel

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController

/**
 * Author: Tanya Yuferova
 * Date: 7/28/2019
 */
open class NavigationViewModel : ViewModel() {

    var navController: NavController? = null

    open fun onReady() {

    }
}