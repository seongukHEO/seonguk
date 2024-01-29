package kr.co.lion.android01.ex09_withteacher

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.divider.MaterialDividerItemDecoration
import kr.co.lion.android01.ex09_withteacher.databinding.ActivityMainBinding
import kr.co.lion.android01.ex09_withteacher.databinding.RowMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding

    //InputActivity 실행을 위한 런처

    lateinit var inputActivityLauncher:ActivityResultLauncher<Intent>

    //ReportActivity 실행을 위한 런처
    lateinit var reportActivityLauncher:ActivityResultLauncher<Intent>

    // ShowInfoActivity 실행을 위한 런처
    lateinit var showInfoActivityLauncher:ActivityResultLauncher<Intent>

    //학생정보를 담을 리스트
    //학생의 모든 정보가 담겨있다
    var studentList = mutableListOf<InfoClass>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        ininData()
        initView()
        initsetEvent()

    }

    //초기 데이터 세팅
    fun ininData(){
        //inputActivity 등록
        var contract1 = ActivityResultContracts.StartActivityForResult()
        inputActivityLauncher = registerForActivityResult(contract1){
            if(it.resultCode == RESULT_OK){
                if(it.data != null){
                    // 객체를 추출한다.
                    // createFromParcel 메서드가 호출되고 반환하는 객체를 반환해준다.
                    //버전별로 나누기...?
                    val info1 = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
                        it.data!!.getParcelableExtra("obj1", InfoClass::class.java)
                    } else {
                        it.data!!.getParcelableExtra<InfoClass>("obj1")
                    }

                    //Log.d("test1234", "${info1?.name}")

                    //리스트에 객체를 담는다
                    studentList.add(info1!!)
                    //리싸이클러 뷰를 갱신
                    activityMainBinding.recyclerViewitem.adapter?.notifyDataSetChanged()
                }
            }

        }
        // ReportActivity 등록
        var contract2 = ActivityResultContracts.StartActivityForResult()
        reportActivityLauncher = registerForActivityResult(contract2){


        }

        //ShowInfoActivity등록
        var contract3 = ActivityResultContracts.StartActivityForResult()
        showInfoActivityLauncher = registerForActivityResult(contract3){

        }

    }
    //view 초기 세팅
    fun initView(){
        activityMainBinding.apply {
            recyclerViewitem.apply {
                //어뎁터 설정
                adapter = ReCyclerViewAdapter()
                //layoutManager 설정
                layoutManager = LinearLayoutManager(this@MainActivity)
                //데코
                var dec = MaterialDividerItemDecoration(this@MainActivity, MaterialDividerItemDecoration.VERTICAL)
                addItemDecoration(dec)
            }


        }

    }
    fun initsetEvent(){
        //학생정보 입력 버튼 이벤트
        activityMainBinding.apply {
            buttonInputInfo.setOnClickListener {
                var newIntent = Intent(this@MainActivity , InputActivity::class.java)
                inputActivityLauncher.launch(newIntent)
            }
            //총점 및 평균 버튼 이벤트
            buttonShowReport.setOnClickListener {
                var inputIntent = Intent(this@MainActivity, RePortActivity::class.java)
                //학생 정보가 저장되어 있는지 여부를 담아준다
                var chk1 = if (studentList.size == 0){
                    false
                }else{
                    true
                }
                inputIntent.putExtra("chk1", chk1)
                //담긴 학생 정보가 없다면 각 값을 구해 담아준다
                if (chk1 == true){
                    var korTotal = 0
                    var engTotal = 0
                    var mathTotal = 0

                    studentList.forEach {
                        korTotal += it.kor
                        engTotal += it.eng
                        mathTotal += it.math
                    }
                    var korAvg = korTotal / studentList.size
                    var engAvg = engTotal / studentList.size
                    var mathAvg = mathTotal / studentList.size

                    var allTotal = korTotal + engTotal + mathTotal
                    var allAvg = (allTotal / studentList.size) / 3

                    inputIntent.putExtra("korTotal", korTotal)
                    inputIntent.putExtra("endTotal", engTotal)
                    inputIntent.putExtra("mathTotal", mathTotal)
                    inputIntent.putExtra("korAvg", korAvg)
                    inputIntent.putExtra("engAvg", engAvg)
                    inputIntent.putExtra("mathAvg", mathAvg)
                    inputIntent.putExtra("allTotal", allTotal)
                    inputIntent.putExtra("allAvg", allAvg)
                }

                reportActivityLauncher.launch(inputIntent)

            }
        }
    }




    //ReCyclerView의 어댑터
    inner class ReCyclerViewAdapter: RecyclerView.Adapter<ReCyclerViewAdapter.ViewHolderClass>(){

        //viewHolder 클래스 생성
        inner class ViewHolderClass(rowMainBinding: RowMainBinding):RecyclerView.ViewHolder(rowMainBinding.root){
            var rowMainBinding:RowMainBinding

            init {
                this.rowMainBinding = rowMainBinding

                // 항목 뷰의 가로 세로 길이 셋팅
                this.rowMainBinding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )

                // 항목을 터치했을 때의 이벤트
                //ReCyclerView의 한 칸을 클릭했을 때 실행되기 때문에 여기에 있다
                this.rowMainBinding.root.setOnClickListener {
                    // ShowInfoActivity를 실행한다.
                    val newIntent = Intent(this@MainActivity, ShowInfoActivity::class.java)

                    //선택한 항목 번째의 학생객체를 Intent에 담아준다
                    newIntent.putExtra("obj1", studentList[adapterPosition])

                    showInfoActivityLauncher.launch(newIntent)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
            //viewBinding
            var rowMainBinding = RowMainBinding.inflate(layoutInflater)
            //viewHolder
            var viewHolderClass = ViewHolderClass(rowMainBinding)
            return viewHolderClass

        }

        override fun getItemCount(): Int {
            return studentList.size
        }

        override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
            holder.rowMainBinding.textViewRowMainName.text = studentList[position].name
            holder.rowMainBinding.textViewRowMainGrade.text = "${studentList[position].grade}학년"
        }
    }
}