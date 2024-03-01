package com.example.scientificcalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.scientificcalculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(){
    private val binding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.zero.setOnClickListener {
            updateExpression("0")
        }
        binding.one.setOnClickListener {
            updateExpression("1")
        }
        binding.two.setOnClickListener {
            updateExpression("2")
        }
        binding.three.setOnClickListener {
            updateExpression("3")
        }
        binding.four.setOnClickListener {
            updateExpression("4")
        }
        binding.five.setOnClickListener {
            updateExpression("5")
        }
        binding.six.setOnClickListener {
            updateExpression("6")
        }
        binding.seven.setOnClickListener {
            updateExpression("7")
        }
        binding.eight.setOnClickListener {
            updateExpression("8")
        }
        binding.nine.setOnClickListener {
            updateExpression("9")
        }
        binding.percent.setOnClickListener {
            updateExpression("%")
        }
        binding.dec.setOnClickListener {
            updateExpression(".")
        }
        binding.plus.setOnClickListener {
            updateExpression("+")
        }
        binding.sub.setOnClickListener {
            updateExpression("-")
        }
        binding.mul.setOnClickListener {
            updateExpression("*")
        }
        binding.div.setOnClickListener {
            updateExpression("/")
        }
        binding.fact.setOnClickListener {
            updateExpression("!")
        }
        binding.square.setOnClickListener {
            updateExpression("²")
        }
        binding.underroot.setOnClickListener {
            updateExpression("√")
        }
        binding.inverse.setOnClickListener {
            updateExpression("^"+"(-1)")
        }
        binding.sin.setOnClickListener {
            updateExpression("sin")
        }
        binding.cos.setOnClickListener {
            updateExpression("cos")
        }
        binding.tan.setOnClickListener {
            updateExpression("tan")
        }
        binding.log.setOnClickListener {
            updateExpression("log")
        }
        binding.ln.setOnClickListener {
            updateExpression("ln")
        }
        binding.small1.setOnClickListener {
            updateExpression("(")
        }
        binding.small2.setOnClickListener {
            updateExpression(")")
        }
        binding.AC.setOnClickListener {
            binding.expresion.text = ""
            binding.answer.text=""
        }
        binding.C.setOnClickListener {
            var str: CharSequence? = binding.expresion.text
            binding.expresion.text = str?.substring(0, str.lastIndex)
        }
        binding.plus.setOnClickListener {
            val str: String = binding.expresion.text.toString()
            if(!str.get(index = str.length-1).equals("+")){
                binding.expresion.text = (binding.expresion.text.toString()+"+")
            }
        }
        binding.sub.setOnClickListener {
            val str: String = binding.expresion.text.toString()
            if(!str.get(index = str.length-1).equals("-")){
                binding.expresion.text = (binding.expresion.text.toString()+"-")
            }
        }
        binding.mul.setOnClickListener {
            val str: String = binding.expresion.text.toString()
            if(!str.get(index = str.length-1).equals("*")){
                binding.expresion.text = (binding.expresion.text.toString()+"*")
            }
        }
        binding.underroot.setOnClickListener {
            if(binding.expresion.text.toString().isEmpty()){
                Toast.makeText(this, "Please enter a valid number", Toast.LENGTH_SHORT).show()
            }else {
                val str: String = binding.expresion.text.toString()
                val r = Math.sqrt(str.toDouble())
                val result = r.toString()
                binding.expresion.text = result
            }
        }
        binding.square.setOnClickListener {
            if(binding.expresion.text.toString().isEmpty()){
                Toast.makeText(this, "please enter a valid number", Toast.LENGTH_SHORT).show()
            }else{
                val d:Double = binding.expresion.text.toString().toDouble()
                var square = d*d
                binding.expresion.text = square.toString()
                binding.answer.text = square.toString()

            }
        }
        binding.fact.setOnClickListener {
            if (binding.expresion.text.toString().isEmpty()){
                Toast.makeText(this, "please enter a valid number", Toast.LENGTH_SHORT).show()
            }else{
                val value:Int = binding.expresion.text.toString().toInt()
                val fact: Int = factorial(value)
                binding.expresion.text = fact.toString()
                binding.answer.text = fact.toString()
            }
        }
        binding.percent.setOnClickListener {
            if (binding.expresion.text.toString().isEmpty()){
                Toast.makeText(this, "please enter a valid number", Toast.LENGTH_SHORT).show()
            }else{
                val value:Int = binding.expresion.text.toString().toInt()
                val per: Double = percentage(value)
                binding.expresion.text = per.toString()
                binding.answer.text = per.toString()
            }
        }
        binding.equal.setOnClickListener {
            var str:String = binding.expresion.text.toString()
            val res:Double = evaluate(str)
            val r = res.toString()
            binding.expresion.text = r
            binding.answer.text = str
        }
    }

    private fun evaluate(str:String): Double {
            return object : Any(){
                var pos = -1
                var ch = 0
                fun nextChar(){
                    ch=if(++pos<str.length){
                        str[pos].toInt()
                    }else{
                        -1
                    }
                }
                fun eat(charToEdt:Int):Boolean{
                    while(ch==' '.toInt())nextChar()
                    if(ch==charToEdt){
                        nextChar()
                        return true
                    }
                    return false
                }
                fun parse():Double{
                    nextChar()
                    val x = parseExpression()
                    if(pos<str.length) throw RuntimeException("Unexpected : "+ch.toChar())
                    return x
                }
                fun parseExpression():Double{
                    var x = parseTerm()
                    while (true){
                        if(eat('+'.toInt()))x += parseTerm()
                        else if(eat('-'.toInt()))x -=parseTerm()
                        else return x
                    }
                }
                fun parseTerm():Double{
                    var x = parseFactor()
                    while(true){
                        if(eat('*'.toInt()))x*=parseFactor()
                        else if(eat('/'.toInt()))x/=parseFactor()
                        else return x
                    }
                }
                fun parseFactor():Double {
                    if (eat('+'.toInt())) return parseFactor()
                    if (eat('-'.toInt())) return -parseFactor()
                    var x: Double
                    val startpos = pos
                    if (eat('('.toInt())) {
                        x = parseExpression()
                        eat(')'.toInt())
                    } else if (ch >= '0'.toInt() && ch <= '9'.toInt() || ch == '.'.toInt()) {
                        while (ch >= '0'.toInt() && ch <= '9'.toInt() || ch == '.'.toInt()) nextChar()
                        x = str.substring(startpos, pos).toDouble()
                    } else if (ch >= 'a'.toInt() && ch <= 'z'.toInt()) {
                        while (ch >= 'a'.toInt() && ch <= 'z'.toInt()) nextChar()
                        val func = str.substring(startpos, pos)
                        x = parseFactor()
                        if (func == "sqrt") {
                            x = Math.sqrt(x)
                        } else if (func == "sin") {
                            x = Math.sin(Math.toRadians(x))
                        } else if (func == "cos") {
                            x = Math.cos(Math.toRadians(x))
                        } else if (func == "tan") {
                            x = Math.tan(Math.toRadians(x))
                        } else if (func == "log") {
                            x = Math.log10(x)
                        } else if (func == "ln") {
                            x = Math.log(x)
                        }

                    } else {
                        throw RuntimeException("Unexpected : " + ch.toChar())
                    }
                    if (eat('^'.toInt())) x = Math.pow(x, parseFactor())
                    return x
                }
            }.parse()
    }

    private fun updateExpression(digit: String) {
        binding.expresion.text = "${binding.expresion.text}$digit"
    }
    private  fun factorial(n:Int):Int{
        if(n<=1){
            return n;
        }else{
            return n*factorial(n-1)
        }
    }
    private fun percentage(n:Int):Double{
        var c:Double = n/100.0
        return c
    }
}