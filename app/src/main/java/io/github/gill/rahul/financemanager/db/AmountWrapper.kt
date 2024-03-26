package io.github.gill.rahul.financemanager.db

@JvmInline
value class AmountWrapper private constructor(
    private val amount: Long
){
    val doubleValue: Double
        get() = amount / 100.0

    val formattedValue: String
        get() = "%.2f".format(doubleValue)

    companion object {

        fun fromLong(long: Long) = AmountWrapper(long)
        fun fromDouble(amount: Double) = AmountWrapper((amount * 100).toLong())
    }
}