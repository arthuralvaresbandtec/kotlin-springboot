package com.c6bank.springkotlin.models

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class Client(
    @Id
    var id: Int,
    var name: String,
    var balance: Double,
    var post: String){

        @Column
        val salary: Double = when(post){
            in "Estagiario" -> 800.00
            in "Junior" -> 2500.00
            in "Pleno" -> 5000.00
            in "Senior" -> 7500.00

            else -> 0.00
        }
    }



