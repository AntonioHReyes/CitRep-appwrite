package com.tonyakitori.citrep.usecases

import com.tonyakitori.citrep.data.repositories.AccountRepository

class GetAccountUseCase(private val accountRepository: AccountRepository) {

    suspend operator fun invoke() = accountRepository.getAccount()

}