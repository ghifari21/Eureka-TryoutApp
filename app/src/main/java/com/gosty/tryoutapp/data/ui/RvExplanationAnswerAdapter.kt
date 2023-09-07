package com.gosty.tryoutapp.data.ui

import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gosty.tryoutapp.R
import com.gosty.tryoutapp.data.models.SelectionModel
import com.gosty.tryoutapp.databinding.ItemAnswerBinding

class RvExplanationAnswerAdapter(
    var selectionId: Int,
    var option: List<SelectionModel?>, var totalOption: Int) : RecyclerView.Adapter<RvExplanationAnswerAdapter.MyViewHolder>() {

    inner class MyViewHolder(val binding : ItemAnswerBinding, val context: Context) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemAnswerBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MyViewHolder(binding, parent.context)
    }

    override fun getItemCount(): Int = totalOption

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = option[position]
        holder.binding.mvAnswer.text = currentItem?.selectionText
        if (currentItem?.isAnswered!!){
            holder.binding.answerBox.background = Resources.getSystem().getDrawable(R.drawable.shape_bg_rounded_corner_answer_green_8_full_radius,null)
            if (selectionId != currentItem?.idSelection){
                holder.binding.answerBox.background = Resources.getSystem().getDrawable(R.drawable.shape_bg_rounded_corner_answer_red_8_full_radius,null)
            }
        }
    }
}