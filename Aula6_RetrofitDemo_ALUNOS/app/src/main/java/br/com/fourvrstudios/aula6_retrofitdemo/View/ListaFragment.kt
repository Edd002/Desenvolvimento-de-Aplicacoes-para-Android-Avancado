package br.com.fourvrstudios.aula6_retrofitdemo.View

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import br.com.fourvrstudios.aula6_retrofitdemo.R
import br.com.fourvrstudios.aula6_retrofitdemo.RetrofitViewModel
import br.com.fourvrstudios.aula6_retrofitdemo.databinding.FragmentListaBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

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

        // Qualquer alterações que o response possuir irá atualizar o textView
        viewModel.response.observe(viewLifecycleOwner, Observer { it ->
            it?.forEach {
                var item = "id: ${it.id}\nTitle: ${it.title}\nurl: ${it.url}\n\n";
                binding.txtRelatorio.append(item)
            }
        })

        // Glide (Dependendo do tempo de carregamento pode ocorrer erro na busca da imagem)
        viewModel.response.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                var urlString = it.get(1).url + ".png"
                var uri = urlString.toUri().buildUpon().scheme("https").build()
                Glide.with(binding.imageView).load(uri).apply (
                    RequestOptions().placeholder(R.drawable.loading_animation)
                        .error(R.drawable.ic_broken_image)
                ).into(binding.imageView)
            }
        })

        viewModel.reqResponse.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                var item = "id: ${it.id}\nTitle: ${it.title}\nurl: ${it.url}\n\n";
                binding.txtRelatorio.append(item)
            }
        })

        return binding.root
    }
}