package me.yapoo.parser.json

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import me.yapoo.parser.core.ParseSuccess

class JsonValueTest : StringSpec({

    "json value parser" should {
        val parser = jsonValue()

        "parse quoted string" {
            parser(
                """
                "hello world"
            """.trimIndent()
            ) shouldBe ParseSuccess(
                value = JsonString("hello world"),
                rest = ""
            )
        }
        "parse integer" {
            parser("123456") shouldBe ParseSuccess(
                value = JsonNumber(123456),
                rest = ""
            )
        }
        "parse number" {
            parser("3.1415") shouldBe ParseSuccess(
                value = JsonNumber(3.1415),
                rest = ""
            )
        }
        "parse boolean" {
            parser("true") shouldBe ParseSuccess(
                value = JsonBoolean(true),
                rest = ""
            )
        }
        "parse null" {
            parser("null") shouldBe ParseSuccess(
                value = JsonNull,
                rest = ""
            )
        }
        "ignore whitespaces" {
            parser("  \"hello world   \"    ") shouldBe ParseSuccess(
                value = JsonString("hello world   "),
                rest = ""
            )
            parser("   null   ") shouldBe ParseSuccess(JsonNull, "")
        }
    }
})
