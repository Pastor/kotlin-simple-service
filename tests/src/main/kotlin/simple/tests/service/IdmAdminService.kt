package simple.tests.service

import org.keycloak.OAuth2Constants
import org.keycloak.admin.client.Keycloak
import org.keycloak.admin.client.KeycloakBuilder
import org.keycloak.admin.client.resource.RealmResource
import org.keycloak.admin.client.resource.UsersResource
import simple.tests.IdmService

data class KeycloakCredentials(
    val serverUrl: String,
    val realm: String,
    val clientId: String,
    val clientSecret: String,

    val username: String,
    val password: String,
)

class IdmAdminService(credentials: KeycloakCredentials) : IdmService {
    private val keycloak: Keycloak = KeycloakBuilder.builder()
        .serverUrl(credentials.serverUrl)
        .realm(credentials.realm)
        .grantType(OAuth2Constants.PASSWORD)
        .clientId(credentials.clientId)
        .clientSecret(credentials.clientSecret)
        .username(credentials.username)
        .password(credentials.password)
        .build()
    private val realmResource = keycloak.realm(credentials.realm)
    override fun realm(): RealmResource = realmResource
    override fun users(): UsersResource = realmResource.users()
}