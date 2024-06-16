package de.maschmi.blog.lims.storage.boot.configuration

import boundary.persistence.StorageInMemoryPersistence
import domain.StorageRepository
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean

@AutoConfiguration
class StorageRepositoryAutoConfiguration {


    @ConditionalOnMissingBean(value = [StorageRepository::class])
    @Bean
    fun inMemoryStorageRepository() : StorageRepository {
        return StorageInMemoryPersistence()
    }
}