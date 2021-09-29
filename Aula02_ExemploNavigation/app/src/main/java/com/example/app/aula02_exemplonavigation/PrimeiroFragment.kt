package com.example.app.aula02_exemplonavigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.app.aula02_exemplonavigation.databinding.FragmentPrimeiroBinding

class PrimeiroFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentPrimeiroBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_primeiro, container, false);

        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            Toast.makeText(context, "Não é possível retornar à tela anterior.", Toast.LENGTH_LONG).show();
        }

        val args = PrimeiroFragmentArgs.fromBundle(requireArguments());
        binding.textViewNomePrimeiroFragment.text = "Selecione o próximo fragmento ${args.argNome}";

        binding.buttonPrimeiroFragmentProximo.setOnClickListener() { view: View ->
            var opcaoSelecionada = binding.radioGroupSelecionarSegundoTerceiroFragment.checkedRadioButtonId;
            if (opcaoSelecionada != -1) {
                if (opcaoSelecionada == R.id.radioButtonIrSegundoFragment) {
                    view.findNavController().navigate(PrimeiroFragmentDirections.actionPrimeiroFragmentToSegundoFragment());
                } else if (opcaoSelecionada == R.id.radioButtonIrTerceiroFragment) {
                    view.findNavController().navigate(PrimeiroFragmentDirections.actionPrimeiroFragmentToTerceiroFragment());
                }
            } else {
                Toast.makeText(context, "Selecione uma opção", Toast.LENGTH_SHORT).show();
            }
        }

        return binding.root;
    }
}