package com.example.app.aula02_exemplonavigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.app.aula02_exemplonavigation.databinding.FragmentSegundoBinding

class SegundoFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val biding: FragmentSegundoBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_segundo, container, false);
        return biding.root;
    }
}