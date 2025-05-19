package com.example.registro.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.registro.R
import com.example.registro.data.model.Registro
import com.example.registro.databinding.ItemRegistroBinding
import java.text.SimpleDateFormat
import java.util.Locale

class RegistroAdapter : ListAdapter<Registro, RegistroAdapter.RegistroViewHolder>(RegistroDiffCallback()) {

    private val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegistroViewHolder {
        val binding = ItemRegistroBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RegistroViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RegistroViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class RegistroViewHolder(private val binding: ItemRegistroBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(registro: Registro) {
            binding.apply {
                tipoGastoTextView.text = when (registro.tipoGasto) {
                    Registro.TipoGasto.AGUA -> root.context.getString(R.string.agua)
                    Registro.TipoGasto.LUZ -> root.context.getString(R.string.luz)
                    Registro.TipoGasto.GAS -> root.context.getString(R.string.gas)
                }
                valorMedidorTextView.text = String.format("%.2f", registro.valorMedidor)
                fechaTextView.text = dateFormatter.format(registro.fecha)

                iconoTipoGasto.setImageResource(
                    when (registro.tipoGasto) {
                        Registro.TipoGasto.AGUA -> R.drawable.ic_agua
                        Registro.TipoGasto.LUZ -> R.drawable.ic_luz
                        Registro.TipoGasto.GAS -> R.drawable.ic_gas
                    }
                )
            }
        }
    }

    class RegistroDiffCallback : DiffUtil.ItemCallback<Registro>() {
        override fun areItemsTheSame(oldItem: Registro, newItem: Registro): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Registro, newItem: Registro): Boolean {
            return oldItem == newItem
        }
    }
}