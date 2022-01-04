package me.yapoo.parser.json

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import me.yapoo.parser.core.ParseSuccess

class JsonArrayTest : StringSpec({

    "json array parser" should {
        val parser = jsonArray()

        "parse empty array" {
            parser("[   \t  \n  \r ]") shouldBe ParseSuccess(
                value = JsonArray(emptyList()),
                rest = ""
            )
        }

        "parse single item array" {
            parser("[    false   ]") shouldBe ParseSuccess(
                value = JsonArray(JsonBoolean(false)),
                rest = ""
            )
        }

        "parse non empty array" {
            parser(
                """
                [
                1,  5, [3,4,5], true, null
                ]
            """.trimIndent()
            ) shouldBe ParseSuccess(
                value = JsonArray(
                    JsonNumber(1),
                    JsonNumber(5),
                    JsonArray(
                        JsonNumber(3),
                        JsonNumber(4),
                        JsonNumber(5)
                    ),
                    JsonBoolean(true),
                    JsonNull
                ),
                rest = ""
            )
        }
    }
})
