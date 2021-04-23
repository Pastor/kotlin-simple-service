import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import java.security.spec.InvalidKeySpecException
import java.util.*
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec


object SaltPassword : Password {
    private val RANDOM: Random = SecureRandom()
    private const val ITERATIONS = 10000
    private const val KEY_LENGTH = 256

    val nextSalt: ByteArray
        get() {
            val salt = ByteArray(16)
            RANDOM.nextBytes(salt)
            return salt
        }

    override fun hash(password: CharArray): Password.Data {
        return hash(password, nextSalt)
    }

    override fun hash(password: CharArray, salt: ByteArray): Password.Data {
        val spec = PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH)
        Arrays.fill(password, Character.MIN_VALUE)
        return try {
            val skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
            Password.Data(skf.generateSecret(spec).encoded, salt)
        } catch (e: NoSuchAlgorithmException) {
            throw AssertionError("Error while hashing a password: " + e.message, e)
        } catch (e: InvalidKeySpecException) {
            throw AssertionError("Error while hashing a password: " + e.message, e)
        } finally {
            spec.clearPassword()
        }
    }

    override fun isExpectedPassword(password: CharArray, data: Password.Data): Boolean {
        val pwdHash = hash(password, data.salt)
        Arrays.fill(password, Character.MIN_VALUE)
        if (pwdHash.hash.size != data.hash.size) return false
        for (i in pwdHash.hash.indices) {
            if (pwdHash.hash[i] != data.hash[i]) return false
        }
        return true
    }

    override fun randomPassword(length: Int): Pair<String, Password.Data> {
        val sb = StringBuilder(length)
        for (i in 0 until length) {
            val c: Int = RANDOM.nextInt(62)
            if (c <= 9) {
                sb.append(c.toString())
            } else if (c < 36) {
                sb.append(('a'.toInt() + c - 10).toChar())
            } else {
                sb.append(('A'.toInt() + c - 36).toChar())
            }
        }
        val password = sb.toString()
        return Pair(password, hash(password.toCharArray()))
    }
}