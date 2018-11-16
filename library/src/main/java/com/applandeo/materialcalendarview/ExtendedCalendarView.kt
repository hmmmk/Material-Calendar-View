package com.izibizy.common.views

import android.content.Context
import android.util.AttributeSet
import com.applandeo.materialcalendarview.CalendarView
import com.applandeo.materialcalendarview.utils.CalendarProperties
import com.izibizy.R
import java.util.*
import kotlin.collections.ArrayList

class ExtendedCalendarView : CalendarView {

    inner class Range(
            val startDate: Calendar,
            val endDate: Calendar
    ) {
        override fun toString(): String {
            return "Start: ${startDate.get(Calendar.DAY_OF_MONTH)}, ${startDate.get(Calendar.MONTH)}" +
                    "End: ${endDate.get(Calendar.DAY_OF_MONTH)}, ${endDate.get(Calendar.MONTH)}"
        }
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr)

    constructor(context: Context, calendarProperties: CalendarProperties) :
            super(context, calendarProperties)

    /**
     * @return an ArrayList of days enclosed in ranges. If date is not in range, creates a range
     * with the same start and end dates(Calendar objects).
     */
    fun getDaysAsRanges(): List<Range> {
        val allDays = ArrayList(selectedDates)
        val result = ArrayList<Range>()

        if (allDays.size > 1) {
            while (allDays.size >= 2) {
                val lStartDate = allDays[0]
                var lEndDate: Calendar = allDays[0]

                while (allDays.size >= 2) {
                    val currDate = Calendar.getInstance().also { it.time = allDays[1].time }
                    currDate.add(Calendar.DAY_OF_YEAR, -1)

                    if (lEndDate.compareTo(currDate) == 0) {
                        lEndDate = allDays[1]

                        allDays.removeAt(1)

                        if (allDays.size == 1) {
                            allDays.removeAt(0)
                        }
                    } else {
                        allDays.removeAt(0)
                        break
                    }
                }

                result.add(Range(lStartDate, lEndDate))
            }

            if (allDays.isNotEmpty()) {
                result.add(Range(allDays[0], allDays[0]))
            }
        } else {
            if (allDays.isNotEmpty()) {
                result.add(Range(allDays[0], allDays[0]))
            }
        }

        return result
    }
}
