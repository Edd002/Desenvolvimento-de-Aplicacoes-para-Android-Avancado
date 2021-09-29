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
import com.example.app.exercicio01_navigationcomponent.databinding.FragmentQuestaoABinding

class QuestaoAFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val biding: FragmentQuestaoABinding = DataBindingUtil.inflate(inflater, R.layout.fragment_questao_a, container, false);

        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            Toast.makeText(context, "Não é possível retornar à tela anterior.", Toast.LENGTH_LONG).show();
        }

        biding.buttonQuestaoAFragmentConfirmar.setOnClickListener() { view: View ->
            val opcaoSelecionada = biding.radioGroupSelecionarQuestaoAFragment.checkedRadioButtonId;
            var resultado = 0;

            if (opcaoSelecionada != -1) {
                if (opcaoSelecionada == R.id.radioButtonQuestaoAResposta2) {
                    resultado++;
                }
                view.findNavController().navigate(QuestaoAFragmentDirections.actionQuestaoAFragmentToQuestaoBFragment(resultado));
            } else {
                Toast.makeText(context, "Selecione uma opção", Toast.LENGTH_SHORT).show();
            }
        }

        return biding.root;
    }
}