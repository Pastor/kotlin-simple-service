package simple.kss.error

enum class StandardErrorCode(
    private val httpCodeId: Int,
    val namespace: Namespace,
    val code: Int,
    private val messageCodeId: String
) : ErrorCode {
    UNKNOWN(500, Namespace.COMMON, 0, "Common.Error.Unknown"), PARAMETER_NOT_FOUND(
        400,
        Namespace.COMMON,
        1,
        "Common.Error.Parameter"
    ),
    NOT_GET_RESOURCE(406, Namespace.COMMON, 2, "Common.Error.NotGetResource"), UNSUPPORTED_OPERATION(
        406,
        Namespace.COMMON,
        3,
        "Common.Error.UnsupportedOperation"
    ),
    NOT_AUTHENTICATED(401, Namespace.SECURITY, 1, "Security.Error.NotAuthenticated"), PERMISSION_DENIED(
        403,
        Namespace.SECURITY,
        2,
        "Security.Error.PermissionDenied"
    ),
    PERMISSION_DENIED_SCOPE(403, Namespace.SECURITY, 3, "Security.Error.PermissionDeniedScope"), ENTITY_NOT_FOUND(
        404,
        Namespace.BUSINESS,
        1,
        "Business.Error.NotFound"
    ),
    PARAMETER_ILLEGAL_FORMAT(
        400,
        Namespace.BUSINESS,
        2,
        "Business.Error.ParameterIllegalFormat"
    ),
    OBJECT_TRANSFORM_FAULT(500, Namespace.BUSINESS, 3, "Business.Error.ObjectTransformFault"), DATABASE_REQUEST_FAULT(
        500,
        Namespace.BUSINESS,
        4,
        "Business.Error.DatabaseRequestFault"
    ),
    DATABASE_SYNTAX_FAULT(500, Namespace.BUSINESS, 5, "Business.Error.DatabaseSyntaxFault"), SCHEMA_GENERATE_FAULT(
        500,
        Namespace.BUSINESS,
        6,
        "Business.Error.SchemaGenerateFault"
    ),
    VALIDATION_OBJECT_FAULT(
        400,
        Namespace.BUSINESS,
        7,
        "Business.Error.ValidationObjectFault"
    ),
    DATABASE_DATA_DAMAGED_FAULT(500, Namespace.BUSINESS, 8, "Business.Error.DatabaseDataDamagedFault");

    override fun generateCode(): String {
        return String.format("%s-%02d%04d", namespace.name, namespace.code, code)
    }

    override fun messageCode(): String {
        return messageCodeId
    }

    override fun httpCode(): Int {
        return httpCodeId
    }

    override fun toString(): String {
        return generateCode()
    }

    enum class Namespace(val internalName: String, val code: Int) {
        COMMON("ERB", 0), SECURITY("ERB", 1), BUSINESS("ERB", 2);
    }
}