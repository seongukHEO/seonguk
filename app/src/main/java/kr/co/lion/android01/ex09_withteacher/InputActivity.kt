package kr.co.lion.android01.ex09_withteacher

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kr.co.lion.android01.ex09_withteacher.databinding.ActivityInputBinding

class InputActivity : AppCompatActivity() {

    lateinit var activityInputBinding: ActivityInputBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityInputBinding = ActivityInputBinding.inflate(layoutInflater)
        setContentView(activityInputBinding.root)

        initData()
        initView()
        setEvent()

    }

    // 초기 데이터 셋팅
    fun initData(){

    }
    // View 초기 셋팅
    fun initView(){

    }
    // 이벤트 설정
    fun setEvent(){
        activityInputBinding.apply {
            //버튼 이벤트
            buttonSubmit.setOnClickListener {
                //입력한 정보를 정의
                var name = inputName.text.toString()
                var grade = inputGrade.text.toString().toInt()
                var kor = inputKor.text.toString().toInt()
                var eng = inputEng.text.toString().toInt()
                var math = inputMath.text.toString().toInt()

                //객체에 담는다
                var info1 = InfoClass(name, grade, kor, eng, math)

                //데이터를 담을 intent
                var resultIntent = Intent()
                //객체를 intent에 저장할 때 writeToParcel메서드가 호출된다
                resultIntent.putExtra("obj1" , info1)

                //결과를 세팅
                setResult(RESULT_OK, resultIntent)

                //현재 엑티비티를 종료한다
                finish()
            }
        }

    }
}