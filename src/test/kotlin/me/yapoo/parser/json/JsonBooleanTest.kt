package me.yapoo.parser.json

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import me.yapoo.parser.core.ParseFailure
import me.yapoo.parser.core.ParseSuccess

class JsonBooleanTest : StringSpec({
    "json boolean parser" should {
        val parser = jsonBoolean()

        "parse true" {
            parser("true") shouldBe ParseSuccess(
                value = JsonBoolean(true),
                rest = ""
            )
        }
        "parse false" {
            parser("false") shouldBe ParseSuccess(
                value = JsonBoolean(false),
                rest = ""
            )
        }
        "fail with upper case boolean" {
            parser("False") shouldBe ParseFailure
        }
    }
})
