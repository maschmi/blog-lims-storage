package de.maschmi.blog.lims.storage.boot.configuration

import application.*
import domain.StorageRepository
import domain.StorageValidator
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.AutoConfigureAfter
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean


@AutoConfiguration
@AutoConfigureAfter(value = [StorageDomainAutoConfiguration::class])
class StorageCabinetUseCaseAutoConfiguration {

    @ConditionalOnMissingBean(value = [ChangeNameOfStorageCabinet::class])
    @Bean
    fun changeNameOfStorageCabinet(repository: StorageRepository, validator: StorageValidator) : ChangeNameOfStorageCabinet {
        return ChangeNameOfStorageCabinet(repository, validator)
    }

    @ConditionalOnMissingBean(value = [ChangeRoomOfStorageCabinet::class])
    @Bean
    fun changeRoomOfStorageCabinet(repository: StorageRepository, validator: StorageValidator) : ChangeRoomOfStorageCabinet {
        return ChangeRoomOfStorageCabinet(repository, validator)
    }

    @ConditionalOnMissingBean(value = [CommissionStorageCabinet::class])
    @Bean
    fun commissionStorageCabinet(repository: StorageRepository, validator: StorageValidator) : CommissionStorageCabinet {
        return CommissionStorageCabinet(repository, validator)
    }

    @ConditionalOnMissingBean(value = [DecomissionStorageCabinet::class])
    @Bean
    fun decommissionStorageCabinet(repository: StorageRepository, validator: StorageValidator) : DecomissionStorageCabinet {
        return DecomissionStorageCabinet(repository)
    }

    @ConditionalOnMissingBean(value = [GetListOfStorageCabinets::class])
    @Bean
    fun getListOfStorageCabinets(repository: StorageRepository) : GetListOfStorageCabinets {
        return GetListOfStorageCabinets(repository)
    }
}

