package me.yapoo.parser

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.char
import io.kotest.property.arbitrary.filter
import io.kotest.property.arbitrary.string
import io.kotest.property.checkAll
import me.yapoo.parser.core.PrimitiveParsers.anyChar
import me.yapoo.parser.core.ParseFailure
import me.yapoo.parser.core.ParseSuccess
import me.yapoo.parser.core.Parsers.char

class CharParserTest : StringSpec({

    "parse any char" should {
        val parser = anyChar()
        "success with nonEmpty string" {
            checkAll(Arb.string(minSize = 1)) { string ->
                parser(string) shouldBe ParseSuccess(
                    value = string[0],
                    rest = string.drop(1)
                )
            }
        }

        "fail with empty string" {
            parser("") shouldBe ParseFailure
        }
    }

    "parse char" should {
        val parser = char('a')

        "success with `a`" {
            parser("abcd") shouldBe ParseSuccess(
                value = 'a',
                rest = "bcd"
            )
        }
        "fail other than `a`" {
            checkAll(Arb.char().filter { it != 'a' }) { c ->
                parser("$c") shouldBe ParseFailure
            }
        }
    }

    "parse char with predicate" should {
        val parser = char { c ->
            c in listOf('a', 'i', 'u', 'e', 'o')
        }
        "success with matched char" {
            parser("abcd") shouldBe ParseSuccess(
                value = 'a',
                rest = "bcd"
            )
        }
        "fail when false" {
            parser("bcde") shouldBe ParseFailure
        }
    }
})
