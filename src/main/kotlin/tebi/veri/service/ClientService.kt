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

    fun deposit(id: String, amount: Int) {
        val client = getClient(id)
        client!!.addBalance(amount)
        this.repository.updateClient(client)
    }

    fun withdraw(id: String, amount: Int) {
        val client = getClient(id)
        client!!.removeBalance(amount)
        this.repository.updateClient(client)
    }

}
