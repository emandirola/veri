package tebi.veri.service

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.catchThrowable
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

    @Test
    fun `can't transfer if not enough money`() {
        // given
        clientService.createClient("francisco")
        clientService.deposit("francisco", 100)
        clientService.createClient("alejandro")

        // when
        val throwable = catchThrowable { clientService.wire("francisco", "alejandro", 200) }

        // then
        assertThat(throwable).isInstanceOf(IllegalArgumentException::class.java)
        assertThat(clientService.balance("francisco")).isEqualTo(100)
        assertThat(clientService.balance("alejandro")).isEqualTo(0)
    }
}