package me.yapoo.parser.json

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import me.yapoo.parser.core.ParseSuccess

class JsonObjectTest : StringSpec({

    "json object parser" should {
        val parser = jsonObject()

        "parse empty object" {
            parser("{  \n\t  \r     }") shouldBe ParseSuccess(
                value = JsonObject(emptyMap()),
                rest = ""
            )
        }
        "parse single item object" {
            parser(
                """
                {
                    "this is key" : "this is value"
                }
            """.trimIndent()
            ) shouldBe ParseSuccess(
                value = JsonObject(
                    "this is key" to JsonString("this is value")
                ),
                rest = ""
            )
        }
        "parse multi item object" {
            parser(
                """
                {
                    "string": "hello world",
                    "number": 3,
                    "null"  : null,
                    "array": [1,2,3,[4]],
                    "object": {
                        "number": 5
                    }
                }
            """.trimIndent()
            ) shouldBe ParseSuccess(
                value = JsonObject(
                    "string" to JsonString("hello world"),
                    "number" to JsonNumber(3),
                    "null" to JsonNull,
                    "array" to JsonArray(
                        JsonNumber(1),
                        JsonNumber(2),
                        JsonNumber(3),
                        JsonArray(
                            JsonNumber(4)
                        )
                    ),
                    "object" to JsonObject(
                        "number" to JsonNumber(5)
                    )
                ),
                rest = ""
            )
        }
    }
})
