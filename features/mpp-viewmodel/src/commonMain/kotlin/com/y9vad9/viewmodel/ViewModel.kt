package com.y9vad9.viewmodel

import kotlinx.coroutines.CoroutineScope

expect abstract class ViewModel {
    val scope: CoroutineScope
}