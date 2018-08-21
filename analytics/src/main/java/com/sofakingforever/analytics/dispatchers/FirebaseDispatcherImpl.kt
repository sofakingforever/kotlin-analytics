package com.sofaking.moonworshipper.analytics.dispatchers

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import com.sofakingforever.analytics.AnalyticsDispatcher
import com.sofakingforever.analytics.events.AnalyticsContentView
import com.sofakingforever.analytics.events.AnalyticsEvent
import com.sofakingforever.analytics.events.AnalyticsInviteEvent
import com.sofakingforever.analytics.kits.FirebaseKit

class FirebaseDispatcherImpl(override val init: Boolean) : AnalyticsDispatcher {

    constructor() : this(true)

    override val kit = FirebaseKit.instance

    var firebaseAnalytics: FirebaseAnalytics? = null


    override fun initDispatcher(context: Context) {
        firebaseAnalytics = FirebaseAnalytics.getInstance(context)
    }


    override fun trackCustomEvent(event: AnalyticsEvent) {
        firebaseAnalytics?.logEvent(event.getEventName(kit).firebaseFriendly(), event.getBundle())
    }

    override fun trackContentView(contentView: AnalyticsContentView) {
        firebaseAnalytics?.logEvent("contentView_" + contentView.getViewName().firebaseFriendly(), Bundle.EMPTY)
    }

    override fun trackInviteEvent(inviteEvent: AnalyticsInviteEvent) {
        firebaseAnalytics?.logEvent(FirebaseAnalytics.Event.SHARE, inviteEvent.getBundle())
    }

    private fun String.firebaseFriendly(): String {

        val firebased = toLowerCase().replace(" ", "_")

        if (firebased.length > 40) {
            throw IllegalStateException("firebase event title shouldn't have more than 40 chars ($firebased)")
        }

        return firebased

    }


    private fun AnalyticsInviteEvent.getBundle(): Bundle {
        val bundle = Bundle()

        bundle.putString("packageName", packageName)
        bundle.putString("appName", getInviteMethod())

        return bundle
    }

    private fun AnalyticsEvent.getBundle(): Bundle {
        val bundle = Bundle()

        getParameters(kit).forEach {
            when {
            // numbers
                it.value is Int -> bundle.putInt(it.key, it.value as Int)
                it.value is Float -> bundle.putFloat(it.key, it.value as Float)
                it.value is Double -> bundle.putDouble(it.key, it.value as Double)
            // other stuff
                it.value is String -> bundle.putString(it.key, it.value as String)
                it.value is Boolean -> bundle.putBoolean(it.key, it.value as Boolean)

                else -> throw RuntimeException("value type " + it.value.javaClass.toString() + " is illegal")
            }
        }

        return bundle
    }


}
