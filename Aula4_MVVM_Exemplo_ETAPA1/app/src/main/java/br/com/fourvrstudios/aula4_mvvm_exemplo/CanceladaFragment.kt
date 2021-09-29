package br.com.fourvrstudios.aula4_mvvm_exemplo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import br.com.fourvrstudios.aula4_mvvm_exemplo.databinding.FragmentCanceladaBinding

class CanceladaFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var binding: FragmentCanceladaBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_cancelada, container, false)

        binding.btnCanceladaVoltar.setOnClickListener {
            findNavController().navigate(CanceladaFragmentDirections.actionCanceladaFragmentToItensFragment())
        }

        return binding.root
    }
}