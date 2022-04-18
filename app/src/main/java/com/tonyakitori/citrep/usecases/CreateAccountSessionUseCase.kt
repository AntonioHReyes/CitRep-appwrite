package com.tonyakitori.citrep.usecases

import com.tonyakitori.citrep.data.repositories.AccountRepository
import com.tonyakitori.citrep.domain.entities.AccountEntity

class CreateAccountSessionUseCase(private val accountRepository: AccountRepository) {

    suspend operator fun invoke(accountEntity: AccountEntity) =
        accountRepository.createAccountSession(accountEntity)

}