package me.yapoo.parser.json

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import me.yapoo.parser.core.ParseSuccess

class JsonNumberTest : StringSpec({
    "json number parser" should {
        val parser = jsonNumber()

        "succeed with single integer" {
            parser("1234") shouldBe ParseSuccess(
                value = JsonNumber(1234.0),
                rest = ""
            )
        }
        "succeed with integer, fraction" {
            parser("1234.56") shouldBe ParseSuccess(
                value = JsonNumber(1234.56),
                rest = ""
            )
        }
        "succeed with integer, fraction and exponent" {
            parser("1234.56e+1") shouldBe ParseSuccess(
                value = JsonNumber(12345.6),
                rest = ""
            )
        }
    }
})
