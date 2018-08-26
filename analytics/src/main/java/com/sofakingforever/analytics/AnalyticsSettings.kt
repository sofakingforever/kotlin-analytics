package com.sofakingforever.analytics

/**
 * Holds some things for the Analytics class
 * @property isAnalyticsEnabled - no events will be sent if this is set to *false*
 * @property exceptionHandler - implementation of @ExceptionHandler
 */
class AnalyticsSettings {

    @Volatile
    var isAnalyticsEnabled = true

    var checkForUpdates = true

    val enabledKits: ServiceEnabledMap<AnalyticsKit> = ServiceEnabledMap()
    val enabledDispatchers: ServiceEnabledMap<String> = ServiceEnabledMap()


    class ServiceEnabledMap<Key> : LinkedHashMap<Key, Boolean>() {

        fun isDisabled(key: Key): Boolean = this[key] == false

    }
}

