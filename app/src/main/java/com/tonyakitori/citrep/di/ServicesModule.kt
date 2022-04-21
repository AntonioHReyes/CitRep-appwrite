package com.tonyakitori.citrep.di

import com.tonyakitori.citrep.framework.services.FileManagementService
import com.tonyakitori.citrep.framework.services.FileManagementService.FilePlace.CACHE
import com.tonyakitori.citrep.framework.services.FileManagementService.FilePlace.FILES
import org.koin.core.qualifier.named
import org.koin.dsl.module

val servicesModule = module {

    factory(named(FILES)) { FileManagementService(get(), filePlace = FILES) }
    factory(named(CACHE)) { FileManagementService(get(), filePlace = CACHE) }

}