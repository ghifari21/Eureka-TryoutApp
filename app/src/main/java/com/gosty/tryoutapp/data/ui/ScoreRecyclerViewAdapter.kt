package com.gosty.tryoutapp.data.ui

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gosty.tryoutapp.R
import com.gosty.tryoutapp.data.models.ScoreModel
import com.gosty.tryoutapp.databinding.ItemScoreBinding
import com.gosty.tryoutapp.ui.explanation.ExplanationActivity

class ScoreRecyclerViewAdapter(private var myScoreList : List<ScoreModel>) : RecyclerView.Adapter<ScoreRecyclerViewAdapter.MyViewHolder>() {
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
        holder.binding.tvTestKe.text = Resources.getSystem().getString(R.string.test_ke, (position + 1).toString())
        holder.binding.tvDurasi.text = currentItem.totalTime.toString()
        holder.binding.tvBenar.text = Resources.getSystem().getString(R.string.benar, currentItem.correctAnswer.toString())
        holder.binding.tvSalah.text = Resources.getSystem().getString(R.string.salah, currentItem.wrongAnswer.toString())
        holder.binding.tvKosong.text = Resources.getSystem().getString(R.string.kosong, currentItem.notAnswered.toString())
        holder.binding.tvNilai.text = currentItem.score.toString()
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.context, ExplanationActivity::class.java)
            intent.putExtra("score_id",currentItem.scoreId)
            it.context.startActivity(intent)
        }
    }
}