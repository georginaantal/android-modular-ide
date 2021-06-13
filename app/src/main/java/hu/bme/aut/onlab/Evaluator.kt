package hu.bme.aut.onlab

import java.util.*

interface Expr {
    fun eval(): Double
    enum class Op {
        UNARY_MINUS, UNARY_LOGICAL_NOT, UNARY_BITWISE_NOT, POWER, MULTIPLY, DIVIDE, REMAINDER, PLUS, MINUS, SHL, SHR, LT, LE, GT, GE, EQ, NE, BITWISE_AND, BITWISE_OR, BITWISE_XOR, LOGICAL_AND, LOGICAL_OR, ASSIGN, COMMA;

        companion object {
            fun isUnary(op: Op?): Boolean {
                return op == UNARY_MINUS || op == UNARY_LOGICAL_NOT || op == UNARY_BITWISE_NOT
            }

            fun isLeftAssoc(op: Op?): Boolean {
                return !isUnary(op) && op != ASSIGN && op != POWER && op != COMMA
            }
        }
    }

    class Const(private val value: Double) : Expr {
        override fun eval(): Double {
            return value
        }

        override fun toString(): String {
            return "#" + value
        }
    }

    class Var(value: Double) : Expr {
        private var value = 0.0
        fun set(value: Double) {
            this.value = value
        }

        override fun eval(): Double {
            return value
        }

        override fun toString(): String {
            return "{" + value + "}"
        }

        init {
            this.set(value)
        }
    }

    class Unary(private val op: Op?, private val arg: Expr?) : Expr {
        override fun eval(): Double {
            when (op) {
                Op.UNARY_MINUS -> return -arg!!.eval()
                Op.UNARY_BITWISE_NOT -> return arg!!.eval().toInt().inv().toDouble()
                Op.UNARY_LOGICAL_NOT -> return if (arg!!.eval().toInt() == 0) 1.0 else 0.0
            }
            return 0.0
        }
    }

    class Binary(val op: Op?, val a: Expr, val b: Expr) : Expr {
        override fun eval(): Double {
            when (op) {
                Op.POWER -> return Math.pow(a.eval(), b.eval())
                Op.MULTIPLY -> return a.eval() * b.eval()
                Op.DIVIDE -> return a.eval() / b.eval()
                Op.REMAINDER, Op.PLUS -> return a.eval() + b.eval()
                Op.MINUS -> return a.eval() - b.eval()
                Op.SHL -> return (a.eval().toInt() shl b.eval().toInt()).toDouble()
                Op.SHR -> return (a.eval().toInt() ushr b.eval().toInt()).toDouble()
                Op.LT -> return if (a.eval() < b.eval()) 1.0 else 0.0
                Op.LE -> return if (a.eval() <= b.eval()) 1.0 else 0.0
                Op.GT -> return if (a.eval() > b.eval()) 1.0 else 0.0
                Op.GE -> return if (a.eval() >= b.eval()) 1.0 else 0.0
                Op.EQ -> return if (a.eval() == b.eval()) 1.0 else 0.0
                Op.NE -> return if (a.eval() != b.eval()) 1.0 else 0.0
                Op.BITWISE_AND -> return (a.eval().toInt() and b.eval().toInt()).toDouble()
                Op.BITWISE_OR -> return (a.eval().toInt() or b.eval().toInt()).toDouble()
                Op.BITWISE_XOR -> return (a.eval().toInt() xor b.eval().toInt()).toDouble()
                Op.LOGICAL_AND -> return if (a.eval() != 0.0) b.eval().toDouble() else 0.0
                Op.LOGICAL_OR -> {
                    val cond = a.eval().toDouble()
                    return if (cond != 0.0) cond else b.eval().toDouble()
                }
                Op.ASSIGN -> {
                    val rhs = b.eval()
                    (a as Var).set(rhs)
                    return rhs
                }
                Op.COMMA -> {
                    a.eval()
                    return b.eval()
                }
            }
            return 0.0
        }
    }

    interface Func<T> {
        fun eval(c: FuncContext<T>?): Double
    }

