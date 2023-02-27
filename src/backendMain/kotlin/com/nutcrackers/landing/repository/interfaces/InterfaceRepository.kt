package com.nutcrackers.landing.repository.interfaces

import BrowserRepositoryModel
import LocaleRepositoryModel
import SessionRepositoryModel
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository

@Repository
interface IBrowserRepository: CrudRepository<BrowserRepositoryModel, Long> {

}

@Repository
interface ISessionRepository: CrudRepository<SessionRepositoryModel, Long>{

}

@Repository
interface  ILocaleRepository: CrudRepository<LocaleRepositoryModel, Long>{

}