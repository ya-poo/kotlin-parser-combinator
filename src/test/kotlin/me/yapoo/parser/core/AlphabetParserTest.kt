package me.yapoo.parser.core

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class AlphabetParserTest {
    @Test
    fun lowerAlphabetParseSuccess() {
        val parser = lowerAlphabet()
        ('a'..'z').forEach { lowerAlphabet ->
            parser(lowerAlphabet.toString()) shouldBe ParseSuccess(
                value = lowerAlphabet,
                rest = ""
            )
        }
    }

    @Test
    fun lowerAlphabetParseFailure() {
        val parser = lowerAlphabet()
        ('A'..'Z').forEach { lowerAlphabet ->
            parser(lowerAlphabet.toString()) shouldBe ParseFailure
        }
    }

    @Test
    fun alphabetParseSuccess() {
        val parser = alphabet()

        (('a'..'z') + ('A'..'Z')).forEach { alphabet ->
            parser(alphabet.toString()) shouldBe ParseSuccess(
                value = alphabet,
                rest = ""
            )
        }
    }

    @Test
    fun alphabetParseFailure() {
        val parser = alphabet()

        ('0'..'9').forEach { alphabet ->
            parser(alphabet.toString()) shouldBe ParseFailure
        }
    }
}