    class FuncContext<T>(val f: Func<T>, val args: List<Expr>, val vars: Map<String, Var>) : Expr {
        var env: T? = null
        override fun eval(): Double {
            return f.eval(this)
        }
    }

    class Builder {
        fun tokenize(input: String): List<String>? {
            var pos = 0
            var expected = TOK_OPEN or TOK_NUMBER or TOK_WORD
            val tokens: MutableList<String> = ArrayList()
            while (pos < input.length) {
                var tok = ""
                var c = input[pos]
                if (Character.isWhitespace(c)) {
                    pos++
                    continue
                }
                if (Character.isDigit(c)) {
                    if (expected and TOK_NUMBER == 0) {
                        return null // unexpected number
                    }
                    expected = TOK_OP or TOK_CLOSE
                    while ((c == '.' || Character.isDigit(c)) && pos < input.length) {
                        tok = tok + input[pos]
                        pos++
                        c = if (pos < input.length) {
                            input[pos]
                        } else {
                            0.toChar()
                        }
                    }
                } else if (Character.isLetter(c)) {
                    if (expected and TOK_WORD == 0) {
                        return null // Unexpected identifier
                    }
                    expected = TOK_OP or TOK_OPEN or TOK_CLOSE
                    while ((Character.isLetter(c) || Character.isDigit(c) || c == '_') && pos < input.length) {
                        tok = tok + input[pos]
                        pos++
                        c = if (pos < input.length) {
                            input[pos]
                        } else {
                            0.toChar()
                        }
                    }
                } else if (c == '(' || c == ')') {
                    tok = tok + c
                    pos++
                    expected = if (c == '(' && expected and TOK_OPEN != 0) {
                        TOK_NUMBER or TOK_WORD or TOK_OPEN or TOK_CLOSE
                    } else if (c == ')' && expected and TOK_CLOSE != 0) {
                        TOK_OP or TOK_CLOSE
                    } else {
                        return null // Parens mismatch
                    }
                } else {
                    if (expected and TOK_OP == 0) {
                        if (c != '-' && c != '^' && c != '!') {
                            return null // Missing operand
                        }
                        tok = tok + c + 'u'
                        pos++
                    } else {
                        var lastOp: String? = null
                        while (!Character.isLetter(c) && !Character.isDigit(c) && !Character.isWhitespace(c) && c != '_' && c != '(' && c != ')' && pos < input.length) {
                            if (OPS.containsKey(tok + input[pos])) {
                                tok = tok + input[pos]
                                lastOp = tok
                            } else if (lastOp == null) {
                                tok = tok + input[pos]
                            } else {
                                break
                            }
                            pos++
                            c = if (pos < input.length) {
                                input[pos]
                            } else {
                                0.toChar()
                            }
                        }
                        if (lastOp == null) {
                            return null // Bad operator
                        }
                    }
                    expected = TOK_NUMBER or TOK_WORD or TOK_OPEN
                }
                tokens.add(tok)
            }
            return tokens
        }

