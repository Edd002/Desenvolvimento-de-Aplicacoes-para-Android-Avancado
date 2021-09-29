package br.com.fourvrstudios.aula4_mvvm_exemplo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import br.com.fourvrstudios.aula4_mvvm_exemplo.databinding.FragmentReciboBinding

class ReciboFragment : Fragment() {
    private lateinit var binding: FragmentReciboBinding

    private val viewModel : ComprasViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recibo, container, false)

        binding.txtCodigoCompra.text = "Código da Compra: ${viewModel.getCodigoCompra()}"
        binding.txtValorRecibo.text = String.format("Valor da compra: R$%.2f", viewModel.valorCompra.value)

        viewModel.resetCompra()

        binding.btnReciboVoltar.setOnClickListener {
            findNavController().navigate(ReciboFragmentDirections.actionReciboFragmentToItensFragment())
        }

        return binding.root
    }
}