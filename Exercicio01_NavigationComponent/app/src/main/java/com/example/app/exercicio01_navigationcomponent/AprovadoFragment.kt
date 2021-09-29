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
import com.example.app.exercicio01_navigationcomponent.databinding.FragmentAprovadoBinding

class AprovadoFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentAprovadoBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_aprovado, container, false);

        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            Toast.makeText(context, "Não é possível retornar ao questionário. Conclua para reiniciá-lo.", Toast.LENGTH_LONG).show();
        }

        val args = AprovadoFragmentArgs.fromBundle(requireArguments());
        binding.textViewFragmentAprovado.text = "Parabéns! Você acertou ${args.argResultado} das 3 perguntas.";

        binding.buttonAprovadoFragmentConcluir.setOnClickListener() { view: View ->
            view.findNavController().popBackStack();
        }

        return binding.root;
    }
}