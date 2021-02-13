package com.happinessinc.getshwifty.utils

import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.happinessinc.getshwifty.R
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

//string to date and format

fun String.toDate(
    dateFormat: String = "yyyy-MM-dd'T'HH:mm:ss", timeZone: TimeZone = TimeZone.getTimeZone(
        "UTC"
    )
): Date {
    val parser = SimpleDateFormat(dateFormat, Locale.getDefault())
    parser.timeZone = timeZone
    return parser.parse(this)
}


fun Date.formatShort(timeZone: TimeZone = TimeZone.getDefault()): String {
    val dateFormat = "dd-MMM-yyyy HH:mm"
    val formatter = SimpleDateFormat(dateFormat, Locale.getDefault())
    formatter.timeZone = timeZone
    return formatter.format(this)
}


@BindingAdapter("date_string")
fun setDate(tv: TextView, date: String?) {
    try{
        val d=date!!.toDate()
        tv.text = d.formatShort()
    }catch (ex:Exception){
        ex.printStackTrace()
        tv.text = "NA"
    }
}
