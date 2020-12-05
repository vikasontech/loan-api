package org.ram.loanapi

import com.mongodb.DuplicateKeyException
import org.bson.types.ObjectId
import org.ram.loanapi.cal.EMICalculator
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import java.lang.Exception
import java.math.BigDecimal
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

@SpringBootApplication
class LoanApiApplication

fun main(args: Array<String>) {
    runApplication<LoanApiApplication>(*args)
}

@RestController
@RequestMapping("/account")
class AccountResource(
        private val accountDetailsRepo: AccountDetailsRepo,
) {

    @PostMapping("/create")
    fun createResource(): Mono<ResponseEntity<AccountDetail>> {

        return Mono.just(EMICalculator.Config( yearlyRate = 8.35,
                months = 180,
                principal = BigDecimal.ONE))
                .map { EMICalculator(it) }
                .map { it.calculateEmi() }
                .flatMap {
                    accountDetailsRepo.save(
                            AccountDetail(
                                    name = "vikas",
                                    userId = "vikas.on",
                                    bankName = "hdfc",
                                    balance = BigDecimal.ONE,
                                    amount = BigDecimal.ONE,
                                    interestRate = 8.35,
                                    emi = BigDecimal.valueOf(it.toDouble())))
                }
                .map { ResponseEntity.ok(it) }
                .onErrorResume {
                    when (it) {
                        is DuplicateKeyException -> handleDuplicateUserIdError()
                        else -> Mono.just(ResponseEntity.status(HttpStatus.FORBIDDEN).build())
                    }

                }
    }

    private fun handleDuplicateUserIdError(): Mono<out ResponseEntity<AccountDetail>> =
            Mono.just(ResponseEntity.badRequest().build())

}

@Repository
interface AccountDetailsRepo : ReactiveCrudRepository<AccountDetail, String>

data class AccountDetail(
        val id: String? = ObjectId.get().toString(),
        val name: String,
        val bankName: String,
        val balance: BigDecimal,
        val amount: BigDecimal,
        val interestRate: Double,
        val userId: String,
        val emi: BigDecimal,
)



