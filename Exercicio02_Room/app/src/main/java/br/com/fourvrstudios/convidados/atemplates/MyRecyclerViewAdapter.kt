package br.com.fourvrstudios.convidados.atemplates

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.fourvrstudios.convidados.db.Convidado
import br.com.fourvrstudios.convidados.R
import br.com.fourvrstudios.convidados.databinding.ListItemBinding

class MyRecyclerViewAdapter(private val listaConvidados: List<Convidado>, private val clickListener: (Convidado)->Unit) :
    RecyclerView.Adapter<MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context);
        val binding: ListItemBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.list_item, parent, false);
        return MyViewHolder(binding);
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(listaConvidados[position], clickListener);
    }

    override fun getItemCount(): Int {
        return listaConvidados.size;
    }
}

class MyViewHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(subscriber: Convidado, clickListener: (Convidado) -> Unit) {
        binding.nameTextView.text = subscriber.name;
        binding.emailTextView.text = subscriber.email;
        binding.listItemLayout.setOnClickListener {
            clickListener(subscriber);
        }
    }
}