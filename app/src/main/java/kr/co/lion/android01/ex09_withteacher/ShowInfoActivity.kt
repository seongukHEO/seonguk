package kr.co.lion.android01.ex09_withteacher

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kr.co.lion.android01.ex09_withteacher.databinding.ActivityShowInfoBinding

class ShowInfoActivity : AppCompatActivity() {

    lateinit var activityShowInfoBinding: ActivityShowInfoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityShowInfoBinding = ActivityShowInfoBinding.inflate(layoutInflater)
        setContentView(activityShowInfoBinding.root)
        initData()
        initView()
        setEvent()

    }
    // 초기 데이터 셋팅
    fun initData(){

    }
    // View 초기 셋팅
    fun initView(){
        activityShowInfoBinding.apply {
            textViewShowInfo.apply {


                //intent로 부터 객체 추출
                var info1 = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
                    intent?.getParcelableExtra("obj1", InfoClass::class.java)
                }else{
                    intent?.getParcelableExtra<InfoClass>("obj")
                }




                text = "이름 : ${info1?.name}\n"
                append("학년 : ${info1?.grade}학년\n")
                append("\n")
                append("국어 점수 : ${info1?.kor}점\n")
                append("영어 점수 : ${info1?.eng}점\n")
                append("수학 점수 : ${info1?.math}점\n")
                append("\n")
                var total = info1!!.kor + info1!!.eng + info1!!.math
                append("총점 : ${total}점\n")
                var avg = total / 3
                append("평균 : ${avg}점")
            }
        }

    }
    // 이벤트 설정
    fun setEvent(){
        activityShowInfoBinding.apply {
            buttonOK2.setOnClickListener {
                finish()
            }
        }

    }
}