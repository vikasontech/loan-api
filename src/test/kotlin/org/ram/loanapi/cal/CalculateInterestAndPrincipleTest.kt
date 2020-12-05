package org.ram.loanapi.cal

import org.junit.jupiter.api.Test
import org.springframework.util.Assert
import java.math.BigDecimal

internal class CalculateInterestAndPrincipleTest {

    @Test
    internal fun sample() {

        val result = CalculateInterestAndPrinciple()
                .getTheInterestAndPrincipleForTheEMI(CalculateInterestAndPrinciple.Config(
                        emi = BigDecimal.valueOf(40553.66),
                        yearlyRate = 8.35,
                        balance = BigDecimal.valueOf(4155230)))

        val expected = CalculateInterestAndPrinciple.InterestPrinciple(
                interest =  BigDecimal("28913.475416666665281590"),
                principle = BigDecimal("11640.184583333334718410")
        )
        Assert.isTrue(result == expected, "Interest and principle value is not correct")

    }
}

