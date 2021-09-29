package br.com.fourvrstudios.convidados.view

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import br.com.fourvrstudios.convidados.MyViewModel
import br.com.fourvrstudios.convidados.R
import br.com.fourvrstudios.convidados.ViewModelFactory
import br.com.fourvrstudios.convidados.databinding.FragmentCrudBinding
import br.com.fourvrstudios.convidados.db.MyDatabase
import br.com.fourvrstudios.convidados.db.Repositorio

class CrudFragment : Fragment() {
    private lateinit var binding: FragmentCrudBinding;
    lateinit var viewModel: MyViewModel;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_crud, container, false);

        val dao = MyDatabase.getInstance(requireContext()).convidadoDAO;
        val repositorio = Repositorio(dao);
        val factory = ViewModelFactory(repositorio);
        viewModel = ViewModelProvider(requireActivity(), factory).get(MyViewModel::class.java);

        binding.viewModelTag = viewModel; // Vincula o ViewModel a tag <data>
        binding.lifecycleOwner = activity;

        binding.btnVerLista.setOnClickListener {
            findNavController().navigate(R.id.action_crudFragment_to_listaFragment);
        }

        displayConvidados();
        return binding.root;
    }

    // Observer de LOG de alterações de convidados
    private fun displayConvidados() {
        viewModel.convidados.observe(viewLifecycleOwner, Observer {
            Log.i("MYTAG", it.toString());
            //Toast.makeText(context, it.toString(), Toast.LENGTH_SHORT).show();
        });

        viewModel.onClear.observe(viewLifecycleOwner, Observer {
           if (it == true) {
               alertaLimparLista();
           }
        });
    }

    // Solicitar confirmação de usuário para excluir tudo
    fun alertaLimparLista() {
        val alert = AlertDialog.Builder(context);
        alert.setTitle("Tem certeza que deseja limpar a lista de convidados?");
        alert.setMessage("Toda a lista de convidados será perdida.");
        alert.setCancelable(false);
        alert.setPositiveButton(
            "Confirmar"
        ) { dialog, which ->
            viewModel.clearAll();
            Toast.makeText(context, "Todos os convidados foram removidos.", Toast.LENGTH_LONG).show();
            viewModel.setOnClearState(false);
        }
        alert.setNegativeButton(
            "Cancelar"
        ) { dialog, which ->
            viewModel.setOnClearState(false);
        }
        val alertDialog = alert.create();
        alertDialog.show();
    }
}