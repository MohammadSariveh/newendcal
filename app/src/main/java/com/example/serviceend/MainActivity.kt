package com.example.serviceend

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.widget.*
import java.util.*

data class JDate(val y: Int, val m: Int, val d: Int)

class MainActivity : Activity() {
    private lateinit var start: EditText
    private lateinit var extraMonths: EditText
    private lateinit var extraDays: EditText
    private lateinit var result: TextView
    private lateinit var typeText: TextView
    private var local = true

    private fun dp(v: Int) = (v * resources.displayMetrics.density).toInt()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(18), dp(18), dp(18), dp(18))
            setBackgroundColor(Color.rgb(247, 249, 248))
        }

        val content = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
        }
        val scroll = ScrollView(this)
        scroll.addView(content)

        content.addView(TextView(this).apply {
            text = "🎖️  محاسبه‌گر پایان خدمت"
            textSize = 25f
            typeface = Typeface.DEFAULT_BOLD
            gravity = Gravity.CENTER
            setTextColor(Color.rgb(15, 118, 110))
            setPadding(0, dp(8), 0, dp(4))
        })

        content.addView(TextView(this).apply {
            text = "ابتدا نوع خدمت را انتخاب کنید"
            textSize = 14f
            gravity = Gravity.CENTER
            setTextColor(Color.DKGRAY)
            setPadding(0, 0, 0, dp(12))
        })

        val typeBox = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER
        }

        val localButton = Button(this).apply {
            text = "🇮🇷  بومی\n۵ روز"
            textSize = 15f
            setOnClickListener {
                local = true
                typeText.text = "نوع خدمت: 🇮🇷 بومی  |  کسری ماهانه: ۵ روز"
                setSelectedButton(this, true)
                setSelectedButton(nonLocalButton, false)
            }
        }

        val nonLocalButton = Button(this).apply {
            text = "🌍  غیر بومی\n۱۲ روز"
            textSize = 15f
            setOnClickListener {
                local = false
                typeText.text = "نوع خدمت: 🌍 غیر بومی  |  کسری ماهانه: ۱۲ روز"
                setSelectedButton(this, true)
                setSelectedButton(localButton, false)
            }
        }

        typeBox.addView(localButton, LinearLayout.LayoutParams(0, dp(70), 1f).apply {
            setMargins(0, 0, dp(6), 0)
        })
        typeBox.addView(nonLocalButton, LinearLayout.LayoutParams(0, dp(70), 1f).apply {
            setMargins(dp(6), 0, 0, 0)
        })
        content.addView(typeBox)

        typeText = TextView(this).apply {
            text = "نوع خدمت: 🇮🇷 بومی  |  کسری ماهانه: ۵ روز"
            textSize = 14f
            typeface = Typeface.DEFAULT_BOLD
            gravity = Gravity.CENTER
            setTextColor(Color.rgb(15, 118, 110))
            setPadding(0, dp(12), 0, dp(10))
        }
        content.addView(typeText)
        setSelectedButton(localButton, true)

        content.addView(label("تاریخ شروع خدمت"))
        start = input("مثال: ۱۴۰۴/۱۱/۰۱")
        content.addView(start)

        content.addView(label("کسری اضافی (ماه)"))
        extraMonths = input("مثال: ۶")
        extraMonths.inputType = 2
        content.addView(extraMonths)

        content.addView(label("کسری اضافی (روز)"))
        extraDays = input("مثال: ۰")
        extraDays.inputType = 2
        content.addView(extraDays)

        content.addView(Button(this).apply {
            text = "🧮  محاسبه پایان خدمت"
            textSize = 16f
            setOnClickListener { calculate() }
        })

        content.addView(Button(this).apply {
            text = "📅  استفاده از تاریخ امروز"
            setOnClickListener { start.setText(format(todayJ())) }
        })

        result = TextView(this).apply {
            textSize = 16f
            setTextColor(Color.rgb(25, 55, 50))
            setPadding(dp(14), dp(16), dp(14), dp(16))
            background = GradientDrawable().apply {
                setColor(Color.WHITE)
                cornerRadius = dp(14).toFloat()
                setStroke(dp(1), Color.rgb(210, 220, 216))
            }
        }
        content.addView(result)

        content.addView(TextView(this).apply {
            text = "این اپ توسط محمد سریوه توسعه داده شده است"
            textSize = 13f
            gravity = Gravity.CENTER
            setTextColor(Color.GRAY)
            setPadding(0, dp(22), 0, dp(8))
        })

        root.addView(scroll, LinearLayout.LayoutParams(-1, 0, 1f))
        setContentView(root)
    }

    private fun setSelectedButton(button: Button, selected: Boolean) {
        button.alpha = if (selected) 1f else 0.55f
    }

    private fun label(t: String) = TextView(this).apply {
        text = t
        textSize = 15f
        typeface = Typeface.DEFAULT_BOLD
        setTextColor(Color.rgb(50, 65, 60))
        setPadding(0, dp(8), 0, dp(4))
    }

    private fun input(h: String) = EditText(this).apply {
        hint = h
        textSize = 16f
        setPadding(dp(12), dp(10), dp(12), dp(10))
        background = GradientDrawable().apply {
            setColor(Color.WHITE)
            cornerRadius = dp(10).toFloat()
            setStroke(dp(1), Color.rgb(210, 220, 216))
        }
    }

    private fun normalize(s: String) = s.map {
        when (it) {
            '۰' -> '0'; '۱' -> '1'; '۲' -> '2'; '۳' -> '3'; '۴' -> '4'
            '۵' -> '5'; '۶' -> '6'; '۷' -> '7'; '۸' -> '8'; '۹' -> '9'
            else -> it
        }
    }.joinToString("")

    private fun parse(s: String): JDate? {
        val a = normalize(s.trim()).split("/", "-", ".").mapNotNull { it.toIntOrNull() }
        if (a.size != 3) return null
        val y = a[0]; val m = a[1]; val d = a[2]
        if (m !in 1..12) return null
        val max = if (m <= 6) 31 else if (m <= 11) 30 else if (leap(y)) 30 else 29
        if (y < 1300 || d !in 1..max) return null
        return JDate(y, m, d)
    }

    private fun leap(y: Int): Boolean {
        val r = ((y - 474) % 2820 + 2820) % 2820
        return r in 0..1
    }

    private fun dim(y: Int, m: Int) =
        if (m <= 6) 31 else if (m <= 11) 30 else if (leap(y)) 30 else 29

    private fun addDays(j: JDate, n0: Int): JDate {
        var y = j.y
        var m = j.m
        var d = j.d
        var n = n0
        while (n > 0) {
            d++
            if (d > dim(y, m)) {
                d = 1
                if (m == 12) { m = 1; y++ } else m++
            }
            n--
        }
        return JDate(y, m, d)
    }

    private fun addMonths(j: JDate, n: Int): JDate {
        var y = j.y
        var m = j.m
        var d = j.d
        repeat(n) {
            if (m == 12) { m = 1; y++ } else m++
            d = minOf(d, dim(y, m))
        }
        return JDate(y, m, d)
    }

    private fun format(j: JDate) =
        String.format(Locale.US, "%04d/%02d/%02d", j.y, j.m, j.d)

    private fun formatNum(x: Double) =
        if (kotlin.math.abs(x - x.toInt()) < 0.0001) x.toInt().toString()
        else String.format(Locale.US, "%.2f", x)

    private fun todayJ(): JDate {
        val c = Calendar.getInstance()
        val gy = c.get(Calendar.YEAR)
        val gm = c.get(Calendar.MONTH) + 1
        val gd = c.get(Calendar.DAY_OF_MONTH)
        val md = intArrayOf(0,31,59,90,120,151,181,212,243,273,304,334)
        var days = 365 * (gy - 1600) + (gy - 1600 + 3) / 4 -
                (gy - 1600 + 99) / 100 + (gy - 1600 + 399) / 400 +
                gd - 1 + md[gm - 1]
        if (gm > 2 && ((gy % 4 == 0 && gy % 100 != 0) || gy % 400 == 0)) days++
        var j = days - 79
        val n = j / 12053
        j %= 12053
        var jy = 979 + 33 * n + 4 * (j / 1461)
        j %= 1461
        if (j >= 366) { jy += (j - 1) / 365; j = (j - 1) % 365 }
        val jm = if (j < 186) 1 + j / 31 else 7 + (j - 186) / 30
        val day = 1 + if (j < 186) j % 31 else (j - 186) % 30
        return JDate(jy, jm, day)
    }

    private fun calculate() {
        val s = parse(start.text.toString())
        if (s == null) {
            result.text = "❌ تاریخ شروع را مثل ۱۴۰۴/۱۱/۰۱ وارد کن."
            return
        }

        val em = extraMonths.text.toString().toDoubleOrNull() ?: 0.0
        val ed = extraDays.text.toString().toDoubleOrNull() ?: 0.0
        val extra = em + ed / 30.0

        val rateDays = if (local) 5 else 12
        val rateText = if (local) "۵" else "۱۲"
        val typeTextValue = if (local) "🇮🇷 بومی" else "🌍 غیر بومی"

        // x + (x * rate/30) + extra = 21
        val actual = (21.0 - extra) / (1.0 + rateDays / 30.0)
        val months = kotlin.math.floor(actual).toInt()
        val days = kotlin.math.round((actual - months) * 30.0).toInt()
        val monthlyDeduction = kotlin.math.round(actual * rateDays).toInt()

        // Start date is day 1.
        val finish = addDays(addMonths(s, months), days - 1)

        result.text = """
🎯  تاریخ پایان خدمت
━━━━━━━━━━━━━━━━
${format(finish)}

📌  جزئیات محاسبه

نوع خدمت:
$typeTextValue

خدمت اسمی:
۲۱ ماه

کسری به ازای هر ماه خدمت:
$rateText روز

کسری اضافی:
${formatNum(em)} ماه و ${formatNum(ed)} روز

خدمت اسمی پس از کسری اضافی:
${formatNum(21.0 - extra)} ماه

━━━━━━━━━━━━━━━━

📐 فرمول محاسبه:

x + (x × $rateText/۳۰) + کسری اضافی = ۲۱

مدت خدمت واقعی:
$months ماه و $days روز

کسری حاصل از خدمت ماهانه:
$monthlyDeduction روز

━━━━━━━━━━━━━━━━

📅 تاریخ شروع:
${format(s)}

✅ تاریخ نهایی پایان خدمت:
${format(finish)}
        """.trimIndent()
    }
}
