package org.timemates.app.feature.common

import com.arkivanov.decompose.ComponentContext
import pro.respawn.flowmvi.api.Container
import pro.respawn.flowmvi.api.MVIAction
import pro.respawn.flowmvi.api.MVIIntent
import pro.respawn.flowmvi.api.MVIState

interface MVI<S : MVIState, I : MVIIntent, A : MVIAction> : Container<S, I, A>, ComponentContext