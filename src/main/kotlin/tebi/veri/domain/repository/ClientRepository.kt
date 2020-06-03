package tebi.veri.domain.repository

import tebi.veri.domain.model.Client
import tebi.veri.domain.model.EntityId

interface ClientRepository {

    fun insertClient(client: Client)
    fun findClient(clientId: EntityId): Client?

}