        fun parse(s: String, vars: MutableMap<String, Var>?, funcs: Map<String?, Func<*>>?): Expr? {
            var vars = vars
            if (vars == null) {
                vars = HashMap()
            }
            val os = Stack<String>()
            val es = Stack<Expr?>()
            var paren = PAREN_ALLOWED
            val tokens = tokenize(s) ?: return null
            for (token in tokens) {
                var parenNext = PAREN_ALLOWED
                if (token == "(") {
                    if (paren == PAREN_EXPECTED) {
                        os.push("{")
                    } else if (paren == PAREN_ALLOWED) {
                        os.push("(")
                    } else {
                        return null // bad call
                    }
                } else if (paren == PAREN_EXPECTED) {
                    return null // bad call
                } else if (token == ")") {
                    while (!os.isEmpty() && os.peek() != "(" && os.peek() != "{") {
                        val e = bind(os.pop(), es) ?: return null
                        es.push(e)
                    }
                    if (os.isEmpty()) {
                        return null // Bad paren
                    }
                    if (os.pop() == "{") {
                        val f = funcs!![os.pop()]
                        val args: MutableList<Expr> = ArrayList()
                        if (!es.isEmpty()) {
                            var e = es.pop()
                            while (e != null) {
                                if (e is Binary) {
                                    val binExpr = e
                                    if (binExpr.op == Op.COMMA) {
                                        args.add(binExpr.a)
                                        e = binExpr.b
                                        continue
                                    }
                                }
                                args.add(e)
                                break
                            }
                        }
                        es.push(FuncContext<Any?>(f as Func<Any?>, args, vars))
                    }
                    parenNext = PAREN_FORBIDDEN
                } else if (parseDouble(token) != null) {
                    es.push(Const(parseDouble(token)!!))
                    parenNext = PAREN_FORBIDDEN
                } else if (funcs != null && funcs.containsKey(token)) {
                    os.push(token)
                    parenNext = PAREN_EXPECTED
                } else if (OPS.containsKey(token)) {
                    val op = OPS[token]
                    var o2 = if (os.isEmpty()) null else os.peek()
                    while (OPS.containsKey(o2) && (Op.isLeftAssoc(op) &&
                                    op!!.ordinal > OPS[o2]!!.ordinal || op!!.ordinal > OPS[o2]!!.ordinal)) {
                        val e = bind(o2, es) ?: return null
                        es.push(e)
                        os.pop()
                        o2 = if (os.isEmpty()) null else os.peek()
                    }
                    os.push(token)
                } else {
                    if (vars.containsKey(token)) {
                        es.push(vars[token])
                    } else {
                        val v = Var(0.0)
                        vars[token] = v
                        es.push(v)
                    }
                    parenNext = PAREN_FORBIDDEN
                }
                paren = parenNext
            }
            if (paren == PAREN_EXPECTED) {
                return null // Bad call
            }
            while (!os.isEmpty()) {
                val op = os.pop()
                if (op == "(" || op == ")") {
                    return null // Bad paren
                }
                val e = bind(op, es) ?: return null
                es.push(e)
            }
            return if (es.isEmpty()) {
                Const(0.0)
            } else {
                es.pop()
            }
        }

        private fun bind(s: String?, stack: Stack<Expr?>): Expr? {
            return if (OPS.containsKey(s)) {
                val op = OPS[s]
                if (Op.isUnary(op)) {
                    if (!stack.isEmpty() && stack.peek() == null) {
                        null // Operand missing
                    } else Unary(op, stack.pop())
                } else {
                    val b = stack.pop()
                    val a = stack.pop()
                    if (a == null || b == null) {
                        null // Operand missing
                    } else Binary(op, a, b)
                }
            } else {
                null // Bad call
            }
        }

        private fun parseDouble(s: String): Double? {
            return try {
                s.toDouble()
            } catch (e: NumberFormatException) {
                null
            }
        }

        companion object {
            private const val TOK_NUMBER = 1
            private const val TOK_WORD = 2
            private const val TOK_OP = 4
            private const val TOK_OPEN = 8
            private const val TOK_CLOSE = 16
            private const val PAREN_ALLOWED = 0
            private const val PAREN_EXPECTED = 1
            private const val PAREN_FORBIDDEN = 2
            val OPS: Map<String?, Op?> = object : HashMap<String?, Op?>() {
                init {
                    put("-u", Op.UNARY_MINUS)
                    put("!u", Op.UNARY_LOGICAL_NOT)
                    put("^u", Op.UNARY_BITWISE_NOT)
                    put("**", Op.POWER)
                    put("*", Op.MULTIPLY)
                    put("/", Op.DIVIDE)
                    put("%", Op.REMAINDER)
                    put("+", Op.PLUS)
                    put("-", Op.MINUS)
                    put("<<", Op.SHL)
                    put(">>", Op.SHR)
                    put("<", Op.LT)
                    put("<=", Op.LE)
                    put(">", Op.GT)
                    put(">=", Op.GE)
                    put("==", Op.EQ)
                    put("!=", Op.NE)
                    put("&", Op.BITWISE_AND)
                    put("^", Op.BITWISE_XOR)
                    put("|", Op.BITWISE_OR)
                    put("&&", Op.LOGICAL_AND)
                    put("||", Op.LOGICAL_OR)
                    put("=", Op.ASSIGN)
                    put(",", Op.COMMA)
                }
            }
        }
    }
}