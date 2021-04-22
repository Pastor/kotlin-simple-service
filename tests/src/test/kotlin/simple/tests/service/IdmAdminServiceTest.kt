package simple.tests.service

import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.keycloak.admin.client.CreatedResponseUtil
import org.keycloak.admin.client.resource.UserResource
import org.keycloak.representations.idm.ClientRepresentation
import org.keycloak.representations.idm.CredentialRepresentation
import org.keycloak.representations.idm.RoleRepresentation
import org.keycloak.representations.idm.UserRepresentation
import simple.tests.IdmService
import java.util.*
import javax.ws.rs.core.Response


@DisplayName("Сервис управления IDM")
internal class IdmAdminServiceTest {
    var idmService: IdmService? = null

    @BeforeEach
    fun setUp() {
        idmService = IdmAdminService(
            KeycloakCredentials(
                "http://127.0.0.1:8080/auth",
                "master",
                "test",
                "947c9a1b-71f8-49fe-9b1b-2623a8fd7e1b",
                "admin",
                "admin"
            )
        )
    }

    @DisplayName("Получение списка пользователей")
    @Test
    fun users() {
        val users = idmService?.users()
        assertNotNull(users)
        val list = users?.list()
        assertNotNull(list)
    }

    @DisplayName("Создание пользователя Петя")
    @Test
    fun create() {

        val user = UserRepresentation()
        user.isEnabled = true
        user.isEmailVerified = true
        user.username = "peter"
        user.firstName = "Петя"
        user.lastName = "Васечкин"
        user.email = "peter+chen@example.com"
        user.attributes = Collections.singletonMap("origin", listOf("demo"))

        val users = idmService!!.users()
        assertNotNull(users)
        val search = users.search(user.username)
        if (search.isNotEmpty()) {
            users.delete(search[0].id)
        }
        val realm = idmService!!.realm()
        val response: Response = users.create(user)
        System.out.printf("Repsonse: %s %s%n", response.status, response.statusInfo)
        println(response.location)
        val userId = CreatedResponseUtil.getCreatedId(response)
        System.out.printf("User created with userId: %s%n", userId);
        val passwordCred = CredentialRepresentation()
        passwordCred.isTemporary = false
        passwordCred.type = CredentialRepresentation.PASSWORD
        passwordCred.value = "test"
        val userResource: UserResource = users.get(userId)
        userResource.resetPassword(passwordCred)
        val testerRealmRole: RoleRepresentation = realm.roles().get("offline_access").toRepresentation()
        userResource.roles().realmLevel().add(listOf(testerRealmRole))
        val app1Client: ClientRepresentation = realm.clients().findByClientId("test")[0]
        val userClientRole: RoleRepresentation = realm.clients().get(app1Client.id)
            .roles().get("uma_protection").toRepresentation()
        userResource.roles().clientLevel(app1Client.id).add(listOf(userClientRole))
    }
}