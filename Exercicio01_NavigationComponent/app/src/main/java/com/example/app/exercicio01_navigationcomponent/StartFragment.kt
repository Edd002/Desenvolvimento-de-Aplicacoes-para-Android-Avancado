package com.example.app.exercicio01_navigationcomponent

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.app.exercicio01_navigationcomponent.databinding.FragmentStartBinding

class StartFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val biding: FragmentStartBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_start, container, false);

        biding.buttonStartFragmentIniciarQuiz.setOnClickListener() { view: View ->
            view.findNavController().navigate(StartFragmentDirections.actionStartFragmentToQuestaoAFragment());
        }

        return biding.root;
    }
}