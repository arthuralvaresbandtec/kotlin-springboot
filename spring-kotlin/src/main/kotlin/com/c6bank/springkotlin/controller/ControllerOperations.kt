package com.c6bank.springkotlin.controller

import com.c6bank.springkotlin.models.Client
import com.c6bank.springkotlin.repository.ClientRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/ops")
class ControllerOperations {

    @Autowired
    lateinit var repositoryOps : ClientRepository

    @GetMapping("/{id}")
    fun getClientBalance(@PathVariable id: Int): ResponseEntity<String>{
        if(repositoryOps.existsById(id)){
            val user = repositoryOps.findById(id)
            val userName = user.get().name
            val userBalance = user.get().balance
            return ResponseEntity.ok().body("Olá ${userName}, seu saldo é de: R$${userBalance}")
        }
        else{
            return ResponseEntity.notFound().build()
        }
    }

    @PutMapping("/ted/{idPayer}/{idReceiver}/{amount}")
    fun ted(@PathVariable idPayer: Int, @PathVariable idReceiver: Int, @PathVariable amount: Double): ResponseEntity<String>{
        if(repositoryOps.existsById(idPayer) && repositoryOps.existsById(idReceiver)){

            var userPayer: Optional<Client> = repositoryOps.findById(idPayer)
            val userPayerName = userPayer.get().name
            var userPayerBalance = userPayer.get().balance

            var userReceiver: Optional<Client> = repositoryOps.findById(idReceiver)
            val userReceiverName = userReceiver.get().name
            var userReceiverBalance = userReceiver.get().balance

            if(userPayerBalance >= amount){
                userPayerBalance -= amount
                userPayer.get().balance = userPayerBalance
                repositoryOps.saveAndFlush(userPayer.get())

                userReceiverBalance += amount
                userReceiver.get().balance = userReceiverBalance
                repositoryOps.saveAndFlush(userReceiver.get())

                return ResponseEntity.ok().body("${userPayerName}, você fez uma transferência de R$${amount} para $userReceiverName")
            }
           else{
               return ResponseEntity.ok().body("Não foi possível completar a operação, saldo insuficiente")
            }
        }
        else{
            return ResponseEntity.notFound().build()
        }
    }

    @PutMapping("/salary/{id}")
    fun receiveSalary(@PathVariable id: Int): ResponseEntity<String>{
        if(repositoryOps.existsById(id)){
            var user: Optional<Client> = repositoryOps.findById(id)
            val userName = user.get().name
            var userBalance = user.get().balance
            val userSalary = user.get().salary

            userBalance += userSalary
            user.get().balance = userBalance
            repositoryOps.saveAndFlush(user.get())

            return ResponseEntity.ok().body("${userName}, você recebeu seu salário no valor de $userSalary")
        }
        else{
            return ResponseEntity.notFound().build()
        }
    }
}