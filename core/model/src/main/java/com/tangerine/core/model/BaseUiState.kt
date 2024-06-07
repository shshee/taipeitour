package com.tangerine.core.model

/**
 * Remember to turn loading on/off manually
 */
sealed interface BaseUiState {
    companion object {
        inline fun <reified T> isSameType(input: Any) = when (T::class) {
            input::class -> true
            else -> false
        }

//        inline fun <reified T> retrieveData(input: BaseUiState): T? {
//            when (input) {
//                is Success<*> -> {
//                    if (isSameType<T>(input)) return input.response as T
//                }
//
//                else -> return input.retrieveCache<T>()
//            }
//
//            return null
//        }
    }

    fun <T> retrieveCache(): T? {
        //TODO Retrieve last cache in case failure happened
        return null
    }

    fun <T> saveCache(data: T) {
        //TODO save api response cache to data store
    }
}