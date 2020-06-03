package tebi.veri.infra.repository

import tebi.veri.domain.model.Client
import tebi.veri.domain.model.EntityId
import tebi.veri.domain.repository.ClientRepository

class InMemoryClientRepository : ClientRepository {
    private val repository: MutableMap<EntityId, Client> = hashMapOf()

    override fun insertClient(client: Client) {
        if (client.id in repository) throw IllegalArgumentException("Client already exists")
        repository[client.id] = client
    }

    override fun findClient(clientId: EntityId): Client? {
        return repository[clientId]
    }

    override fun updateClient(client: Client) {
        if (client.id !in repository) throw IllegalArgumentException("Client doesn't exist")
        repository[client.id] = client
    }
}

