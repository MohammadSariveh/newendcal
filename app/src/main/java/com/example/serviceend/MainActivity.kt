Mohammad, [8/14/2026 12:56]
package com.example.serviceend

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.widget.*
import java.util.Calendar
import java.util.Locale
import kotlin.math.floor
import kotlin.math.round

data class JDate(
    val y: Int,
    val m: Int,
    val d: Int
)

class MainActivity : Activity() {

    private lateinit var start: EditText
    private lateinit var extraMonths: EditText
    private lateinit var extraDays: EditText
    private lateinit var result: TextView
    private lateinit var typeText: TextView

    private var local = true

    private fun dp(value: Int): Int {
        return (value * resources.displayMetrics.density).toInt()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(
                dp(18),
                dp(18),
                dp(18),
                dp(18)
            )
            setBackgroundColor(
                Color.rgb(247, 249, 248)
            )
        }

        val content = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
        }

        val scroll = ScrollView(this)
        scroll.addView(content)

        // عنوان
        content.addView(
            TextView(this).apply {
                text = "🎖️  محاسبه‌گر پایان خدمت"
                textSize = 25f
                typeface = Typeface.DEFAULT_BOLD
                gravity = Gravity.CENTER
                setTextColor(
                    Color.rgb(15, 118, 110)
                )
                setPadding(
                    0,
                    dp(8),
                    0,
                    dp(4)
                )
            }
        )

        // توضیح
        content.addView(
            TextView(this).apply {
                text = "ابتدا نوع خدمت را انتخاب کنید"
                textSize = 14f
                gravity = Gravity.CENTER
                setTextColor(Color.DKGRAY)
                setPadding(
                    0,
                    0,
                    0,
                    dp(12)
                )
            }
        )

        // -------------------------------------------------
        // انتخاب بومی / غیر بومی
        // -------------------------------------------------

        val typeBox = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER
        }

        lateinit var localButton: Button
        lateinit var nonLocalButton: Button

        localButton = Button(this).apply {
            text = "🇮🇷  بومی\n۵ روز"
            textSize = 15f

            setOnClickListener {
                local = true

                typeText.text =
                    "نوع خدمت: 🇮🇷 بومی  |  کسری ماهانه: ۵ روز"

                setSelectedButton(
                    this,
                    true
                )

                setSelectedButton(
                    nonLocalButton,
                    false
                )
            }
        }

        nonLocalButton = Button(this).apply {
            text = "🌍  غیر بومی\n۱۲ روز"
            textSize = 15f

            setOnClickListener {
                local = false

                typeText.text =
                    "نوع خدمت: 🌍 غیر بومی  |  کسری ماهانه: ۱۲ روز"

                setSelectedButton(
                    this,
                    true
                )

                setSelectedButton(
                    localButton,
                    false
                )
            }
        }

        typeBox.addView(

Mohammad, [8/14/2026 12:56]
// -------------------------------------------------
        // نام سازنده
        // -------------------------------------------------

        content.addView(
            TextView(this).apply {

                text =
                    "این اپ توسط محمد سریوه توسعه داده شده است"

                textSize = 13f

                gravity = Gravity.CENTER

                setTextColor(Color.GRAY)

                setPadding(
                    0,
                    dp(22),
                    0,
                    dp(8)
                )
            }
        )

        root.addView(
            scroll,
            LinearLayout.LayoutParams(
                -1,
                0,
                1f
            )
        )

        setContentView(root)
    }

    // =====================================================
    // ظاهر دکمه انتخاب
    // =====================================================

    private fun setSelectedButton(
        button: Button,
        selected: Boolean
    ) {
        button.alpha =
            if (selected) 1f else 0.55f
    }

    // =====================================================
    // Label
    // =====================================================

    private fun label(
        textValue: String
    ): TextView {

        return TextView(this).apply {

            text = textValue

            textSize = 15f

            typeface =
                Typeface.DEFAULT_BOLD

            setTextColor(
                Color.rgb(
                    50,
                    65,
                    60
                )
            )

            setPadding(
                0,
                dp(8),
                0,
                dp(4)
            )
        }
    }

    // =====================================================
    // Input
    // =====================================================

    private fun input(
        hintText: String
    ): EditText {

        return EditText(this).apply {

            hint = hintText

            textSize = 16f

            setPadding(
                dp(12),
                dp(10),
                dp(12),
                dp(10)
            )

            background =
                GradientDrawable().apply {

                    setColor(Color.WHITE)

                    cornerRadius =
                        dp(10).toFloat()

                    setStroke(
                        dp(1),
                        Color.rgb(
                            210,
                            220,
                            216
                        )
                    )
                }
        }
    }

    // =====================================================
    // تبدیل اعداد فارسی به انگلیسی
    // =====================================================

    private fun normalize(
        value: String
    ): String {

        return value.map {

            when (it) {

                '۰' -> '0'
                '۱' -> '1'
                '۲' -> '2'
                '۳' -> '3'
                '۴' -> '4'
                '۵' -> '5'
                '۶' -> '6'
                '۷' -> '7'
                '۸' -> '8'
                '۹' -> '9'

                else -> it
            }

        }.joinToString("")
    }

    // =====================================================
    // تبدیل تاریخ شمسی وارد شده
    // =====================================================

    private fun parse(
        value: String
    ): JDate? {

        val parts =
            normalize(value.trim())
                .split(
                    "/",
                    "-",
                    "."
                )
                .mapNotNull {
                    it.toIntOrNull()
                }

        if (parts.size != 3) {
            return null
        }

        val y = parts[0]
        val m = parts[1]
        val d = parts[2]

        if (m !in 1..12) {
            return null
        }

Mohammad, [8/14/2026 12:56]
localButton,
            LinearLayout.LayoutParams(
                0,
                dp(70),
                1f
            ).apply {
                setMargins(
                    0,
                    0,
                    dp(6),
                    0
                )
            }
        )

        typeBox.addView(
            nonLocalButton,
            LinearLayout.LayoutParams(
                0,
                dp(70),
                1f
            ).apply {
                setMargins(
                    dp(6),
                    0,
                    0,
                    0
                )
            }
        )

        content.addView(typeBox)

        // متن نوع خدمت
        typeText = TextView(this).apply {
            text =
                "نوع خدمت: 🇮🇷 بومی  |  کسری ماهانه: ۵ روز"

            textSize = 14f
            typeface = Typeface.DEFAULT_BOLD
            gravity = Gravity.CENTER

            setTextColor(
                Color.rgb(15, 118, 110)
            )

            setPadding(
                0,
                dp(12),
                0,
                dp(10)
            )
        }

        content.addView(typeText)

        setSelectedButton(
            localButton,
            true
        )

        // -------------------------------------------------
        // تاریخ شروع
        // -------------------------------------------------

        content.addView(
            label("تاریخ شروع خدمت")
        )

        start = input(
            "مثال: ۱۴۰۴/۱۱/۰۱"
        )

        content.addView(start)

        // -------------------------------------------------
        // کسری ماه
        // -------------------------------------------------

        content.addView(
            label("کسری اضافی (ماه)")
        )

        extraMonths = input(
            "مثال: ۶"
        )

        extraMonths.inputType = 2

        content.addView(extraMonths)

        // -------------------------------------------------
        // کسری روز
        // -------------------------------------------------

        content.addView(
            label("کسری اضافی (روز)")
        )

        extraDays = input(
            "مثال: ۰"
        )

        extraDays.inputType = 2

        content.addView(extraDays)

        // -------------------------------------------------
        // دکمه محاسبه
        // -------------------------------------------------

        content.addView(
            Button(this).apply {
                text = "🧮  محاسبه پایان خدمت"
                textSize = 16f

                setOnClickListener {
                    calculate()
                }
            }
        )

        // -------------------------------------------------
        // دکمه تاریخ امروز
        // -------------------------------------------------

        content.addView(
            Button(this).apply {
                text = "📅  استفاده از تاریخ امروز"

                setOnClickListener {
                    start.setText(
                        format(todayJ())
                    )
                }
            }
        )

        // -------------------------------------------------
        // نتیجه
        // -------------------------------------------------

        result = TextView(this).apply {

            textSize = 16f

            setTextColor(
                Color.rgb(25, 55, 50)
            )

            setPadding(
                dp(14),
                dp(16),
                dp(14),
                dp(16)
            )

            background =
                GradientDrawable().apply {

                    setColor(Color.WHITE)

                    cornerRadius =
                        dp(14).toFloat()

                    setStroke(
                        dp(1),
                        Color.rgb(
                            210,
                            220,
                            216
                        )
                    )
                }
        }

        content.addView(result)

