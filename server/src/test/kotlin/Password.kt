interface Password {
    fun hash(password: CharArray): Data
    fun hash(password: CharArray, salt: ByteArray): Data
    fun isExpectedPassword(password: CharArray, data: Data): Boolean
    fun randomPassword(length: Int): Pair<String, Data>

    data class Data(val hash: ByteArray, val salt: ByteArray) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Data

            if (!hash.contentEquals(other.hash)) return false
            if (!salt.contentEquals(other.salt)) return false

            return true
        }

        override fun hashCode(): Int {
            var result = hash.contentHashCode()
            result = 31 * result + salt.contentHashCode()
            return result
        }
    }
}