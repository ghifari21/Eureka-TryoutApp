package com.gosty.tryoutapp.data.ui

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gosty.tryoutapp.data.models.ScoreModel
import com.gosty.tryoutapp.databinding.ItemScoreBinding
import com.gosty.tryoutapp.ui.explanation.ExplanationActivity
import com.gosty.tryoutapp.utils.Converter

class   ScoreRecyclerViewAdapter(private var myScoreList : List<ScoreModel>) : RecyclerView.Adapter<ScoreRecyclerViewAdapter.MyViewHolder>() {
    inner class  MyViewHolder(val binding : ItemScoreBinding, val context : Context) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemScoreBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding,parent.context)
    }

    override fun getItemCount(): Int {
        return myScoreList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = myScoreList[position]

        if (position == 0){
            holder.binding.tvTestKe.text = "Test Terakhir"
        } else if (position == myScoreList.size - 1){
            holder.binding.tvTestKe.text = "Test Pertama"
        } else {
            holder.binding.tvTestKe.text = "Test Ke-${myScoreList.size - position}"
        }

        holder.binding.tvDurasi.text = Converter.millisecondToMinuteAndSecond(currentItem.totalTime!!)
        holder.binding.tvBenar.text = "Benar : ${currentItem.correctAnswer}"
        holder.binding.tvSalah.text = "Salah : ${currentItem.wrongAnswer}"
        holder.binding.tvKosong.text = "Kosong : ${currentItem.notAnswered}"
        holder.binding.tvNilai.text = currentItem.score.toString()
        holder.binding.tvTanggalPengerjaan.text = "Finished ${Converter.dateTime(currentItem.dateTime!!)}"
        holder.binding.tvTryoutCategory.text = currentItem.tryoutCategory

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.context, ExplanationActivity::class.java)
            intent.putExtra(ExplanationActivity.EXTRA_QUESTION_TYPE,currentItem.tryoutCategory)
            intent.putExtra(ExplanationActivity.EXTRA_ANSWER,currentItem)
            it.context.startActivity(intent)
        }
    }
}