package com.tonyakitori.citrep.usecases

import com.tonyakitori.citrep.data.repositories.AccountRepository

class LogOutUseCase(private val accountRepository: AccountRepository) {

    suspend operator fun invoke() = accountRepository.deleteAccountSession()
}