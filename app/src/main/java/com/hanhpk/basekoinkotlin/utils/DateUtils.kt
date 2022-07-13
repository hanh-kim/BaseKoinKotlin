package com.hanhpk.basekoinkotlin.utils

import com.hanhpk.basekoinkotlin.extensions.convertToDate
import com.hanhpk.basekoinkotlin.extensions.convertToString
import com.hanhpk.basekoinkotlin.extensions.getDayOfWeekInJp

object DateUtils {
    const val FORMAT_YEAR_MONTH_DAY_HYPHEN = "yyyy-MM-dd"
    const val FORMAT_YEAR_MONTH_DAY_CODE = "yyyyMMdd"
    const val FORMAT_YEAR_MONTH_DAY_HYPHEN_HOUR_MINUTE_SECOND = "yyyy-MM-dd HH:mm:ss"
    const val FORMAT_YEAR_MONTH_DAY_JP = "yyyy年M月d日"
    const val FORMAT_YEAR_MONTH_DAY_JP_FULL = "yyyy年MM月dd日"
    const val FORMAT_MONTH_DAY_JP_FULL = "MM月dd日"
    const val FORMAT_MONTH_DAY_JP = "M月d日(E)"
    const val FORMAT_YEAR_MONTH_HYPHEN = "yyyy-MM"
    const val FORMAT_YEAR_MONTH_DAY_DAY_OF_WEEK_JP = "yyyy年M月d日(E)"
    const val FORMAT_YEAR_MONTH_JP = "yyyy年MM月"
    const val FORMAT_YEAR_ONE_DIGIT_MONTH_JP = "yyyy年M月"
    const val FORMAT_YEAR_ONE_JP = "yyyy年"
    const val FORMAT_HOUR_MINUTE = "HH:mm"
    const val FORMAT_YEAR_MONTH_DAY = "yyyy/MM/dd"

    fun convertApiDataDateTimeToDisplayDate(data: String?): String? {
        return data?.convertToDate(FORMAT_YEAR_MONTH_DAY_HYPHEN_HOUR_MINUTE_SECOND)
            ?.convertToString(
                FORMAT_YEAR_MONTH_DAY_JP
            )
    }

    fun convertRankingAnalysisPeriodStartTime(data: String?): String? {
        return data?.convertToDate(FORMAT_YEAR_MONTH_DAY_HYPHEN_HOUR_MINUTE_SECOND)
            ?.convertToString(
                FORMAT_YEAR_MONTH_DAY_JP_FULL
            )
    }

    fun convertRankingAnalysisPeriodEndTime(data: String?): String? {
        return data?.convertToDate(FORMAT_YEAR_MONTH_DAY_HYPHEN_HOUR_MINUTE_SECOND)
            ?.convertToString(
                FORMAT_MONTH_DAY_JP_FULL
            )
    }

    fun getDayInWeekfromString(data: String?): String? {
        val date = data?.convertToDate(FORMAT_YEAR_MONTH_DAY_HYPHEN_HOUR_MINUTE_SECOND)
        return date?.getDayOfWeekInJp()
    }

    fun convertApiDataDateToDisplayDate(data: String?): String? {
        return data?.convertToDate(FORMAT_YEAR_MONTH_DAY_HYPHEN)
            ?.convertToString(
                FORMAT_MONTH_DAY_JP
            )
    }

    fun convertApiDataDateYearToDisplayDateYear(data: String?): String? {
        return data?.convertToDate(FORMAT_YEAR_MONTH_HYPHEN)
            ?.convertToString(FORMAT_YEAR_ONE_DIGIT_MONTH_JP)
    }

    fun convertApiDataDateYearToDisplayYear(data: String?): String {
        return "${data}年"
    }

    fun convertApiDataDateMonthYearToDisplayDateMonthYear(data: String?): String? {
        return data?.convertToDate(FORMAT_YEAR_MONTH_DAY_HYPHEN)
            ?.convertToString(
                FORMAT_YEAR_MONTH_DAY_DAY_OF_WEEK_JP
            )
    }

    fun convertApiDataDateMonthYearToDisplayDateMonthYearFull(data: String?): String? {
        return data?.convertToDate(FORMAT_YEAR_MONTH_DAY_HYPHEN)
            ?.convertToString(
                FORMAT_YEAR_MONTH_DAY_JP
            )
    }

    fun convertApiDataYearMonthDayToDisplayYearMonthDayJP(data: String?): String? {
        return data?.convertToDate(FORMAT_YEAR_MONTH_DAY)
            ?.convertToString(
                FORMAT_YEAR_MONTH_DAY_DAY_OF_WEEK_JP
            )
    }

}