package com.khatch.projektarbete_vg

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.khatch.projektarbete_vg.databinding.FragmentResultatBinding




class ResultatFragment : Fragment() {
    private lateinit var binding: FragmentResultatBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentResultatBinding.inflate(layoutInflater, container, false)
        var view = binding.root

        // ID:s

        // Logic goes here

        // access the ListView from xml-file

        // ViewModel

        // ViewModel Lifecycle

        // OnClick Snack-bar Fragment

        // OnClick ViewModel Value btnAddTomatoFragment

        // OnClick

        return view

    }
}
