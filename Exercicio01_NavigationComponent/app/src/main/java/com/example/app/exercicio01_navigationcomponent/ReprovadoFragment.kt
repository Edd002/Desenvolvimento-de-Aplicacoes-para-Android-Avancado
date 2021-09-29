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
import com.example.app.exercicio01_navigationcomponent.databinding.FragmentReprovadoBinding

class ReprovadoFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val biding: FragmentReprovadoBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_reprovado, container, false);

        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            Toast.makeText(context, "Não é possível retornar ao questionário. Conclua para reiniciá-lo.", Toast.LENGTH_LONG).show();
        }

        val args = ReprovadoFragmentArgs.fromBundle(requireArguments());
        biding.textViewFragmentReprovado.text = "Tente novamente. Você acertou ${args.argResultado} das 3 perguntas.";

        biding.buttonReprovadoFragmentConcluir.setOnClickListener() { view: View ->
            view.findNavController().popBackStack();
        }

        return biding.root;
    }
}