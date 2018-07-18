package com.example.q.cs496_week3

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.*
import org.w3c.dom.Text
import java.util.Random

class WriteActivity : AppCompatActivity(), View.OnClickListener {

    private var classifier: HangulClassifier? = null
    private var paintView1: PaintView? = null
    private var paintView2: PaintView? = null
    private var startBtn: Button? = null
    private var submitBtn: Button? = null
    private var resultText: EditText? = null
    private var solutionText: String? = null
    private var solutionTextView: TextView? = null
    private var solutionTextArray: Array<String>? = null
    private var currentTopLabels1: Array<String>? = null
    private var currentTopLabels2: Array<String>? = null
    private var tts: TextToSpeech? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write)

        tts = TextToSpeech(this, TextToSpeech.OnInitListener{})

        paintView1 = findViewById(R.id.paintView1) as PaintView
        paintView2 = findViewById(R.id.paintView2) as PaintView
        solutionTextView = findViewById(R.id.solutionTextView) as TextView

        solutionTextArray = arrayOf("가나", "소주", "맥주", "닭발", "지각", "", "")

        val submitBtn = findViewById(R.id.submitBtn) as Button
        submitBtn.setOnClickListener(this)

        val backBtn = findViewById(R.id.backBtn) as Button
        backBtn.setOnClickListener(this)

        val startBtn = findViewById(R.id.startBtn) as Button
        startBtn.setOnClickListener(this)


        resultText = findViewById(R.id.editText) as EditText

        loadModel()
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.startBtn -> {
               var length: Int = solutionTextArray!!.size
                val random = Random()
                var index = random.nextInt(length)
                solutionText = solutionTextArray!![index]
                solutionTextView!!.text = solutionText
                resultText!!.setText("")
                speakOut()
            }
            R.id.submitBtn -> {
                classify()
                paintView1!!.reset()
                paintView1!!.invalidate()
                paintView2!!.reset()
                paintView2!!.invalidate()
                Log.d("************", solutionText)
                Log.d("************", resultText.toString())
                if (solutionText == resultText!!.text.toString()) {
                    Toast.makeText(this,"정답입니다!", Toast.LENGTH_SHORT).show()
                }
                else {
                    Toast.makeText(this,"오답입니다!", Toast.LENGTH_SHORT).show()
                }
            }
            R.id.backBtn -> {
                backspace()
                paintView1!!.reset()
                paintView1!!.invalidate()
                paintView2!!.reset()
                paintView2!!.invalidate()
            }

        }
    }


    private fun backspace() {
        val len = resultText!!.text.length
        if (len > 0) {
            resultText!!.text.delete(len - 1, len)
        }
    }



    private fun classify() {
        val pixels1 = paintView1!!.pixelData
        val pixels2 = paintView2!!.pixelData
        currentTopLabels1 = classifier!!.classify(pixels1)
        resultText!!.append(currentTopLabels1!![0])
        currentTopLabels2 = classifier!!.classify(pixels2)
        resultText!!.append(currentTopLabels2!![0])

    }

    private fun speakOut() {
        tts!!.speak(solutionText, TextToSpeech.QUEUE_FLUSH, null,"")
    }



    override fun onResume() {
        paintView1!!.onResume()
        paintView2!!.onResume()
        super.onResume()
    }

    override fun onPause() {
        paintView1!!.onPause()
        paintView2!!.onPause()
        super.onPause()
    }


    private fun loadModel() {
        Thread(Runnable {
            try {
                classifier = HangulClassifier.create(assets,
                        MODEL_FILE, LABEL_FILE, PaintView.FEED_DIMENSION,
                        "input", "keep_prob", "output")
            } catch (e: Exception) {
                throw RuntimeException("Error loading pre-trained model.", e)
            }
        }).start()
    }

    companion object {

        private val LABEL_FILE = "2350-common-hangul.txt"
        private val MODEL_FILE = "optimized_hangul_tensorflow.pb"
    }
}
