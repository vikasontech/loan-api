package org.ram.loanapi.cal

import org.apache.poi.ss.formula.functions.FinanceLib
import java.math.BigDecimal
import java.text.DecimalFormat

class EMICalculator(
        private val config: Config,
) {

    fun calculateEmiString(): String? {
        return DecimalFormat("#.##").format(
                calculateEmi().toDouble())
    }

    fun calculateEmi(): BigDecimal {
        return BigDecimal.valueOf(
                FinanceLib.pmt(
                        config.getRateInPercentage() / 12,
                        config.months.toDouble(),
                        config.principal.toDouble(),
                        config.futureValue?.toDouble() ?: 0.0,
                        config.type ?: false
                )).multiply(BigDecimal.valueOf(-1))
    }

    data class Config(
            val yearlyRate: Double,
            val months: Int,
            val principal: BigDecimal,
            val futureValue: BigDecimal? = BigDecimal.ZERO,
            val type: Boolean? = false,
    ) {

        fun getRateInPercentage(): Double {
            return yearlyRate / 100
        }

    }
}