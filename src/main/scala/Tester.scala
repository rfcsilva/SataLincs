import earthExplorer.templates.requests.LoginData
import earthExplorer.{EarthExplorerApi, InvalidAuthenticationException}

object Tester {
  def main(args: Array[String]): Unit = {
    EarthExplorerApi.login("user", "pass")
    EarthExplorerApi.close()
  }
}
