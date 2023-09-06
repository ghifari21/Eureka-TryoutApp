package com.gosty.tryoutapp.data.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gosty.tryoutapp.R
import com.gosty.tryoutapp.data.models.SelectionModel
import com.gosty.tryoutapp.databinding.ItemMultipleChoiceBinding

class RvListMultipleChoiceAdapter :
    ListAdapter<SelectionModel, RvListMultipleChoiceAdapter.MultipleChoiceViewHolder>(DIFF_CALLBACK) {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MultipleChoiceViewHolder {
        val binding =
            ItemMultipleChoiceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MultipleChoiceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MultipleChoiceViewHolder, position: Int) {
        val selection = getItem(position)
        holder.bind(selection)
        holder.binding.root.setOnClickListener {
            onItemClickCallback.onItemClicked(selection)
        }
    }

    class MultipleChoiceViewHolder(val binding: ItemMultipleChoiceBinding) :
        RecyclerView.ViewHolder(
            binding.root
        ) {
        fun bind(selection: SelectionModel) {
            binding.mvAnswer.text = selection.selectionText
            if (selection.isAnswered) {
                binding.container.setBackgroundResource(R.color.green_29823B)
            } else {
                binding.container.setBackgroundResource(R.color.gray_F1F0F4)
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(selection: SelectionModel)
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<SelectionModel> =
            object : DiffUtil.ItemCallback<SelectionModel>() {
                override fun areItemsTheSame(
                    oldItem: SelectionModel,
                    newItem: SelectionModel
                ): Boolean {
                    return oldItem.idSelection == newItem.idSelection
                }

                override fun areContentsTheSame(
                    oldItem: SelectionModel,
                    newItem: SelectionModel
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}