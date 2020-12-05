package org.ram.loanapi.cal

import kotlin.math.roundToInt

class LoanStructure {
    fun calculatePaymentStructure(config: Config): List<CalculateInterestAndPrinciple.InterestPrinciple> {
        return process(config).result?.toList() ?: mutableListOf()
    }

    private fun process(config: Config): Config {
        //todo: interest amount can be different for diff months
        //todo: should have option for additional payment (prepay)
        //todo: duration can be adjusted according to thatΩ

        val count: Int = config.count ?: 1
        val calculateInterestConfig: CalculateInterestAndPrinciple.Config = config.config
        val obj: CalculateInterestAndPrinciple = config.calculateInterestAndPrinciple
        val durationInMonth: Int = config.durationInMonth


        return if (count > durationInMonth) config
        else {
            val i1 = obj.getTheInterestAndPrincipleForTheEMI(config = calculateInterestConfig)
            val config2 = calculateInterestConfig.copy(balance = calculateInterestConfig.balance - i1.principle)
            println("count: $count:::::EMI: ${calculateInterestConfig.emi}::::::::Balance Remaining: ${config2.balance.toDouble().roundToInt()}::::::, " +
                    "Interest: ${i1.interest.toDouble().roundToInt()}:::::::, principal: ${i1.principle.toDouble().roundToInt()}:::::::::")
            val x = config.result
            x?.add(i1)
            val newConfig = config.copy(count = count + 1, interestPrinciple = i1, config = config2, result = x)
            process(newConfig)
        }

    }

    data class Config(
            val count: Int? = 1,
            val config: CalculateInterestAndPrinciple.Config,
            val interestPrinciple: CalculateInterestAndPrinciple.InterestPrinciple,
            val calculateInterestAndPrinciple: CalculateInterestAndPrinciple,
            val durationInMonth: Int,
            val result: MutableList<CalculateInterestAndPrinciple.InterestPrinciple>? = mutableListOf(),
    )
}