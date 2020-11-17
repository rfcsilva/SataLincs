package commons

import akka.actor.{ActorSystem, ClassicActorSystemProvider}
import akka.stream.{Materializer, SystemMaterializer}

import scala.concurrent.ExecutionContextExecutor

abstract class Api{

  implicit val system: ActorSystem = ActorSystem()
  /**
   * Implicitly provides the system wide materializer from a classic or typed `ActorSystem`
   */
  implicit def matFromSystem(implicit provider: ClassicActorSystemProvider): Materializer =
    SystemMaterializer(provider.classicSystem).materializer
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher


  def login(username: String, password: String)

  def logout()

  def search()

  def download()

  def close(): Unit = { system.terminate() }
}
