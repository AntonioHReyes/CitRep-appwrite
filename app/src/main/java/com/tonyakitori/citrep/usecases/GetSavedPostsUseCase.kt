package com.tonyakitori.citrep.usecases

import com.tonyakitori.citrep.data.repositories.PostCollectionRepository

class GetSavedPostsUseCase(private val postCollectionRepository: PostCollectionRepository) {

    suspend operator fun invoke() = postCollectionRepository.getSavedPosts()

}