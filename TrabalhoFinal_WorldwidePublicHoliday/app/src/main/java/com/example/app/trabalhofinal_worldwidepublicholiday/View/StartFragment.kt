package com.example.app.trabalhofinal_worldwidepublicholiday.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.app.trabalhofinal_worldwidepublicholiday.R
import com.example.app.trabalhofinal_worldwidepublicholiday.databinding.FragmentStartBinding

class StartFragment : Fragment() {
    private lateinit var binding: FragmentStartBinding;

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_start, container, false);
        binding.lifecycleOwner = activity;

        binding.buttonStart.setOnClickListener {
            findNavController().navigate(R.id.action_startFragment_to_yearCountryFragment);
        }

        return binding.root;
    }
}