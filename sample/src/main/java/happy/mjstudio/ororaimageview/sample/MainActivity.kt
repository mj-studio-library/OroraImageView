package happy.mjstudio.ororaimageview.sample

import android.graphics.Color
import android.os.Bundle
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonBlack.setOnClickListener {
            orora.shadowColor = Color.BLACK
        }
        buttonRed.setOnClickListener {
            orora.shadowColor = Color.RED
        }
        buttonBlue.setOnClickListener {
            orora.shadowColor = Color.BLUE
        }
        buttonYellow.setOnClickListener {
            orora.shadowColor = Color.YELLOW
        }

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                orora.blurRadius = p1.toFloat()
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }
        })

        orora.setImageResource(R.drawable.heart)
    }
}
