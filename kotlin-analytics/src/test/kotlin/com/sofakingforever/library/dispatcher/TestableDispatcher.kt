package com.sofakingforever.library.dispatcher

import com.sofakingforever.analytics.AnalyticsDispatcher
import com.sofakingforever.analytics.AnalyticsKit
import com.sofakingforever.analytics.events.ContentViewEvent
import com.sofakingforever.analytics.events.CustomEvent
import com.sofakingforever.analytics.events.SetUserProperties
import com.sofakingforever.analytics.events.base.Event
import com.sofakingforever.library.events.InitDispatcherEvent

class TestableDispatcher : AnalyticsDispatcher {


    override val init: Boolean = true

    override val kit: AnalyticsKit = TestKit.instance

    override val dispatcherName: String = DispatcherName

    val eventList: MutableList<Event> = mutableListOf()
    override fun initDispatcher() {
        track(InitDispatcherEvent())
    }

    override fun trackContentView(contentView: ContentViewEvent) {
        eventList.add(contentView)
    }

    override fun trackCustomEvent(event: CustomEvent) {
        eventList.add(event)
    }

    override fun setUserProperties(properties: SetUserProperties) {
        eventList.add(properties)
    }

    override fun track(event: Event) {
        if (event is InitDispatcherEvent) {
            eventList.add(event)
        } else {
            super.track(event)
        }
    }

    companion object {
        const val DispatcherName = "TestDispatcher"
    }

}