package com.test.timetofocus_pomodoro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.test.timetofocus_pomodoro.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding

    companion object {
        var POMODORO_TIMER_FRAGMENT = "PomodoroTimerFragment"
    }

    // 다이얼로그에서 설정한 시간값을 저장
    var focusMinute: Int = 0
    var focusSecond: Int = 0
    var restMinute: Int = 0
    var restSecond: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)

        replaceFragment(POMODORO_TIMER_FRAGMENT, false, false)

        setContentView(activityMainBinding.root)
    }

    // 지정한 Fragment를 보여주는 메서드
    fun replaceFragment(name: String, addToBackStack: Boolean, animate: Boolean) {
        // Fragment 교체 상태로 설정한다.
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        // 새로운 Fragment를 담을 변수
        var newFragment = when (name) {
            POMODORO_TIMER_FRAGMENT -> PomodoroTimerFragment()
            else -> Fragment()
        }

        if (newFragment != null) {
            // Fragment를 교채한다.
            fragmentTransaction.replace(R.id.mainContainer, newFragment)

            if (animate == true) {
                // 애니메이션을 설정한다.
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            }

            if (addToBackStack == true) {
                // Fragment를 Backstack에 넣어 이전으로 돌아가는 기능이 동작할 수 있도록 한다.
                fragmentTransaction.addToBackStack(name)
            }

            // 교체 명령이 동작하도록 한다.
            fragmentTransaction.commit()
        }
    }

    // Fragment를 BackStack에서 제거한다.
    fun removeFragment(name: String) {
        supportFragmentManager.popBackStack(name, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

}