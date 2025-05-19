package com.example.registro.registro.ui
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.registro.R
import com.example.registro.adapter.RegistroAdapter
import com.example.registro.databinding.FragmentListaRegistrosBinding
import com.example.registro.data.AppDatabase
import com.example.registro.data.repository.RegistroRepository

class ListaRegistrosFragment : Fragment() {

    private var _binding: FragmentListaRegistrosBinding? = null
    private val binding get() = _binding!!
    private val viewModel: RegistroViewModel by activityViewModels {
        val database = AppDatabase.getDatabase(requireContext())
        RegistroViewModel.RegistroViewModelFactory(RegistroRepository(database.registroDao()))
    }
    private lateinit var adapter: RegistroAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListaRegistrosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = RegistroAdapter()
        binding.recyclerViewRegistros.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewRegistros.adapter = adapter

        viewModel.allRegistros.observe(viewLifecycleOwner) { registros ->
            adapter.submitList(registros)
            binding.emptyTextView.visibility = if (registros.isEmpty()) View.VISIBLE else View.GONE
        }

        binding.fabNuevoRegistro.setOnClickListener {
            findNavController().navigate(R.id.action_listaRegistrosFragment_to_nuevoRegistroFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
