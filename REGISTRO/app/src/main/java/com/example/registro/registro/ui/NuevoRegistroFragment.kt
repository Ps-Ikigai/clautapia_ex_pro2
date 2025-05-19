package com.example.registro.registro.ui

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.registro.R
import com.example.registro.data.AppDatabase
import com.example.registro.data.model.Registro
import com.example.registro.data.repository.RegistroRepository
import com.example.registro.databinding.FragmentNuevoRegistroBinding
import java.util.Calendar
import java.util.Date
import java.text.SimpleDateFormat
import java.util.Locale

class NuevoRegistroFragment : Fragment() {

    private var _binding: FragmentNuevoRegistroBinding? = null
    private val binding get() = _binding!!
    private val viewModel: RegistroViewModel by activityViewModels {
        val database = AppDatabase.getDatabase(requireContext())
        RegistroViewModel.RegistroViewModelFactory(RegistroRepository(database.registroDao()))
    }
    private var selectedDate: Date = Calendar.getInstance().time
    private val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNuevoRegistroBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tiposGasto = arrayOf(getString(R.string.agua), getString(R.string.luz), getString(R.string.gas))
        val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, tiposGasto)
        binding.tipoGastoAutoCompleteTextView.setAdapter(adapter)

        binding.fechaEditText.setText(dateFormatter.format(selectedDate))
        binding.fechaEditText.setOnClickListener {
            showDatePickerDialog()
        }

        binding.btnGuardarRegistro.setOnClickListener {
            val tipoGastoString = binding.tipoGastoAutoCompleteTextView.text.toString()
            val valorString = binding.valorMedidorEditText.text.toString()

            if (tipoGastoString.isNotEmpty() && valorString.isNotEmpty()) {
                val tipoGasto = when (tipoGastoString) {
                    getString(R.string.agua) -> Registro.TipoGasto.AGUA
                    getString(R.string.luz) -> Registro.TipoGasto.LUZ
                    getString(R.string.gas) -> Registro.TipoGasto.GAS
                    else -> null
                }
                val valor = valorString.toDoubleOrNull()

                if (tipoGasto != null && valor != null) {
                    viewModel.insertarRegistro(tipoGasto, valor, selectedDate)
                    findNavController().navigate(R.id.action_nuevoRegistroFragment_to_listaRegistrosFragment)
                } else {
                    Toast.makeText(requireContext(), "Ingrese un valor de medidor válido.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "Por favor, complete todos los campos.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, yearSelected, monthOfYear, dayOfMonth ->
                val calendarSelected = Calendar.getInstance()
                calendarSelected.set(yearSelected, monthOfYear, dayOfMonth)
                selectedDate = calendarSelected.time
                binding.fechaEditText.setText(dateFormatter.format(selectedDate))
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

class DropdownItemViewHolder(view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {
    val textView = view.findViewById<android.widget.TextView>(android.R.id.text1)
}
