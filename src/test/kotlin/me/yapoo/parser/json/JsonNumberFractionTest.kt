package me.yapoo.parser.json

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import me.yapoo.parser.core.ParseFailure
import me.yapoo.parser.core.ParseSuccess
import java.math.BigDecimal

class JsonNumberFractionTest : StringSpec({
    "json number fraction parser" should {
        val parser = jsonNumberFraction()

        "succeed with valid fraction string" {
            parser(".1234") shouldBe ParseSuccess(
                value = 0.1234.toBigDecimal(),
                rest = ""
            )
        }
        "succeed with all zero fraction" {
            parser(".0000") shouldBe ParseSuccess(
                value = BigDecimal.ZERO,
                rest = ""
            )
        }
        "succeed with empty string" {
            parser("") shouldBe ParseFailure
        }
        "fail with invalid fraction" {
            parser("1234") shouldBe ParseFailure
        }
    }
})
