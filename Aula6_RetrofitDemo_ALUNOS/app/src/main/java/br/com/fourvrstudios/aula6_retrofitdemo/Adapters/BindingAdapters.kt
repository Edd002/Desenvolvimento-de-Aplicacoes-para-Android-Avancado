package br.com.fourvrstudios.aula6_retrofitdemo

import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

/*
@BindingAdapter("imageUrl") // Procedimento será executado quando o XML possuir atributo imageUrl
fun bindImage(imageView: ImageView, imgUrl: String?) {
    imgUrl?.let { // manitular null com o let
        var mystr = it + ".png" // Solução específica para esta - Na maioria dos casos, a extensão já vem na url
        var imgUrl = mystr.toUri().buildUpon().scheme("https").build()
        Log.i("IMGURL" , imgUrl.toString())
        Glide.with(imageView.context).load(imgUrl)
            .apply(RequestOptions().placeholder(R.drawable.loading_animation).error(R.drawable.ic_broken_image))
            .into(imageView)
    }
}

@BindingAdapter("apiStatus")
fun bindStatus(statusImageView: ImageView, status: RestApiStatus) { // o Binding Adapter tem 2 argumentos: I tipo da view, e o valor proveniente do viewModel
    when(status) {
        RestApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        RestApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }

        RestApiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
    }
}
*/
