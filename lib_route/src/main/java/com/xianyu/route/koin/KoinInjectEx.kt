package com.xianyu.route.koin

import android.content.ComponentCallbacks
import org.koin.android.ext.android.get
import org.koin.core.error.NoBeanDefFoundException
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier

inline fun <reified T : Any> ComponentCallbacks.injectOrNull(
    qualifier: Qualifier? = null,
    mode: LazyThreadSafetyMode = LazyThreadSafetyMode.SYNCHRONIZED,
    noinline parameters: ParametersDefinition? = null,
) = lazy(mode) {
    try {
        get<T>(qualifier, parameters)
    } catch (exception: NoBeanDefFoundException) {
        null
    }
}