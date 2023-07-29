package com.test.timetofocus_pomodoro

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        return fragmentPomodoroTimerBinding.root
    }
}