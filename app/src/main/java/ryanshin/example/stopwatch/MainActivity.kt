package ryanshin.example.stopwatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import java.util.*
import ryanshin.example.stopwatch.databinding.ActivityMainBinding
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var time = 0
    private var timerTask: Timer? = null
    private var isRunning = false
    private var lap = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //시작버튼눌렀을때 액션
        binding.fab.setOnClickListener {
            isRunning = !isRunning
            if ( isRunning)
                start()
            else
                pause()
        }
        // lapButton 눌렀을때 액션
        binding.labButton.setOnClickListener {
            recordLapTime()
        }
        binding.resetFab.setOnClickListener {
            reset()
        }
    }
    private fun start(){
        binding.fab.setImageResource(R.drawable.ic_baseline_pause_24)
        timerTask = timer( period = 10){
            time++
            val sec = time / 100
            val milli = time % 100
            runOnUiThread{
                binding.secTextView.text = "$sec"
                binding.milliTextView.text = "$milli"
            }
        }
    }
    private fun pause(){
        binding.fab.setImageResource(R.drawable.ic_baseline_play_arrow_24)
        timerTask?.cancel()
    }
    private fun recordLapTime(){
        val lapTime = this.time
        val textView = TextView(this)
        textView.text = "$lap LAB : ${lapTime / 100}.${lapTime % 100}"

        //맨위에 랩타임 추가
        binding.labLayout.addView(textView, 0)
        lap++
    }
    private fun reset(){
        timerTask?.cancel()
        //모든 변수 초기화
        this.time = 0
        this.isRunning = false
        binding.fab.setImageResource(R.drawable.ic_baseline_play_arrow_24)
        binding.secTextView.text = "0"
        binding.milliTextView.text = "00"
        // 모든 랩타임 제거
        binding.labLayout.removeAllViews()
        this.lap = 1
    }
}