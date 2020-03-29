package com.papageorgiouk.curfnsurf.ui.form.purpose

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.papageorgiouk.curfnsurf.R
import com.papageorgiouk.curfnsurf.data.Purpose
import kotlinx.android.synthetic.main.purpose_fragment.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

class PurposeFragment(val onNext: (() -> Unit)) : Fragment(R.layout.purpose_fragment) {

    private val viewModel: PurposeViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.purposesFlow.collect {
                recycler_purposes.apply {
                    adapter = PurposesAdapter(it) { onPurposeClicked(it) }
                    layoutManager = LinearLayoutManager(this@PurposeFragment.context, RecyclerView.VERTICAL, false)
                    addItemDecoration(DividerItemDecoration(this@PurposeFragment.context, LinearLayoutManager.VERTICAL).apply {
                        setDrawable(resources.getDrawable(R.drawable.divider))
                    })
                }
            }
        }
    }

    private fun onPurposeClicked(purpose: Purpose) {
        viewModel.onPurposeClicked(purpose)
        onNext.invoke()
    }
}

class PurposesAdapter(private val purposes: List<Purpose>, val purposeClick: ((purpose: Purpose)-> Unit)) : RecyclerView.Adapter<PurposesAdapter.PurposeVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PurposeVH {
        return LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_purpose, parent, false)
            .let { PurposeVH(it) }
    }

    override fun getItemCount(): Int = purposes.size

    override fun onBindViewHolder(holder: PurposeVH, position: Int) {
        holder.bindPurpose(purposes[position])
    }

    inner class PurposeVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val txtPurpose = itemView.findViewById<TextView>(R.id.txt_purpose)
        private val txtIndex = itemView.findViewById<TextView>(R.id.txt_index)
        private val container = itemView.findViewById<ViewGroup>(R.id.container)

        fun bindPurpose(purpose: Purpose) {
            txtIndex.text = purpose.number.toString()
            txtPurpose.text = purpose.purpose
            container.setOnClickListener { purposeClick.invoke(purpose) }
        }

    }
}
