package com.gosty.tryoutapp.data.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gosty.tryoutapp.R
import com.gosty.tryoutapp.data.models.ScoreModel
import com.gosty.tryoutapp.data.models.SelectionAnswerModel
import com.gosty.tryoutapp.data.models.SelectionModel
import com.gosty.tryoutapp.databinding.ItemAnswerBinding
import com.gosty.tryoutapp.ui.explanation.ExplanationActivity
import com.gosty.tryoutapp.ui.explanation.ExplanationFragment

class RvExplanationAnswerAdapter(
    var selectionAnswer: List<SelectionAnswerModel?>?,
    var option: List<SelectionModel?>,
    var answer: ScoreModel?,
    var totalOption: Int,
    var context: Context,
) : RecyclerView.Adapter<RvExplanationAnswerAdapter.MyViewHolder>() {

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

        val flags = Html.FROM_HTML_MODE_COMPACT or Html.FROM_HTML_MODE_LEGACY

        holder.binding.mvAnswer.text = Html.fromHtml(currentItem?.selectionText, flags, { source ->
            Glide.with(context)
                .load(source.replace(""""""", ""))
                .into(holder.binding.ivAnswer)
            null
        }, null).toString()

        for (i in answer?.answers!!){
            if (i.questionId == currentItem?.questionId){
                for (j in i.answer!!){
                    if (currentItem?.selectionText?.isEmpty() == false){
                        if (j == currentItem.selectionText){
                            for (k in selectionAnswer!!){
                                if (j != k?.selectionText){
                                    holder.binding.answerBox.setBackgroundResource(R.drawable.shape_bg_rounded_corner_answer_red_8_full_radius)
                                } else {
                                    holder.binding.answerBox.setBackgroundResource(R.drawable.shape_bg_rounded_corner_answer_green_8_full_radius)
                                }
                            }
                        }
                    } else {
                        if (j == currentItem?.image){
                            for (k in selectionAnswer!!){
                                if (j != k?.image){
                                    holder.binding.answerBox.setBackgroundResource(R.drawable.shape_bg_rounded_corner_answer_red_8_full_radius)
                                } else {
                                    holder.binding.answerBox.setBackgroundResource(R.drawable.shape_bg_rounded_corner_answer_green_8_full_radius)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}