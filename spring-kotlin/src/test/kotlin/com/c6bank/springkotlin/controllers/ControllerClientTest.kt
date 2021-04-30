package com.c6bank.springkotlin.controllers

import com.c6bank.springkotlin.controller.ControllerClient
import com.c6bank.springkotlin.models.Client
import com.c6bank.springkotlin.repository.ClientRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate

@SpringBootTest
class ControllerClientTest {

    @Autowired
    lateinit var controller : ControllerClient

    @MockBean
    lateinit var repository : ClientRepository

    @Mock
    lateinit var restTemplate: RestTemplate

    @Test
    @DisplayName("getClients() deve retornar status 200 e a lista de todos os clientes")
    fun getClientsOk(){

        val clientList = listOf(Mockito.mock(Client::class.java))
        Mockito.`when`(repository.findAll()).thenReturn(clientList)

        val answer: ResponseEntity<List<Client>> =  controller.getClients()
        Assertions.assertEquals(200, answer.statusCodeValue)
        Assertions.assertEquals(clientList, answer.body)
 /*
        val client = Client(3, "Ronaldo", 7500.00, "Senior")
        val url = "http://localhost:8080/client/3"

        Mockito.`when`(restTemplate.getForEntity(url, Client::class.java)).thenReturn(ResponseEntity(client, HttpStatus.OK))
        Assertions.assertEquals(client, controller.getClients())
 */
    }

    @Test
    @DisplayName("getClients() não deve ter corpo e trazer status 204")
    fun getClientsFailure() {
        val clientList: List<Client> = listOf<Client>()
        Mockito.`when`(repository.findAll()).thenReturn(clientList)

        val answer: ResponseEntity<*> = controller.getClients()
        Assertions.assertEquals(204, answer.statusCodeValue)
        Assertions.assertEquals(null, answer.body)
    }

}