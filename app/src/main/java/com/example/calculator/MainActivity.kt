package com.example.calculator

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {

    var lastNumeric:Boolean = false
    var lastDot:Boolean = false

    private lateinit var  input:TextView
    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
         input = findViewById(R.id.input)
        mediaPlayer = MediaPlayer.create(applicationContext , R.raw.buttonclick)
    }

    fun onDigit(view: View) {
        mediaPlayer.start()
        val value = input.text.toString()
        if(value.startsWith("0")){
            input.text = ""
        }
        input.append((view as Button).text)
        lastNumeric = true
        lastDot = false
    }

    fun onClear(view: View) {
        mediaPlayer.start()
        input.text = "0"
    }

    fun onDecimal(view: View) {
        mediaPlayer.start()
        if(lastNumeric && !lastDot){
            input.append(".")
            lastNumeric = false
            lastDot = true
        }
    }

    fun onOperator(view : View){
        mediaPlayer.start()
        input.text?.let {
            if(lastNumeric && !isOperatorAdded(it.toString())){
                input.append((view as Button).text)
                lastNumeric = false
                lastDot = false
            }
        }

    }

    private fun isOperatorAdded(value : String) : Boolean{

        val check = if(value.startsWith("-")){
            return false
        }
        else{
            value.contains("/")||
            value.contains("*")||
            value.contains("-")||
            value.contains("+")

        }
        return check
        }

    fun onEqual(view: View){
        mediaPlayer.start()
        if(lastNumeric){
            var values:String = input.text.toString()
            var prefix =""

            try {

                if(values.startsWith("-")){
                    prefix = "-"
                    values = values.substring(1 )
                }
                if(values.contains("-")){
                    val splitValue = values.split("-")
                    val one = splitValue[0]
                    val two = splitValue[1]

                    input.text = removeZero((one.toDouble() - two.toDouble()).toString())
                }


                else if(values.contains("+")){
                    val splitValue = values.split("+")
                    val one = splitValue[0]
                    val two = splitValue[1]

                    input.text = removeZero((one.toDouble() + two.toDouble()).toString())
                }

                else if(values.contains("X")){
                    val splitValue = values.split("X")
                    val one = splitValue[0]
                    val two = splitValue[1]

                    input.text = removeZero((one.toDouble() * two.toDouble()).toString())
                }

                else if(values.contains("/")){
                    val splitValue = values.split("/")
                    val one = splitValue[0]
                    val two = splitValue[1]

                    input.text = removeZero((one.toDouble() / two.toDouble()).toString())
                }
            }
            catch (e:ArithmeticException){
                e.printStackTrace()
            }
        }

    }

    private fun removeZero(result:String) : String {
        var value = result
        if(result.contains(".0")) {
            value = result.substring(0, result.length - 2)
        }
        return value
    }
    }