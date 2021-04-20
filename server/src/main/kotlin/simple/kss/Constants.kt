package simple.kss

import javax.ws.rs.core.MediaType

object Constants {
    const val REQUEST_ID = "request-id"
    const val VERSION_1 = "v1"
    const val CURRENT_VERSION = VERSION_1
    const val UUID_REGEXP = "^([a-f0-9]{8}(-[a-f0-9]{4}){4}[a-f0-9]{8})$"
    const val APPLICATION_JSON_UTF8 = MediaType.APPLICATION_JSON + ";charset=utf-8"
    const val APPLICATION_XML_UTF8 = MediaType.APPLICATION_XML + ";charset=utf-8"
    const val LOCAL_DATE_FORMAT_PATTERN = "yyyy-MM-dd"
    fun byVersion(name: String): String {
        return name + "_" + CURRENT_VERSION
    }
}