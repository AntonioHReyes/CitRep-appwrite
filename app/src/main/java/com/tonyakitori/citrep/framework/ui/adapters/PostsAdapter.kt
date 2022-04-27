package com.tonyakitori.citrep.framework.ui.adapters

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tonyakitori.citrep.databinding.ItemPostPublishedBinding
import com.tonyakitori.citrep.domain.entities.PostEntity
import com.tonyakitori.citrep.framework.utils.getFormattedDate

class DiffPosts() : DiffUtil.ItemCallback<PostEntity>() {
    override fun areItemsTheSame(oldItem: PostEntity, newItem: PostEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PostEntity, newItem: PostEntity): Boolean {
        return oldItem == newItem
    }

}

class PostsAdapter(private val callback: () -> Unit) :
    RecyclerView.Adapter<PostsAdapter.PostsViewHolder>() {

    private val posts: AsyncListDiffer<PostEntity> = AsyncListDiffer(this, DiffPosts())

    fun addPosts(list: List<PostEntity>) {
        posts.submitList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsViewHolder {
        val binding =
            ItemPostPublishedBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return PostsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostsViewHolder, position: Int) {
        val item = posts.currentList[position]

        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return posts.currentList.size
    }

    inner class PostsViewHolder(private val binding: ItemPostPublishedBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PostEntity) = with(binding) {

            item.user?.let {
                avatarImage.setImageBitmap(it.avatar?.let { avatar ->
                    BitmapFactory.decodeByteArray(avatar, 0, avatar.size)
                })

                profileName.text = it.name
            }

            postDate.text = item.date.getFormattedDate()
            postDescription.text = item.comment

            galleryImages.isVisible = showImages(item)

            galleryImagesLayout.apply {
                val list = getImagesAsList(item)
                image1.setImageBitmap(null)
                image1.isVisible = false
                image2.setImageBitmap(null)
                image2.isVisible = false
                image3.setImageBitmap(null)
                image3.isVisible = false

                list.forEachIndexed { index, image ->
                    when (index) {
                        0 -> {
                            image1.isVisible = true
                            image1.setImageBitmap(
                                BitmapFactory.decodeByteArray(
                                    image,
                                    0,
                                    image.size
                                )
                            )
                        }

                        1 -> {
                            image2.isVisible = true
                            image2.setImageBitmap(
                                BitmapFactory.decodeByteArray(
                                    image,
                                    0,
                                    image.size
                                )
                            )
                        }

                        2 -> {
                            image3.isVisible = true
                            image3.setImageBitmap(
                                BitmapFactory.decodeByteArray(
                                    image,
                                    0,
                                    image.size
                                )
                            )
                        }
                    }
                }
            }

            actions.emotionAction.setOnClickListener {
                callback()
            }

            actions.commentAction.setOnClickListener {
                callback()
            }

            actions.shareAction.setOnClickListener {
                callback()
            }
        }

        private fun showImages(item: PostEntity): Boolean {
            return item.image1ByteArray != null ||
                    item.image2ByteArray != null ||
                    item.image3ByteArray != null
        }

        private fun getImagesAsList(item: PostEntity): List<ByteArray> {
            return listOfNotNull(
                item.image1ByteArray,
                item.image2ByteArray,
                item.image3ByteArray
            )
        }

    }
}