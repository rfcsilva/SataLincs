package earthExplorer

import java.io.IOException

import akka.actor.{ActorSystem, ClassicActorSystemProvider}
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.{Materializer, SystemMaterializer}
import com.typesafe.config.ConfigFactory
import commons.Api
import play.api.libs.json.{JsValue, Json}
import utils.utils

import scala.concurrent.{ExecutionContextExecutor, Future}
import scala.util.{Failure, Success}

object EarthExplorerApi extends Api{

  implicit val system: ActorSystem = ActorSystem()
  /**
   * Implicitly provides the system wide materializer from a classic or typed `ActorSystem`
   */
  implicit def matFromSystem(implicit provider: ClassicActorSystemProvider): Materializer =
    SystemMaterializer(provider.classicSystem).materializer
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher

  val base_url : String = ConfigFactory.load().getString("satalincs.earth-explorer.base-url")
  var token: String = null

  override def login(username: String, password: String): Unit = {
    val loginData = Map("username" -> username, "password" -> password, "authType" -> "EROS", "catalogId" -> "EE")
    println(s"$base_url/login")
    val response = post_form(s"${base_url}login", loginData)

    response.onComplete{
      case Success(json) => parseLoginResponse(json)
      case Failure(_)   => sys.error("something wrong")
    }

  }

  def parseLoginResponse(token_data : String): Unit ={

    val res_js = Json.parse(token_data)
    if(token_data.contains("AUTH_INVALID"))
        throw InvalidAuthenticationException("Wrong username or password")

    token = (res_js \ "data").as[String]
  }

  def refreshToken(): Unit = ???

  override def logout(): Unit = ???

  override def search(): Unit = ???

  override def download(): Unit = ???

  def post_form(url: String, data: Map[String, String]): Future[String] = {

    val json = utils.toJson(data) match {
      case Some(data) => data
      case None => "{}"
    }

    val responseFuture: Future[HttpResponse] = Http().singleRequest(
      HttpRequest(
        uri = url,
        method = HttpMethods.POST,
        entity = FormData(("jsonRequest", json)).toEntity
      ))

    val result: Future[String] = responseFuture.flatMap {
      case HttpResponse(StatusCodes.OK, _, e, _) =>
        Unmarshal(e).to[String]
      case HttpResponse(status, _, e, _) =>
        e.discardBytes() //all entities should be consumed or discarded, so...
        val error = s"Google GeoCoding request failed with status code ${status}"
        Future.failed(new IOException(error))
    }
    result
  }
}
