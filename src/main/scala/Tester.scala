import earthExplorer.templates.requests.LoginData
import earthExplorer.{EarthExplorerApi, InvalidAuthenticationException}

object Tester {
  def main(args: Array[String]): Unit = {
    EarthExplorerApi.login("rfc.silva", "Rubencristianas2")
    EarthExplorerApi.close()
  }
//{"username": "rfc.silva", "password": "Rubencristianas2", "authType": "", "catalogId": "EE"}
}
