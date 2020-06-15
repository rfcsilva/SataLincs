package earthExplorer

import java.io.IOException

import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.unmarshalling.Unmarshal
import com.typesafe.config.ConfigFactory
import commons.{Api, JsonObj}
import earthExplorer.templates.requests.LoginData
import play.api.libs.json.Json

import scala.concurrent.Future
import scala.util.{Failure, Success}

object EarthExplorerApi extends Api{

  val base_url : String = ConfigFactory.load().getString("satalincs.earth-explorer.base-url")
  var token: String = _

  override def login(username: String, password: String): Unit = {
    val loginData = new LoginData(username, password)
    val response = post_form(s"${base_url}login", loginData)

    response.onComplete{
      case Success(json) => parseLoginResponse(json)
      case Failure(_)   => sys.error("something wrong")
    }
  }

  def parseLoginResponse(token_data : String): Unit ={
    if(token_data.contains("AUTH_INVALID"))
        throw InvalidAuthenticationException("Wrong username or password")

    token = (Json.parse(token_data) \ "data").as[String]
  }

  override def logout(): Unit = { token = null }

  override def search(): Unit = ???

  override def download(): Unit = ???

  def post_form(url: String, data: JsonObj): Future[String] = {

    val json = data.toJson
    val responseFuture: Future[HttpResponse] = Http().singleRequest(
      HttpRequest(
        uri = url,
        method = HttpMethods.POST,
        entity = FormData(("jsonRequest", json)).toEntity
      ))

    responseFuture.flatMap {
      case HttpResponse(StatusCodes.OK, _, e, _) =>
        Unmarshal(e).to[String]
      case HttpResponse(status, _, e, _) =>
        e.discardBytes() //all entities should be consumed or discarded, so...
        val error = s"Google GeoCoding request failed with status code ${status}"
        Future.failed(new IOException(error))
    }
  }
}
