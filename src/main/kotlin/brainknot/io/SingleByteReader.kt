package brainknot.io

import java.io.InputStreamReader

object SingleByteReader {
    private val reader = InputStreamReader(System.`in`)

    fun getchar(): Byte {
        return reader.read().toByte()
    }

}