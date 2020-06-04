package tebi.veri.service

import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import tebi.veri.infra.repository.InMemoryClientRepository

class MoneyTransferTest {
    private lateinit var clientService: ClientService

    @Before
    fun before() {
        clientService = ClientService(InMemoryClientRepository())
    }

    @Test
    fun `basic transfer`() {
        // given
        clientService.createClient("francisco")
        clientService.deposit("francisco", 100)
        clientService.createClient("alejandro")

        // when
        clientService.wire("francisco", "alejandro", 100)

        // then
        assertThat(clientService.balance("francisco")).isEqualTo(0)
        assertThat(clientService.balance("alejandro")).isEqualTo(100)
    }
}