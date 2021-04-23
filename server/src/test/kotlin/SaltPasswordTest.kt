import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Работа с паролями")
internal class SaltPasswordTest {
    private lateinit var password: Pair<String, Password.Data>

    @BeforeEach
    internal fun setUp() {
        password = SaltPassword.randomPassword(10)
    }

    @DisplayName("Проверка пароля")
    @Test
    internal fun check() {
        assertTrue(SaltPassword.isExpectedPassword(password = password.first.toCharArray(), password.second))
    }
}