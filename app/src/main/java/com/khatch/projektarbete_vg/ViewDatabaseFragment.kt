package com.khatch.projektarbete_vg

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.FrameLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.Navigation
import com.khatch.projektarbete_vg.counter.CounterViewModel
import com.khatch.projektarbete_vg.databinding.FragmentViewDatabaseBinding
import kotlinx.coroutines.launch

public var usersFragmentArrayList : ArrayList<String> = arrayListOf(
    "Matthew",
    "Lukas"
)

class ViewDatabaseFragment : Fragment() {
    private lateinit var bindingViewDatabaseFragment: FragmentViewDatabaseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        bindingViewDatabaseFragment =
            FragmentViewDatabaseBinding.inflate(layoutInflater, container, false)
        val returnedViewViewDatabaseFragment: FrameLayout = bindingViewDatabaseFragment.root

        // ID:s
        val btnViewDatabaseFragmentToResultatFragment: Button = bindingViewDatabaseFragment.btnViewDatabaseFragmentToResultatFragment
        var usersListFragment = bindingViewDatabaseFragment.listViewUsersListFragment
        var tvCounterValueFragment = bindingViewDatabaseFragment.tvCounterValueFragment

        // Logic goes here
        // use ArrayAdapter and define an array
        val arrayAdapter: ArrayAdapter<*>

        // access the listView from xml file
        arrayAdapter = ArrayAdapter(requireContext(),
            android.R.layout.simple_list_item_1,
            usersFragmentArrayList)
        usersListFragment.adapter = arrayAdapter
        arrayAdapter.notifyDataSetChanged()
        //switchDisplayImageViewFragment.setOnClickListener() {}

        // ViewModel
        val counterViewModelFragment by viewModels<CounterViewModel> ()

        // ViewModel Lifecycle
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                counterViewModelFragment.uiState.collect(){
                    tvCounterValueFragment.text = counterViewModelFragment.uiState.value.counterValue.toString()
                    usersFragmentArrayList.add(bindingViewDatabaseFragment.tvCounterValueFragment.text.toString())
                    arrayAdapter.notifyDataSetChanged()
                }
            }
        }



        // OnClick
        btnViewDatabaseFragmentToResultatFragment.setOnClickListener() {
            println(" btnViewDatabaseFragmentToResultatFragment was clicked. ")
            Navigation.findNavController(returnedViewViewDatabaseFragment).navigate(R.id.action_viewDatabaseFragment_to_resultatFragment)
        }
        tvCounterValueFragment.setOnClickListener() {}


        return returnedViewViewDatabaseFragment
    }
}
