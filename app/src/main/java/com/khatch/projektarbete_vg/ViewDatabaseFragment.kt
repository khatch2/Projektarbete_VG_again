package com.khatch.projektarbete_vg

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import androidx.navigation.Navigation
import com.khatch.projektarbete_vg.databinding.FragmentViewDatabaseBinding

class ViewDatabaseFragment : Fragment() {
    private lateinit var bindingViewDatabaseFragment: FragmentViewDatabaseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

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

        // OnClick
        btnViewDatabaseFragmentToResultatFragment.setOnClickListener() {
            println(" btnViewDatabaseFragmentToResultatFragment was clicked. ")
            Navigation.findNavController(returnedViewViewDatabaseFragment).navigate(R.id.action_viewDatabaseFragment_to_resultatFragment)
        }


        return returnedViewViewDatabaseFragment
    }
}
