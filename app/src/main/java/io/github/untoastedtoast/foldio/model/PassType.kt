package io.github.untoastedtoast.foldio.model

import androidx.annotation.StringRes
import androidx.room.Entity
import io.github.untoastedtoast.foldio.R

sealed class PassType(
    val jsonKey: String,
    @param:StringRes val label: Int,
) {
    @Entity
    object Generic : PassType(GENERIC, R.string.generic_pass) {
        override fun isSameType(passType: PassType): Boolean = this == passType
    }

    @Entity
    object Event : PassType(EVENT, R.string.event) {
        override fun isSameType(passType: PassType): Boolean = this == passType
    }

    @Entity
    object Coupon : PassType(COUPON, R.string.coupon) {
        override fun isSameType(passType: PassType): Boolean = this == passType
    }

    @Entity
    data class Boarding(
        val transitType: TransitType,
    ) : PassType(BOARDING, R.string.boarding_pass) {
        override fun isSameType(passType: PassType): Boolean = this.javaClass == passType.javaClass
    }

    @Entity
    object StoreCard : PassType(STORE_CARD, R.string.store_card) {
        override fun isSameType(passType: PassType) = this == passType
    }

    /**
     * Checks whether two {@link PassType}s are the same type, regardless of possible further inner differentiation
     * like flight or trains boarding pass
     */
    abstract fun isSameType(passType: PassType): Boolean

    companion object {
        const val GENERIC = "generic"
        const val EVENT = "eventTicket"
        const val COUPON = "coupon"
        const val BOARDING = "boardingPass"
        const val STORE_CARD = "storeCard"

        fun all(): List<PassType> =
            listOf(
                Generic,
                Event,
                Coupon,
                Boarding(TransitType.GENERIC),
                StoreCard,
            )
    }
}
