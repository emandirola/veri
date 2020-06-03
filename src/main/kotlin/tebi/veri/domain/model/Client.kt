package tebi.veri.domain.model

/**
 * Client aggregate
 */
class Client(
    /** value, id **/
    override val id: EntityId,
    /** root entity **/
    private val account: Account
) : Entity {
    fun addBalance(amount: Int) {
        account.balance += amount
    }

    val balance
        get() = account.balance
}
