package com.c6bank.springkotlin.domain

import com.c6bank.springkotlin.models.Client
import com.c6bank.springkotlin.repository.ClientRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/client")
class DomainClient {

    @Autowired
    lateinit var repository : ClientRepository

    @GetMapping
    fun getClient(): ResponseEntity<List<Client>>{
        if(repository.count() > 0){
            return ResponseEntity.ok(repository.findAll())
        }
        else{
            return ResponseEntity.noContent().build()
        }
    }

    @PostMapping
    fun addClient(@RequestBody newClient: Client): ResponseEntity<Client> {
        return ResponseEntity.ok().body(repository.save(newClient))
    }

    @DeleteMapping("/{id}")
    fun deleteClient(@PathVariable id: Int): ResponseEntity<String>{
        if(repository.existsById(id)){
            repository.deleteById(id)
            return ResponseEntity.ok().body("Cliente deletado")
        }
        else{
            return ResponseEntity.notFound().build()
        }
    }

    @PutMapping("/{id}/{newName}")
    fun updateClientName(@PathVariable id: Int, @PathVariable newName: String): ResponseEntity<String>{
        if(repository.existsById(id)){
            val currentUser: Optional<Client> = repository.findById(id)
            currentUser.get().name = newName
            repository.save(currentUser.get())
            return ResponseEntity.ok().body("Usuário de id: ${id} foi alterado com sucesso!")
        }
        else{
            return ResponseEntity.notFound().build()
        }
    }
}