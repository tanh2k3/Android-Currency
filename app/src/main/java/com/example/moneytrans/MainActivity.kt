package com.example.moneytrans

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var sourceAmount: EditText
    private lateinit var sourceCurrency: Spinner
    private lateinit var targetAmount: EditText
    private lateinit var targetCurrency: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sourceAmount = findViewById(R.id.sourceAmount)
        sourceCurrency = findViewById(R.id.sourceCurrency)
        targetAmount = findViewById(R.id.targetAmount)
        targetCurrency = findViewById(R.id.targetCurrency)

        // Tạo các sự kiện cập nhật kết quả khi có thay đổi
        sourceAmount.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) = convertCurrency()
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        val onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                convertCurrency()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        sourceCurrency.onItemSelectedListener = onItemSelectedListener
        targetCurrency.onItemSelectedListener = onItemSelectedListener
    }

    private fun convertCurrency() {
        val sourceAmountText = sourceAmount.text.toString()
        if (sourceAmountText.isNotEmpty()) {
            val amount = sourceAmountText.toDouble()
            val sourceRate = getRateForCurrency(sourceCurrency.selectedItem.toString())
            val targetRate = getRateForCurrency(targetCurrency.selectedItem.toString())
            val convertedAmount = amount * (targetRate / sourceRate)
            targetAmount.setText(convertedAmount.toString())
        } else {
            targetAmount.setText("")
        }
    }

    private fun getRateForCurrency(currency: String): Double {
        return when (currency) {
            "USD" -> 1.0        // Đơn vị gốc là USD
            "EUR" -> 0.85       // 1 USD ~ 0.85 EUR
            "JPY" -> 110.0      // 1 USD ~ 110 JPY
            "VND" -> 25355.0    // 1 USD ~ 253551 VND
            "GBP" -> 0.76       // 1 USD ~ 0.76 GBP
            else -> 1.0         // Mặc định là USD nếu loại tiền không hợp lệ
        }
    }

}
