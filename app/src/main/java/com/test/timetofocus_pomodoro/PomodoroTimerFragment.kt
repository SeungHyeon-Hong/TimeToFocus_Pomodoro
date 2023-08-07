package com.test.timetofocus_pomodoro

import android.content.DialogInterface
import android.content.res.Resources
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.test.timetofocus_pomodoro.databinding.FragmentPomodoroTimerBinding

class PomodoroTimerFragment : Fragment() {

    lateinit var fragmentPomodoroTimerBinding: FragmentPomodoroTimerBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentPomodoroTimerBinding = FragmentPomodoroTimerBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        // resizing components
        resizingComponents()

        // 초기화
        fragmentPomodoroTimerBinding.run {
            layoutPlay.visibility = View.VISIBLE
            layoutRunning.visibility = View.GONE
        }

        // 동작
        fragmentPomodoroTimerBinding.run {
            // 타이머 셋팅 UI 터치
            setTimerLayout.setOnClickListener {
                // 타임피커 다이얼로그
                // TODO()

                // 집중 최소시간 25분, 휴식 최소시간 5분
                setTime(25, 0, 5, 0)
            }

            // Play 터치
            imageViewPlay.setOnClickListener {
                // 시작

                val colorInt = mainActivity.resources.getColor(R.color.blueberry_blue, null)
                imageViewClock.setColorFilter(colorInt)
                textViewTime.setTextColor(colorInt)
                layoutPlay.visibility = View.GONE
                layoutRunning.visibility = View.VISIBLE

                setTimerLayout.isClickable = false

                // 집중, 휴식 시간 분기
                if (true) {
                    val msg = "지금은 <font color=\"#${
                        String.format(
                            "%X",
                            mainActivity.resources.getColor(R.color.raspberry_deep, null)
                        ).substring(2)
                    }\">집중</font> 시간입니다"
                    textViewStateMsg.text = (Html.fromHtml(msg, Html.FROM_HTML_MODE_LEGACY))
                } else {
                    textViewStateMsg.text = "지금은 휴식 시간입니다"
                }
            }

            // Stop 터치
            imageViewStop.setOnClickListener {
                // 그만두기 다이얼로그
                val builder = MaterialAlertDialogBuilder(mainActivity, R.style.DialogTheme).apply {
                    // 아이콘
                    setIcon(R.drawable.warning_fill0_wght300_grad0_opsz48)
                    setTitle(" ") // 빈칸이라도 있어야 아이콘이 표시됩니다.

                    //메시지
                    setMessage("정말로 그만 두시겠습니까?")

                    setNegativeButton("아니요", null)
                    setPositiveButton("네") { dialogInterface: DialogInterface, i: Int ->
                        // main으로 돌아가기
                        val colorInt =
                            mainActivity.resources.getColor(R.color.blueberry_lighter, null)
                        imageViewClock.setColorFilter(colorInt)
                        textViewTime.setTextColor(colorInt)
                        layoutPlay.visibility = View.VISIBLE
                        layoutRunning.visibility = View.GONE

                        setTimerLayout.isClickable = true
                    }
                }
                builder.show()
            }
        }

        return fragmentPomodoroTimerBinding.root
    }

    private fun resizingComponents() {
        // 화면 크기 가져오기
        var screenH = Resources.getSystem().displayMetrics.heightPixels
        // status bar 존재여부 확인 후 높이값 더하기
        val resId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resId > 0) {
            screenH += resources.getDimensionPixelSize(resId)
        }

        // 컴포넌트들의 높이값 셋팅 : 모두 더해서 1이 되어야 함
        val spaceH = (screenH * 0.15f).toInt() // x2
        val layoutTimerH = (screenH * 0.12f).toInt()
        val space1 = (screenH * 0.04f).toInt()
        val layoutMainContentH = (screenH * 0.54f).toInt()

        fragmentPomodoroTimerBinding.run {
            pomodoroTimerSpaceStart.layoutParams.height = spaceH
            setTimerLayout.layoutParams.height = layoutTimerH
            pomodoroTimerSpace1.layoutParams.height = space1
            layoutMainContent.layoutParams.height = layoutMainContentH
            pomodoroTimerSpaceFinal.layoutParams.height = spaceH
        }
    }

    private fun setTime(focusMinute: Int, focusSecond: Int, restMinute: Int, restSecond: Int) {
        // 시간값 저장
        mainActivity.focusMinute = focusMinute
        mainActivity.focusSecond = focusSecond
        mainActivity.restMinute = restMinute
        mainActivity.restSecond = restSecond

        // 문자열 만들기
        var timeText = ""
        fun addDigit(digit: Int) {
            if (digit == 0) timeText += "0"
            else if (digit < 10) timeText += "  "
            timeText += digit.toString()
        }
        timeText = "집중 "
        addDigit(focusMinute)
        timeText += ":"
        addDigit(focusSecond)
        timeText += "\n휴식 "
        addDigit(restMinute)
        timeText += ":"
        addDigit(restSecond)

        // 설정한 시간값 표시
        fragmentPomodoroTimerBinding.textViewTime.text = timeText
    }

}