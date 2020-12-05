package org.ram.loanapi.cal

import java.math.BigDecimal

class CalculateInterestAndPrinciple {

    fun getTheInterestAndPrincipleForTheEMI(config: Config): InterestPrinciple {

        val interest = config.balance * BigDecimal.valueOf(config.getMonthlyRate())

        val principle = config.emi.subtract(interest)

        return InterestPrinciple(
                interest = interest,
                principle = principle
        )
    }

    data class InterestPrinciple(
            val interest: BigDecimal,
            val principle: BigDecimal,
    )

    data class Config(
            val emi: BigDecimal,
            val yearlyRate: Double,
            val balance: BigDecimal,
    ) {

        fun getMonthlyRate(): Double {
            return (yearlyRate / 100) / 12
        }


    }
}