Mohammad, [8/14/2026 12:56]
intArrayOf(
                0,
                31,
                59,
                90,
                120,
                151,
                181,
                212,
                243,
                273,
                304,
                334
            )

        var days =
            365 * (gy - 1600) +
                    (gy - 1600 + 3) / 4 -
                    (gy - 1600 + 99) / 100 +
                    (gy - 1600 + 399) / 400 +
                    gd -
                    1 +
                    monthDays[gm - 1]

        val gregorianLeap =
            (
                gy % 4 == 0 &&
                        gy % 100 != 0
                ) ||
                    gy % 400 == 0

        if (
            gm > 2 &&
            gregorianLeap
        ) {
            days++
        }

        var j =
            days - 79

        val n =
            j / 12053

        j %= 12053

        var jy =
            979 +
                    33 * n +
                    4 * (j / 1461)

        j %= 1461

        if (j >= 366) {

            jy +=
                (j - 1) / 365

            j =
                (j - 1) % 365
        }

        val jm =
            if (j < 186) {

                1 + j / 31

            } else {

                7 + (j - 186) / 30
            }

        val day =
            1 +
                    if (j < 186) {

                        j % 31

                    } else {

                        (j - 186) % 30
                    }

        return JDate(
            jy,
            jm,
            day
        )
    }

    // =====================================================
    // محاسبه اصلی
    // =====================================================

    private fun calculate() {

        val startDate =
            parse(
                start.text.toString()
            )

        if (startDate == null) {

            result.text =
                "❌ تاریخ شروع را به شکل زیر وارد کن:\n\n۱۴۰۴/۱۱/۰۱"

            return
        }

        val extraMonth =
            normalize(
                extraMonths.text.toString()
            ).toDoubleOrNull()
                ?: 0.0

        val extraDay =
            normalize(
                extraDays.text.toString()
            ).toDoubleOrNull()
                ?: 0.0

        val extraTotal =
            extraMonth +
                    extraDay / 30.0

        // -------------------------------------------------
        // نرخ کسری
        // -------------------------------------------------

        val rateDays =
            if (local) 5 else 12

        val rateText =
            if (local) "۵" else "۱۲"

        val serviceType =
            if (local) {
                "🇮🇷 بومی"
            } else {
                "🌍 غیر بومی"
            }

        // -------------------------------------------------
        // فرمول
        //
        // x + x*(rate/30) + extra = 21
        //
        // -------------------------------------------------

        val actualService =
            (
                21.0 -
                        extraTotal
                ) /
                    (
                        1.0 +
                                rateDays / 30.0
                        )

        val serviceMonths =
            floor(
                actualService
            ).toInt()

        val serviceDays =
            round(
                (
                    actualService -
                            serviceMonths
                    ) * 30.0
            ).toInt()

        val monthlyDeduction =
            round(
                actualService *
                        rateDays
            ).toInt()

        // -------------------------------------------------
        // محاسبه تاریخ پایان
        // -------------------------------------------------

        val finishDate =
            addDays(
                addMonths(
                    startDate,
                    serviceMonths
                ),
                serviceDays - 1
            )

Mohammad, [8/14/2026 12:56]
val maxDay =
            if (m <= 6) {
                31
            } else if (m <= 11) {
                30
            } else {
                if (leap(y)) 30 else 29
            }

        if (y < 1300) {
            return null
        }

        if (d !in 1..maxDay) {
            return null
        }

        return JDate(
            y,
            m,
            d
        )
    }

    // =====================================================
    // سال کبیسه
    // =====================================================

    private fun leap(
        year: Int
    ): Boolean {

        val r =
            ((year - 474) % 2820 + 2820) % 2820

        return r in 0..1
    }

    // =====================================================
    // تعداد روزهای ماه
    // =====================================================

    private fun dim(
        year: Int,
        month: Int
    ): Int {

        return when {

            month <= 6 ->
                31

            month <= 11 ->
                30

            leap(year) ->
                30

            else ->
                29
        }
    }

    // =====================================================
    // اضافه کردن روز
    // =====================================================

    private fun addDays(
        date: JDate,
        amount: Int
    ): JDate {

        var y = date.y
        var m = date.m
        var d = date.d

        var n = amount

        while (n > 0) {

            d++

            if (d > dim(y, m)) {

                d = 1

                if (m == 12) {

                    m = 1
                    y++

                } else {

                    m++
                }
            }

            n--
        }

        return JDate(
            y,
            m,
            d
        )
    }

    // =====================================================
    // اضافه کردن ماه
    // =====================================================

    private fun addMonths(
        date: JDate,
        amount: Int
    ): JDate {

        var y = date.y
        var m = date.m
        var d = date.d

        repeat(amount) {

            if (m == 12) {

                m = 1
                y++

            } else {

                m++
            }

            d =
                minOf(
                    d,
                    dim(y, m)
                )
        }

        return JDate(
            y,
            m,
            d
        )
    }

    // =====================================================
    // نمایش تاریخ
    // =====================================================

    private fun format(
        date: JDate
    ): String {

        return String.format(
            Locale.US,
            "%04d/%02d/%02d",
            date.y,
            date.m,
            date.d
        )
    }

    // =====================================================
    // نمایش عدد
    // =====================================================

    private fun formatNum(
        value: Double
    ): String {

        return if (
            kotlin.math.abs(
                value - value.toInt()
            ) < 0.0001
        ) {

            value.toInt().toString()

        } else {

            String.format(
                Locale.US,
                "%.2f",
                value
            )
        }
    }

    // =====================================================
    // تاریخ امروز شمسی
    // =====================================================

    private fun todayJ(): JDate {

        val calendar =
            Calendar.getInstance()

        val gy =
            calendar.get(Calendar.YEAR)

        val gm =
            calendar.get(Calendar.MONTH) + 1

        val gd =
            calendar.get(Calendar.DAY_OF_MONTH)

        val monthDays =

Mohammad, [8/14/2026 12:56]
// -------------------------------------------------
        // نمایش نتیجه
        // -------------------------------------------------

        result.text =
            """
🎯  تاریخ پایان خدمت
━━━━━━━━━━━━━━━━

${format(finishDate)}

📌 جزئیات محاسبه

نوع خدمت:
$serviceType

خدمت اسمی:
۲۱ ماه

کسری به ازای هر ماه خدمت:
$rateText روز

کسری اضافی:
${formatNum(extraMonth)} ماه و ${formatNum(extraDay)} روز

مجموع کسری اضافی:
${formatNum(extraTotal)} ماه

━━━━━━━━━━━━━━━━

📐 فرمول محاسبه:

x + (x × $rateText/۳۰) + کسری اضافی = ۲۱

مدت خدمت واقعی:

$serviceMonths ماه و $serviceDays روز

کسری حاصل از خدمت ماهانه:

$monthlyDeduction روز

━━━━━━━━━━━━━━━━

📅 تاریخ شروع:
${format(startDate)}

✅ تاریخ نهایی پایان خدمت:

${format(finishDate)}
            """.trimIndent()
    }
}
