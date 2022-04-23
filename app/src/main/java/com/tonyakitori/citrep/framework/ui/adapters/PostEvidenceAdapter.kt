package com.tonyakitori.citrep.framework.ui.adapters

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tonyakitori.citrep.databinding.ItemImageFileBinding
import com.tonyakitori.citrep.domain.exceptions.StorageExceptions


class PostEvidenceAdapter(private val context: Context) :
    RecyclerView.Adapter<PostEvidenceAdapter.PostEvidenceViewHolder>() {

    private var listOfEvidences = ArrayList<Uri>()

    fun addNewEvidences(listEvidences: ArrayList<Uri>) {

        if (listEvidences.size > MAX_FILES) {
            throw StorageExceptions.MaxFilesLoaded()
        }

        listOfEvidences = listEvidences
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostEvidenceViewHolder {
        val binding =
            ItemImageFileBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return PostEvidenceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostEvidenceViewHolder, position: Int) {
        holder.bind(listOfEvidences[position])
    }

    override fun getItemCount(): Int {
        return listOfEvidences.size
    }

    inner class PostEvidenceViewHolder(private val binding: ItemImageFileBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Uri) = with(binding) {
            imageFile.setImageURI(item)
        }

    }


    companion object {
        const val MAX_FILES = 3
    }
}