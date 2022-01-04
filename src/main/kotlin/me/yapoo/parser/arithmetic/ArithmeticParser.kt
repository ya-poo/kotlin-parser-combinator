package me.yapoo.parser.arithmetic

import me.yapoo.parser.core.Parser
import me.yapoo.parser.core.char
import me.yapoo.parser.core.many
import me.yapoo.parser.core.map
import me.yapoo.parser.core.or
import me.yapoo.parser.core.product
import me.yapoo.parser.core.unsignedInteger

/*
<expr>   ::= <term> [ ('+'|'-') <term> ]*
<term>   ::= <factor> [ ('*'|'/') <factor> ]*
<factor> ::= <number> | '(' <expr> ')'
<number> :== 1つ以上の数字
*/

fun arithmeticParser(): Parser<Int> = expressionParser() or { termParser() } or { factorParser() } or { numberParser() }

private fun expressionParser(): Parser<Int> = (termParser() product {
    ((char('+') or { char('-') }) product { termParser() }).many()
}).map { (factor, pl) ->
    pl.fold(factor) { acc, pair ->
        when (pair.first) {
            '+' -> acc + pair.second
            else -> acc - pair.second
        }
    }
}

private fun termParser(): Parser<Int> = (factorParser() product {
    ((char('*') or { char('/') }) product { factorParser() }).many()
}).map { (factor, pl) ->
    pl.fold(factor) { acc, pair ->
        when (pair.first) {
            '*' -> acc * pair.second
            else -> acc / pair.second
        }
    }
}

private fun factorParser(): Parser<Int> = numberParser() or {
    (char('(') product { expressionParser() } product { char(')') }).map {
        it.first.second
    }
}

private fun numberParser(): Parser<Int> = unsignedInteger()
