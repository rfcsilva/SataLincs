package utils

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.stream.ActorMaterializer
import akka.util.ByteString
import com.fasterxml.jackson.databind.{DeserializationFeature, ObjectMapper}
import com.fasterxml.jackson.module.scala.{DefaultScalaModule, ScalaObjectMapper}

import scala.concurrent.{ExecutionContextExecutor, Future}
import scala.util.{Failure, Success, Try}


object HttpClient {

  def POST_FORM(url: String, data: Map[String, String]): Unit ={
    implicit val system: ActorSystem = ActorSystem()
    implicit val materializer: ActorMaterializer = ActorMaterializer()
    // needed for the future flatMap/onComplete in the end
    implicit val executionContext: ExecutionContextExecutor = system.dispatcher

    val json = toJson(data) match {
      case Some(data) => data
      case None => "{}"
    }

    println(json)
    val responseFuture: Future[HttpResponse] = Http().singleRequest(HttpRequest(uri = url, method = HttpMethods.POST, entity = FormData(("jsonRequest", json)).toEntity))

    responseFuture
      .onComplete {
        case Success(res) => res.entity.dataBytes.runFold(ByteString(""))(_ ++ _).foreach { body => println(body.utf8String) }
        case Failure(_)   => sys.error("something wrong")
      }
  }

  def POST(url: String, data: Map[String, String]): Unit ={
    implicit val system: ActorSystem = ActorSystem()
    implicit val materializer: ActorMaterializer = ActorMaterializer()
    // needed for the future flatMap/onComplete in the end
    implicit val executionContext: ExecutionContextExecutor = system.dispatcher

    val json = toJson(data) match {
      case Some(data) => data
      case None => "{}"
    }
    val responseFuture: Future[HttpResponse] = Http().singleRequest(HttpRequest(uri = url))

    responseFuture
      .onComplete {
        case Success(res) => println(res.status)
        case Failure(_)   => sys.error("something wrong")
      }
  }

  def toJson[T](obj: T)(implicit m: Manifest[T]): Option[String] = {
    Try {
      lazy val mapper = new ObjectMapper() with ScalaObjectMapper
      mapper.registerModule(DefaultScalaModule)
      mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
      mapper.writeValueAsString(obj)
    } match {
      case Success(x) => Some(x)
      case Failure (err) =>
        sys.error("@@@@Got " + err.getMessage + " while converting object  to JSON:--> " + obj)
        None
    }
  }
}
