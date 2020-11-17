package earthExplorer.templates.replies

case object AUTH_INVALID extends Enumeration

class LoginReply(val errorCode: Enumeration,
                 val error: String,
                 val data: Any,
                 val api_version: String,
                 val access_level: String,
                 val catalog_id: String,
                 val executionTime: Double)
