package com.example.converter

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

data class Currency(val flagRes: Int, val name: String, val code: String)
class MainActivity : AppCompatActivity() {
    private val currencyList = listOf(
        Currency(R.drawable.flag_eu, "Евро", "EUR"),
        Currency(R.drawable.flag_uk, "Английн фунт", "GBP"),
        Currency(R.drawable.flag_ru, "Оросын рубль", "RUB"),
        Currency(R.drawable.flag_cn, "Хятадын юань", "CNY"),
        Currency(R.drawable.flag_jp, "Японы иен", "JPY"),
        Currency(R.drawable.flag_kr, "БНСУ-ын вон", "KRW"),
        Currency(R.drawable.flag_au, "Австралийн доллар", "AUD"),
        Currency(R.drawable.flag_ch, "Швейцар франк", "CHF"),
        Currency(R.drawable.flag_ca, "Канад доллар", "CAD"),
        Currency(R.drawable.flag_sg, "Сингапур доллар", "SGD"),
        Currency(R.drawable.flag_se, "Швед крон", "SEK"),
        Currency(R.drawable.flag_tr, "Турк лир", "TRY"),
        Currency(R.drawable.flag_hk, "Гонконг доллар", "HKD")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val layoutList = findViewById<LinearLayout>(R.id.layoutCurrencyList)
        showCurrencyList(layoutList)
    }

    private fun showCurrencyList(layoutList: LinearLayout) {
        for (currency in currencyList) {
            val itemView = layoutInflater.inflate(R.layout.item_currency_row, layoutList, false)
            val flag = itemView.findViewById<ImageView>(R.id.imgFlag)
            val name = itemView.findViewById<TextView>(R.id.tvCurrencyName)
            val code = itemView.findViewById<TextView>(R.id.tvCurrencyCode)
            flag.setImageResource(currency.flagRes)
            name.text = currency.name
            code.text = currency.code

            itemView.setOnClickListener {
                openConverter(currency)
            }
            layoutList.addView(itemView)
        }
    }
    private fun openConverter(currency: Currency) {
        val intent = Intent(this, ConverterActivity::class.java)
        intent.putExtra("currencyName", currency.name)
        intent.putExtra("currencyCode", currency.code)
        startActivity(intent)
    }
}
