package kr.co.lion.android01.ex09_withteacher

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kr.co.lion.android01.ex09_withteacher.databinding.ActivityRePortBinding

class RePortActivity : AppCompatActivity() {

    lateinit var activityRePortBinding: ActivityRePortBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityRePortBinding = ActivityRePortBinding.inflate(layoutInflater)
        setContentView(activityRePortBinding.root)

        initData()
        initView()
        setEvent()


    }

    // 초기 데이터 셋팅
    fun initData(){

    }
    // View 초기 셋팅
    fun initView(){
        activityRePortBinding.apply {
            textViewReport.apply {

                //Activity가 실행되었을 때 사용할 Intent로 부터 데이터들을 추출한다
                var chk1 = intent?.getBooleanExtra("chk1", false)
                val korTotal = intent?.getIntExtra("korTotal", 0)
                val engTotal = intent?.getIntExtra("engTotal", 0)
                val mathTotal = intent?.getIntExtra("mathTotal", 0)
                val korAvg = intent?.getIntExtra("korAvg", 0)
                val engAvg = intent?.getIntExtra("engAvg", 0)
                val mathAvg = intent?.getIntExtra("mathAvg", 0)
                val allTotal = intent?.getIntExtra("allTotal", 0)
                val allAvg = intent?.getIntExtra("allAvg", 0)

                if (chk1 == false){
                    text = "등록된 학생의 정보가 없습니다"
                }else {

                    text = "국어 총점 : $korTotal\n"
                    append("국어 평균 : $korAvg\n")
                    append("영어 총점 : $engTotal\n")
                    append("영어 평균 : $engAvg\n")
                    append("수학 총점 : $mathTotal\n")
                    append("수학 평균 : $mathAvg\n")
                    append("\n")
                    append("전체 총점 : $allTotal\n")
                    append("전체 평균 : $allAvg\n")
                }

            }
        }

    }
    // 이벤트 설정
    fun setEvent(){
        activityRePortBinding.apply {
            //확인 버튼을 눌렀을 때
            buttonOK.setOnClickListener {
                finish()
            }
        }

    }
}