package br.com.fourvrstudios.aula4_mvvm_exemplo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import br.com.fourvrstudios.aula4_mvvm_exemplo.databinding.FragmentCheckoutBinding

class CheckoutFragment : Fragment() {
    private lateinit var binding: FragmentCheckoutBinding;

    private val viewModel : ComprasViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_checkout, container, false)

        binding.checkoutViewModel = viewModel

        viewModel.moedas.observe(viewLifecycleOwner, Observer { moedas ->
            binding.txtResumoCheckout.text = "Resumo: Você adquiriu $moedas moedas e ${viewModel.pontos.value} Pontos de Resistência"
        })

        viewModel.pontos.observe(viewLifecycleOwner, Observer { pontos ->
            binding.txtResumoCheckout.text = "Resumo: Você adquiriu ${viewModel.moedas.value} moedas e $pontos Pontos de Resistência"
        })

        viewModel.valorCompra.observe(viewLifecycleOwner, Observer { valCompra ->
            binding.txtValorCheckout.text = String.format("R$%.2f", valCompra)
        })

        binding.btnCancelar.setOnClickListener {
            viewModel.resetCompra()
            findNavController().navigate(CheckoutFragmentDirections.actionCheckoutFragmentToCanceladaFragment())
        }

        binding.btnConfirmar.setOnClickListener {
            findNavController().navigate(CheckoutFragmentDirections.actionCheckoutFragmentToReciboFragment())
        }

        return binding.root
    }
}