package br.com.fourvrstudios.aula4_mvvm_exemplo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import br.com.fourvrstudios.aula4_mvvm_exemplo.databinding.FragmentItensBinding

class ItensFragment : Fragment() {
    private lateinit var binding: FragmentItensBinding

    // Criação da ViewModel em nível de activity para que possa ser utilizado por todos os fragments
    private val activityViewModel : ComprasViewModel by activityViewModels();

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_itens, container, false)

        binding.itensViewModel = activityViewModel
        binding.lifecycleOwner = activity

        binding.btnCheckout.setOnClickListener {
            findNavController().navigate(ItensFragmentDirections.actionItensFragmentToCheckoutFragment())
        }

        return binding.root
    }
}