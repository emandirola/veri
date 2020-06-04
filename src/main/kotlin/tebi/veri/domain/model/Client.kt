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
    fun deposit(amount: Int) {
        if (amount < 0) throw IllegalArgumentException("Can't deposit negative money")
        account.balance += amount
    }

    fun withdraw(amount: Int) {
        if (amount > account.balance) throw IllegalArgumentException("Can't overdraft")
        if (amount < 0) throw  IllegalArgumentException("Can't withdraw negative money")
        account.balance -= amount
    }

    val balance
        get() = account.balance
}
