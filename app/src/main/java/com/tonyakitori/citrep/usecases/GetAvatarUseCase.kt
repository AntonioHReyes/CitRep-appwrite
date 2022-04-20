package com.tonyakitori.citrep.usecases

import com.tonyakitori.citrep.data.repositories.AvatarRepository

class GetAvatarUseCase(private val avatarRepository: AvatarRepository) {

    suspend operator fun invoke() = avatarRepository.getAvatar()

}