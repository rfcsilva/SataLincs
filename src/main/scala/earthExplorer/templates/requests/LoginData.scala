package earthExplorer.templates.requests

import java.io.StringWriter

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import commons.JsonObj

class LoginData(val username: String, val password: String,  val authType: String = "EROS", val catalogId : String = "EE") extends JsonObj{

  override def toJson: String ={
    val mapper = new ObjectMapper()
    mapper.registerModule(DefaultScalaModule)

    val out = new StringWriter
    mapper.writeValue(out, this)
    val json = out.toString
    println(json)
    json
  }
}


