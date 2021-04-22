package simple.tests

import org.keycloak.admin.client.resource.RealmResource
import org.keycloak.admin.client.resource.UsersResource

interface IdmService {
    fun realm(): RealmResource
    fun users(): UsersResource
}