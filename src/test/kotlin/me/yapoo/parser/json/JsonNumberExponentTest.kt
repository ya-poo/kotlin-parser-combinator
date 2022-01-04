package me.yapoo.parser.json

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import me.yapoo.parser.core.ParseFailure
import me.yapoo.parser.core.ParseSuccess

class JsonNumberExponentTest : StringSpec({
    "json number exponent parser" should {
        val parser = jsonNumberExponent()

        "succeed with upper E with negative integer" {
            parser("E-12") shouldBe ParseSuccess(
                value = 1e-12,
                rest = ""
            )
        }
        "succeed with upper E with unsigned integer" {
            parser("E12") shouldBe ParseSuccess(
                value = 1e12,
                rest = ""
            )
        }
        "succeed with upper E with plus integer" {
            parser("E+12") shouldBe ParseSuccess(
                value = 1e12,
                rest = ""
            )
        }
        "succeed with lower e with negative integer" {
            parser("e-12") shouldBe ParseSuccess(
                value = 1e-12,
                rest = ""
            )
        }
        "succeed with lower e with unsigned integer" {
            parser("e12") shouldBe ParseSuccess(
                value = 1e12,
                rest = ""
            )
        }
        "succeed with lower e with plus integer" {
            parser("e+12") shouldBe ParseSuccess(
                value = 1e12,
                rest = ""
            )
        }
        "succeed with plus zero" {
            parser("e+00") shouldBe ParseSuccess(
                value = 1.0,
                rest = ""
            )
        }
        "succeed with minus zero" {
            parser("e-0") shouldBe ParseSuccess(
                value = 1.0,
                rest = ""
            )
        }
        "succeed with unsigned zero" {
            parser("e0000") shouldBe ParseSuccess(
                value = 1.0,
                rest = ""
            )
        }
        "fail with single integer" {
            parser("123") shouldBe ParseFailure
        }
    }
})
