package tebi.veri.service

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.catchThrowable
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
        assertThat(clientService.balance("francisco")).isEqualTo(100)
    }

    @Test
    fun `scenario 1 existing user deposits money`() {
        // given
        clientService.createClient("francisco")
        clientService.deposit("francisco", 100)

        // when
        clientService.deposit("francisco", 10)

        // then
        assertThat(clientService.balance("francisco")).isEqualTo(110)
    }

    @Test
    fun `can't deposit negative amount`() {
        // given
        clientService.createClient("francisco")
        clientService.deposit("francisco", 100)

        // when
        val throwable = catchThrowable { clientService.deposit("francisco", -10) }

        // then
        assertThat(throwable).isInstanceOf(IllegalArgumentException::class.java)
    }

    @Test
    fun `withdraw money`() {
        // given
        clientService.createClient("francisco")
        clientService.deposit("francisco", 100)

        // when
        clientService.withdraw("francisco", 10)

        // then
        assertThat(clientService.balance("francisco")).isEqualTo(90)
    }

    @Test
    fun `can't overdraft`() {
        // given
        clientService.createClient("francisco")
        clientService.deposit("francisco", 100)

        // when
        val throwable = catchThrowable { clientService.withdraw("francisco", 200) }

        // then
        assertThat(throwable).isInstanceOf(IllegalArgumentException::class.java)
    }

    @Test
    fun `can't withdraw negative money`() {
        // given
        clientService.createClient("francisco")
        clientService.deposit("francisco", 100)

        // when
        val throwable = catchThrowable { clientService.withdraw("francisco", -10) }

        // then
        assertThat(throwable).isInstanceOf(IllegalArgumentException::class.java)
    }

    @Test
    fun `can't deposit if client doesn't exist`() {
        // given

        // when
        val throwable = catchThrowable { clientService.deposit("francisco", 10) }

        // then
        assertThat(throwable).isInstanceOf(IllegalArgumentException::class.java)
    }

    @Test
    fun `can't withdraw if client doesn't exist`() {
        // given

        // when
        val throwable = catchThrowable { clientService.withdraw("francisco", 10) }

        // then
        assertThat(throwable).isInstanceOf(IllegalArgumentException::class.java)
    }

}

