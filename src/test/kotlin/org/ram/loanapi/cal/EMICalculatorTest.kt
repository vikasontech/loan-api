package org.ram.loanapi.cal

import org.junit.jupiter.api.Test
import org.springframework.util.Assert
import java.math.BigDecimal

internal class EMICalculatorTest{


    @Test
    internal fun sample() {
        //rate, months, present value, future value, at the beginning of the period
        //https://stackoverflow.com/questions/7827352/excel-pmt-function-in-java

        Assert.isTrue((
                EMICalculator(
                        EMICalculator.Config(
                                yearlyRate = 8.35,
                                months = 180,
                                principal = BigDecimal.valueOf(4155230)
                        )).calculateEmiString()

                ).equals("-40553.66"), "Emi is not correct!")
    }

}

