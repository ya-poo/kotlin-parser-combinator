package me.yapoo.parser

import io.kotest.matchers.shouldBe
import me.yapoo.parser.core.ParseFailure
import me.yapoo.parser.core.ParseSuccess
import me.yapoo.parser.core.Parsers
import org.junit.jupiter.api.Test

class AlphabetParserTest {
    @Test
    fun lowerAlphabetParseSuccess() {
        val parser = Parsers.lowerAlphabet()
        ('a'..'z').forEach { lowerAlphabet ->
            parser(lowerAlphabet.toString()) shouldBe ParseSuccess(
                value = lowerAlphabet,
                rest = ""
            )
        }
    }

    @Test
    fun lowerAlphabetParseFailure() {
        val parser = Parsers.lowerAlphabet()
        ('A'..'Z').forEach { lowerAlphabet ->
            parser(lowerAlphabet.toString()) shouldBe ParseFailure
        }
    }

    @Test
    fun alphabetParseSuccess() {
        val parser = Parsers.alphabet()

        (('a'..'z') + ('A'..'Z')).forEach { alphabet ->
            parser(alphabet.toString()) shouldBe ParseSuccess(
                value = alphabet,
                rest = ""
            )
        }
    }

    @Test
    fun alphabetParseFailure() {
        val parser = Parsers.alphabet()

        ('0'..'9').forEach { alphabet ->
            parser(alphabet.toString()) shouldBe ParseFailure
        }
    }
}
