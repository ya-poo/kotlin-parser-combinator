package me.yapoo.parser.json

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import me.yapoo.parser.core.ParseSuccess
import java.math.BigDecimal

class JsonNumberTest : StringSpec({
    "json number parser" should {
        val parser = jsonNumber()

        "succeed with zero" {
            parser("0") shouldBe ParseSuccess(
                value = JsonNumber(0),
                rest = ""
            )
        }
        "succeed with single integer" {
            parser("1234") shouldBe ParseSuccess(
                value = JsonNumber(1234),
                rest = ""
            )
        }
        "succeed with integer, fraction" {
            parser("1234.56") shouldBe ParseSuccess(
                value = JsonNumber(1234.56),
                rest = ""
            )
        }
        "succeed with integer, fraction and positive exponent" {
            parser("1234.56e+1") shouldBe ParseSuccess(
                value = JsonNumber(BigDecimal.valueOf(1234.56) * BigDecimal.TEN),
                rest = ""
            )
        }
        "succeed with integer, fraction and negative exponent" {
            parser("1234.56e-1") shouldBe ParseSuccess(
                value = JsonNumber(BigDecimal.valueOf(1234.56).divide(BigDecimal.TEN)),
                rest = ""
            )
        }
        "succeed with integer, fraction and zero exponent" {
            parser("1234.56e000") shouldBe ParseSuccess(
                value = JsonNumber(1234.56),
                rest = ""
            )
        }
        "succeed with integer, exponent" {
            parser("1234e2") shouldBe ParseSuccess(
                value = JsonNumber(123400),
                rest = ""
            )
        }
        "succeed with negative integer, fraction" {
            parser("-1234.56") shouldBe ParseSuccess(
                value = JsonNumber(-1234.56),
                rest = ""
            )
        }
        "succeed with negative integer, fraction and positive exponent" {
            parser("-1234.56e2") shouldBe ParseSuccess(
                value = JsonNumber(BigDecimal.valueOf(-1234.56) * BigDecimal.valueOf(100)),
                rest = ""
            )
        }
        "succeed with negative integer, fraction and negative exponent" {
            parser("-1234.56e-2") shouldBe ParseSuccess(
                value = JsonNumber(BigDecimal.valueOf(-1234.56).divide(BigDecimal.valueOf(100))),
                rest = ""
            )
        }
        "succeed with negative integer, fraction and zero exponent" {
            parser("-1234.56e-00") shouldBe ParseSuccess(
                value = JsonNumber(-1234.56),
                rest = ""
            )
        }
        "succeed with fraction" {
            parser("0.123") shouldBe ParseSuccess(
                value = JsonNumber(0.123),
                rest = ""
            )
        }
        "succeed with fraction and positive exponent" {
            parser("0.123e2") shouldBe ParseSuccess(
                value = JsonNumber(BigDecimal.valueOf(0.123) * BigDecimal.valueOf(100)),
                rest = ""
            )
        }
        "succeed with fraction and negative exponent" {
            parser("0.123e-2") shouldBe ParseSuccess(
                value = JsonNumber(BigDecimal.valueOf(0.123).divide(BigDecimal.valueOf(100))),
                rest = ""
            )
        }
        "succeed with minus zero and fraction" {
            parser("-0.123") shouldBe ParseSuccess(
                value = JsonNumber(-0.123),
                rest = ""
            )
        }
        "succeed with minus zero, fraction and positive exponent" {
            parser("-0.123e2") shouldBe ParseSuccess(
                value = JsonNumber(BigDecimal.valueOf(-0.123) * BigDecimal.valueOf(100)),
                rest = ""
            )
        }
        "succeed with minus zero, fraction and negative exponent" {
            parser("-0.123e-2") shouldBe ParseSuccess(
                value = JsonNumber(BigDecimal.valueOf(-0.123).divide(BigDecimal.valueOf(100))),
                rest = ""
            )
        }
        "succeed with positive exponent" {
            parser("0e2") shouldBe ParseSuccess(
                value = JsonNumber(0),
                rest = ""
            )
        }
        "succeed with negative exponent" {
            parser("0e-3") shouldBe ParseSuccess(
                value = JsonNumber(0),
                rest = ""
            )
        }
        "succeed with minus zero" {
            parser("-0") shouldBe ParseSuccess(
                value = JsonNumber(0),
                rest = ""
            )
        }
    }
})
