package tebi.veri.infra.repository

import tebi.veri.domain.model.Client
import tebi.veri.domain.model.EntityId
import tebi.veri.domain.repository.ClientRepository

class InMemoryClientRepository : ClientRepository {
    private val repository: MutableMap<EntityId, Client> = hashMapOf()

    override fun insertClient(client: Client) {
        repository[client.id] = client
    }

    override fun findClient(clientId: EntityId): Client? {
        return repository[clientId]
    }

    override fun updateClient(client: Client) {
        repository[client.id] = client
    }
}

