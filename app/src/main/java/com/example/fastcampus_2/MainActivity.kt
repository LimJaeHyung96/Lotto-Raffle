package com.example.fastcampus_2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import java.util.*

class MainActivity : AppCompatActivity() {
    private val clearButton : Button by lazy {
        findViewById<Button>(R.id.clearButton)
    }
    private val runButton : Button by lazy {
        findViewById<Button>(R.id.runButton)
    }
    private val addButton : Button by lazy {
        findViewById<Button>(R.id.addButton)
    }
    private val numberPicker : NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.numberPicker)
    }
    private val numberTextViewList : List<TextView> by lazy {
        listOf<TextView>(
            findViewById<TextView>(R.id.textView1),
            findViewById<TextView>(R.id.textView2),
            findViewById<TextView>(R.id.textView3),
            findViewById<TextView>(R.id.textView4),
            findViewById<TextView>(R.id.textView5),
            findViewById<TextView>(R.id.textView6)
        )
    }

    private var didRun = false
    private val pickNumberSet = hashSetOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        numberPicker.minValue = 1
        numberPicker.maxValue = 45

        initRunButton()
        initAddButton()
        initClearButton()
    }

    private fun initClearButton(){
        clearButton.setOnClickListener {
            didRun = false
            pickNumberSet.clear()
            numberTextViewList.forEach {
                it.isVisible = false
            }
        }
    }

    private fun initAddButton(){
        addButton.setOnClickListener {
            if(didRun) {
                Toast.makeText(this, "초기화 후에 시도해주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(pickNumberSet.size >= 5){
                Toast.makeText(this, "번호는 5개까지만 선택할 수 있습니다", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(pickNumberSet.contains(numberPicker.value)){
                Toast.makeText(this, "이미 선택한 번호입니다", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val textView = numberTextViewList[pickNumberSet.size]
            textView.isVisible = true
            textView.text = numberPicker.value.toString()

            pickNumberSet.add(numberPicker.value)
        }
    }

    private fun initRunButton(){
        runButton.setOnClickListener {
            val list = getRandomNumber()
            didRun = true

            list.forEachIndexed { index, number ->
                val textView = numberTextViewList[index]
                textView.text = number.toString()
                textView.isVisible = true
            }
        }
    }

    private fun getRandomNumber() : List<Int> {
        val list = mutableListOf<Int>().apply {
            for(i in 1..45){
                if(pickNumberSet.contains(i)){
                    continue
                }
                this.add(i)
            }
        }//apply는 초기화하는 데에 많이 사용

        list.shuffle()

        val result = pickNumberSet.toList() + list.subList(0, 6 - pickNumberSet.size)

        return result.sorted()
    }
}