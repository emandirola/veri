package tebi.veri.service

import tebi.veri.domain.model.Account
import tebi.veri.domain.model.Client
import tebi.veri.domain.model.EntityId
import tebi.veri.domain.repository.ClientRepository

class ClientService(private val repository: ClientRepository) {
    fun createClient(clientId: String) {
        val client = Client(EntityId(clientId), Account())
        this.repository.insertClient(client)
    }

    fun getClient(id: String): Client {
        return repository.findClient(EntityId(id)) ?: throw IllegalArgumentException("Client $id doesn't exist")
    }

    fun balance(id: String): Int? {
        return getClient(id).balance
    }

    fun deposit(id: String, amount: Int) {
        val client = getClient(id)
        client.deposit(amount)
        this.repository.updateClient(client)
    }

    fun withdraw(id: String, amount: Int) {
        val client = getClient(id)
        client.withdraw(amount)
        this.repository.updateClient(client)
    }

    fun wire(idFrom: String, idTo: String, amount: Int) {
        val fromClient = getClient(idFrom)
        val toClient = getClient(idTo)
        fromClient.withdraw(amount)
        toClient.deposit(amount)
    }

}
