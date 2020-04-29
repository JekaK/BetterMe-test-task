package com.krikun.data.util

import java.text.SimpleDateFormat
import java.util.*

var serverFormat: SimpleDateFormat = SimpleDateFormat("YYYY-MM-dd")

fun Date.getCurrentTimeInServerFormat(): String = serverFormat.format(this.time)

fun Date.getTwoWeeksTimeInServerFormat(): String {
    val cal: Calendar = GregorianCalendar()
    cal.add(Calendar.DAY_OF_MONTH, -14)
    val twoWeeksAgo = cal.time
    return serverFormat.format(twoWeeksAgo.time)
}

