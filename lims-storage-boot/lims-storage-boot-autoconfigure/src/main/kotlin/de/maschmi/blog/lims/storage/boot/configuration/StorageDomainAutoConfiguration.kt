package de.maschmi.blog.lims.storage.boot.configuration

import domain.StorageRepository
import domain.StorageValidator
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.AutoConfigureAfter
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean

@AutoConfiguration
@AutoConfigureAfter(value = [StorageRepositoryAutoConfiguration::class])
class StorageDomainAutoConfiguration {

    @ConditionalOnMissingBean(StorageValidator::class)
    @Bean
    fun storageValidator(repository: StorageRepository): StorageValidator {
        return StorageValidator(repository)
    }

}