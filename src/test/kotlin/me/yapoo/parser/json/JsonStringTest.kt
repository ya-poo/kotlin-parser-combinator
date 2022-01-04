package me.yapoo.parser.json

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import me.yapoo.parser.core.ParseFailure
import me.yapoo.parser.core.ParseSuccess

class JsonStringTest : StringSpec({

    "unicode parser" should {
        val parser = unicode()

        "parse utf8 char" {
            parser("""\u002f""") shouldBe ParseSuccess(
                value = '/',
                rest = ""
            )
        }
        "parse case-insensitively" {
            parser("""\u002F""") shouldBe parser("""\u002f""")
        }
    }

    "json string parser" should {
        val parser = jsonString()

        "fail with unquoted string" {
            parser("hello world") shouldBe ParseFailure
        }
        "succeed with unquoted string" {
            parser("\"hello\\tworld\"") shouldBe ParseSuccess(
                value = "hello\tworld".let(::JsonString),
                rest = ""
            )
        }
        "fail with invalid escape" {
            parser("\"a\\a\"") shouldBe ParseFailure
        }
        "succeed with escaped characters" {
            parser(
                """
                "\b\t\n\f\r\"/\\\\"
            """.trimIndent()
            ) shouldBe ParseSuccess(
                value = "\b\t\n\u000C\r\"/\\\\".let(::JsonString),
                rest = ""
            )
        }
        "succeed with [////]" {
            parser(
                """
                    "[//\u002F\u002f]"
                """.trimIndent()
            ) shouldBe ParseSuccess(
                value = "[////]".let(::JsonString),
                rest = ""
            )
        }
        "succeed with emoji 😀" {
            parser(
                """
                    "\ud83d\uDE00"
                """.trimIndent()
            ) shouldBe ParseSuccess(
                value = "😀".let(::JsonString),
                rest = ""
            )
        }
    }
})
