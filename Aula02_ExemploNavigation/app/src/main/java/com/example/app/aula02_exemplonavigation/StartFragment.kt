package com.example.app.aula02_exemplonavigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.app.aula02_exemplonavigation.databinding.FragmentStartBinding

class StartFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val biding: FragmentStartBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_start, container, false);

        biding.buttonStartFragmentProximo.setOnClickListener() { view: View ->
            if (biding.editTextNomeStartFragment.text.toString() != "") {
                var texto = biding.editTextNomeStartFragment.text.toString();
                view.findNavController().navigate(StartFragmentDirections.actionStartFragmentToPrimeiroFragment(texto));
            }
        }

        return biding.root;
    }
}