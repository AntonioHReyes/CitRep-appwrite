package com.tonyakitori.citrep.usecases

import com.tonyakitori.citrep.data.repositories.AccountRepository

class ConfirmEmailVerificationUseCase(private val accountRepository: AccountRepository) {

    suspend operator fun invoke(userId: String, secret: String) =
        accountRepository.confirmEmailVerification(userId, secret)

}