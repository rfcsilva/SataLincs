package earthExplorer

import com.typesafe.config.ConfigFactory

class Search() {
  val base_url : String = ConfigFactory.load().getString("satalincs.earth-explorer.base-url")

  def login(username: String, password: String): Unit ={

  }
}
