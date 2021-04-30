package com.c6bank.springkotlin.repository

import com.c6bank.springkotlin.models.Client
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ClientRepository : JpaRepository<Client, Int>{
    //fun deleteById(id: Int) = if(t.existsById(id)) {t.deleteById(id); true} else false
}