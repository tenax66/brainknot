package brainknot

import brainknot.io.SingleByteReader
import java.io.File

/*
 * > : Increment the data pointer.
 * < : Decrement the data pointer.
 * + : Increment the byte at the data pointer.
 * - : Decrement the byte at the data pointer.
 * . : Output the byte at the data pointer.
 * , : Accept input, storing its value in the byte at the data pointer.
 * [ : goto ']' if the byte at the data pointer is 0.
 * ] : goto'[' if the byte at the data pointer is not 0.
 */

const val MEMORY_SIZE: Int = 30000

var memory: ByteArray = ByteArray(MEMORY_SIZE) { 0 }
var ptr = 0

val stack = ArrayDeque<Int>()

fun main(args: Array<String>) {
    if (args.size > 1) {
        throw IllegalArgumentException("Too many arguments.")
    }

    val inputFile = File(args[0])
    val code: String = inputFile.readText()

    var i = 0
    while (i < code.length) {
        when (code[i]) {
            '[' -> {
                if (memory[ptr] != 0.toByte()) {
                    stack.add(i)
                } else {
                    i++
                    while (stack.isNotEmpty()) {
                        if (code[i] == '[') {
                            stack.add(i)
                        } else if (code[i] == ']') {
                            stack.removeLast()
                        }
                        i++
                    }
                }
            }

            ']' -> {
                if (memory[ptr] != 0.toByte()) {
                    i = stack.last()
                } else {
                    stack.removeLast()
                }
            }

            else -> interpret(code[i])
        }
        i++
    }
}

fun interpret(ch: Char) {
    when (ch) {
        '>' -> ++ptr
        '<' -> --ptr
        '+' -> ++memory[ptr]
        '-' -> --memory[ptr]
        '.' -> print(memory[ptr].toInt().toChar())
        ',' -> memory[ptr] = SingleByteReader.getchar()
    }
}
