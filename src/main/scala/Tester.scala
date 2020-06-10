import utils.HttpClient.POST_FORM

object Tester {
  def main(args: Array[String]): Unit = {
    POST_FORM(url = "https://earthexplorer.usgs.gov/inventory/json/v/1.4.0/login", Map("username"->"andrelopes",
      "password" -> "andrelopes14", "authType" -> "", "catalogId" -> "EE" ))
  }
//{"username": "rfc.silva", "password": "Rubencristianas2", "authType": "", "catalogId": "EE"}
}
