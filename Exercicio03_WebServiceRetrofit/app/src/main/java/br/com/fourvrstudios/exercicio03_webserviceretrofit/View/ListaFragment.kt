package br.com.fourvrstudios.exercicio03_webserviceretrofit.View

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import br.com.fourvrstudios.exercicio03_webserviceretrofit.R
import br.com.fourvrstudios.exercicio03_webserviceretrofit.RestApiStatus
import br.com.fourvrstudios.exercicio03_webserviceretrofit.RetrofitViewModel
import br.com.fourvrstudios.exercicio03_webserviceretrofit.databinding.FragmentListaBinding

class ListaFragment : Fragment() {
    private val viewModel: RetrofitViewModel by activityViewModels()
    private lateinit var binding: FragmentListaBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_lista, container, false)

        binding.listaViewModel = viewModel
        binding.lifecycleOwner = this

        var endereco: String? = null

        binding.buttonBuscar.setOnClickListener {
            var CEP : String? = binding.editTextCEP.text.toString().replace("-", "")
            if (!CEP.isNullOrBlank()) {
                viewModel.getByCEP(CEP)
            } else {
                Toast.makeText(context, "CEP no formato inválido para buscar.", Toast.LENGTH_LONG).show()
            }
        }

        viewModel.response.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                endereco = "CEP: ${it.cep}" +
                        "\nLogradouro: ${it.logradouro}" +
                        "\nComplemento: ${it.complemento}" +
                        "\nBairro: ${it.bairro}" +
                        "\nLocalidade: ${it.localidade}" +
                        "\nUF: ${it.uf}" +
                        "\nIBGE: ${it.ibge}" +
                        "\nGIA: ${it.gia}" +
                        "\nDDD: ${it.ddd}" +
                        "\nSIAFI: ${it.siafi}";
            } else {
                endereco = null
            }
        })

        viewModel.status.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                when (it) {
                    RestApiStatus.LOADING -> {
                        binding.txtRelatorio.text = "Carregando endereço..."
                    }
                    RestApiStatus.ERROR -> {
                        binding.txtRelatorio.text = "Error ao buscar o endereço."
                    }
                    RestApiStatus.DONE -> {
                        if (!endereco.isNullOrBlank()) {
                            binding.txtRelatorio.text = endereco
                        } else {
                            binding.txtRelatorio.text = "Error ao buscar o endereço."
                        }
                    }
                }
            }
        })

        return binding.root
    }
}