package earthExplorer

import com.typesafe.config.ConfigFactory
import commons.Api

class EarthExplorerApi extends Api{
  val base_url : String = ConfigFactory.load().getString("satalincs.earth-explorer.base-url")
  val token : String = None[String]

  override def login(username: String, password: String): Unit = {

  }

  def refreshToken(): Unit = ???

  override def logout(): Unit = ???

  override def search(): Unit = ???

  override def download(): Unit = ???


}
