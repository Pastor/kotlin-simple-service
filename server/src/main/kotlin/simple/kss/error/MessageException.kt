package simple.kss.error

import java.io.Serializable

class MessageException(errorCode: StandardErrorCode, vararg args: Serializable) :
    BaseMessageException(errorCode, *args) {
}