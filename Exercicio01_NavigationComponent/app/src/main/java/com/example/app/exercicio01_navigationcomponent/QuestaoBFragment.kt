package com.example.app.exercicio01_navigationcomponent

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.app.exercicio01_navigationcomponent.databinding.FragmentQuestaoBBinding

class QuestaoBFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val biding: FragmentQuestaoBBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_questao_b, container, false);

        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            Toast.makeText(context, "Não é possível retornar à questão anterior.", Toast.LENGTH_LONG).show();
        }

        biding.buttonQuestaoBFragmentConfirmar.setOnClickListener() { view: View ->
            val opcaoSelecionada = biding.radioGroupSelecionarQuestaoBFragment.checkedRadioButtonId;
            var resultado = QuestaoBFragmentArgs.fromBundle(requireArguments()).argResultado;

            if (opcaoSelecionada != -1) {
                if (opcaoSelecionada == R.id.radioButtonQuestaoBResposta3) {
                    resultado++;
                }
                view.findNavController().navigate(QuestaoBFragmentDirections.actionQuestaoBFragmentToQuestaoCFragment(resultado));
            } else {
                Toast.makeText(context, "Selecione uma opção", Toast.LENGTH_SHORT).show();
            }
        }

        return biding.root;
    }
}