package org.ram.loanapi.cal

import org.bson.types.ObjectId
import org.junit.jupiter.api.Test
import org.ram.loanapi.cal.CalculateInterestAndPrinciple.Config
import org.ram.loanapi.cal.CalculateInterestAndPrinciple.InterestPrinciple
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime

internal class LoanStructureTest {

    @Test
    internal fun sample() {

        val principle = BigDecimal(4155230)
        //todo: emi is fixed value, should be calculated initially
        val emi = BigDecimal("40553.66460491901")

        println(
                LoanStructure().calculatePaymentStructure(
                        LoanStructure.Config(
                                config = Config(emi = emi, yearlyRate = 8.35, balance = principle),
                                interestPrinciple = InterestPrinciple(interest = BigDecimal(0), principle = BigDecimal(0)),
                                calculateInterestAndPrinciple = CalculateInterestAndPrinciple(),
                                durationInMonth = 180
                        ))
        )
    }

}


//todo: this should be saved in database, after loan struture is created
data class LoanStructureData(
        val id: String? = ObjectId.get().toString(),
        val userID: String,
        val date: LocalDate,
        val emiCount: Int,
        val dateString: String, //yyyymmdd
        val emi: BigDecimal,
        val rate: Double,
        val interest: BigDecimal,
        val principal: BigDecimal,
        val balance: BigDecimal,
        val datetime: LocalDateTime? = LocalDateTime.now(),
        val status: Boolean? = false,
)


