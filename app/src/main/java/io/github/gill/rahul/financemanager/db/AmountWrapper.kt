package io.github.gill.rahul.financemanager.db

@JvmInline
value class AmountWrapper private constructor(
    private val amountInner: Long
) {
    val doubleValue: Double
        get() = amountInner / 100.0

    val formattedValue: String
        get() = "%.2f".format(doubleValue)


    operator fun plus(other: AmountWrapper): AmountWrapper {
        return fromLong(this.amountInner + other.amountInner)
    }

    companion object {

        fun fromLong(long: Long) = AmountWrapper(long)
        fun fromDouble(amount: Double) = AmountWrapper((amount * 100).toLong())
    }
}