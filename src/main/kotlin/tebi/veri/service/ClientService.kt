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

    fun getClient(id: String): Client? {
        return repository.findClient(EntityId(id))
    }

    fun balance(id: String): Int? {
        return getClient(id)?.balance ?: throw IllegalArgumentException("Client doesn't exist")
    }

    fun deposit(id: String, amount: Int) {
        val client = getClient(id) ?: throw IllegalArgumentException("Client doesn't exist")
        client.deposit(amount)
        this.repository.updateClient(client)
    }

    fun withdraw(id: String, amount: Int) {
        val client = getClient(id) ?: throw IllegalArgumentException("Client doesn't exist")
        client.withdraw(amount)
        this.repository.updateClient(client)
    }

    fun wire(idFrom: String, idTo: String, amount: Int) {
        val fromClient = getClient(idFrom) ?: throw IllegalArgumentException("Transferer client doesn't exist")
        val toClient = getClient(idTo) ?: throw IllegalArgumentException("Receiver client doesn't exist")
        fromClient.withdraw(amount)
        toClient.deposit(amount)
    }

}
