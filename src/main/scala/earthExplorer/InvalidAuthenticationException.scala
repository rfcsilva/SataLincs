package earthExplorer

final case class InvalidAuthenticationException(private val message: String = "",
                                 private val cause: Throwable = None.orNull)
  extends Exception(message, cause)