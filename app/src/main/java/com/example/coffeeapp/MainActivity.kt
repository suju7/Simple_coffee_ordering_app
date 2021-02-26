package com.example.coffeeapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import org.w3c.dom.Text
import java.text.NumberFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private val cost = 70
    private val whippedCreamCost = 20
    private var name = ""
    private var msg = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private fun setQuantity(num: Int) {
        findViewById<TextView>(R.id.quantity).text = num.toString()
    }

    private fun getQuantity(): Int {
        val qty = findViewById<TextView>(R.id.quantity).text.toString().toInt()
        println("getQuantity qty value = $qty")
        return qty
    }

    private fun setBasePrice() {
        val quantity = getQuantity()
        val baseCost = quantity * cost
        findViewById<TextView>(R.id.basePrice).text = NumberFormat.getCurrencyInstance(Locale("hi", "IN")).format(baseCost)
    }


    fun incrementQuantity(view: View) {
        setQuantity(getQuantity() + 1)
        setBasePrice()
    }


    fun decrementQuantity(view: View) {
        if (getQuantity() > 0)
            setQuantity(getQuantity() - 1)
        setBasePrice()
    }


    fun clearEverything(view: View) {
        findViewById<EditText>(R.id.name).text = SpannableStringBuilder("")
        findViewById<CheckBox>(R.id.checkBox).isChecked = false
        setQuantity(0)
        setBasePrice()
        findViewById<TextView>(R.id.displayName).text = ""
        findViewById<TextView>(R.id.displayWhippedCream).text = ""
        findViewById<TextView>(R.id.displayTotal).text = ""
    }

    fun submitOrder(view: View){

        val name = findViewById<EditText>(R.id.name).text
        findViewById<TextView>(R.id.displayName).text = name

        var total = getQuantity() * cost

        var msg = "$name has ordered " + getQuantity() + " cup(s) of Coffee.\nWith add-ons : "

        if(findViewById<CheckBox>(R.id.checkBox).isChecked) {
            findViewById<TextView>(R.id.displayWhippedCream).text = "Whipped Cream"
            total += getQuantity() * whippedCreamCost
            msg += "whipped cream."
        }
        else {
            findViewById<TextView>(R.id.displayWhippedCream).text = "NIL"
            msg += "NIL."
        }

        findViewById<TextView>(R.id.displayTotal).text = NumberFormat.getCurrencyInstance(Locale("hi", "IN")).format(total)


        this.name = name.toString()
        msg += "\nTotal amount is " + findViewById<TextView>(R.id.displayTotal).text
        this.msg = msg
    }

    fun share(view: View){

        submitOrder(view)

        val sendIntent = Intent(Intent.ACTION_SEND)

        sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Coffee Order for $name")
        sendIntent.putExtra(Intent.EXTRA_TEXT, msg)
        sendIntent.type = "text/plain"

        //        if (intent.resolveActivity(packageManager) != null) {
        //            startActivity(intent)
        //        }

        startActivity(Intent.createChooser(sendIntent,"Send"))
    }
}