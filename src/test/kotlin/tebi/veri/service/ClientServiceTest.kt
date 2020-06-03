package tebi.veri.service

import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import tebi.veri.domain.model.Client
import tebi.veri.domain.model.EntityId
import tebi.veri.infra.repository.InMemoryClientRepository

class ClientTest {
    private lateinit var clientService: ClientService

    @Before
    fun before() {
        clientService = ClientService(InMemoryClientRepository())

    }

    @Test
    fun `create client account`() {
        // given
        clientService.createClient("francisco")

        // when
        val client: Client? = clientService.getClient("francisco")

        // then
        assertThat(client).isNotNull
        assertThat(client!!.id).isEqualTo(EntityId("francisco"))
        assertThat(client.balance).isEqualTo(0)
    }

    @Test
    fun `deposit money`() {
        // given
        clientService.createClient("francisco")

        // when
        clientService.deposit("francisco", 100)

        // then
        val client = clientService.getClient("francisco")!!
        assertThat(client.balance).isEqualTo(100)
    }

}

