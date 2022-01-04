package me.yapoo.parser.json

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import me.yapoo.parser.core.ParseFailure
import me.yapoo.parser.core.ParseSuccess

class JsonIntegerTest : StringSpec({
    "json integer parser" should {
        val parser = jsonInteger()
        "parse zero" {
            parser("0") shouldBe ParseSuccess(
                value = JsonInteger(0),
                rest = ""
            )
        }
        "parse simple integer" {
            parser("1234") shouldBe ParseSuccess(
                value = JsonInteger(1234),
                rest = ""
            )
        }
        "parse negative integer" {
            parser("-31415") shouldBe ParseSuccess(
                value = JsonInteger(-31415),
                rest = ""
            )
        }
        "fail with plus signed integer" {
            parser("+1234") shouldBe ParseFailure
        }
        "fail with non-integer string" {
            parser("hello") shouldBe ParseFailure
        }
        "fail when after minus sign is not zero" {
            parser("-0123") shouldBe ParseFailure
        }
        "parse only integer" {
            parser("123abc") shouldBe ParseSuccess(
                value = JsonInteger(123),
                rest = "abc"
            )
        }
    }
})
