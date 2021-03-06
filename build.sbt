name := "SataLincs"
organization in ThisBuild := "pt.unl.fct"
version := "0.1"
scalaVersion in ThisBuild := "2.13.2"

lazy val global = project
  .in(file("."))
  .settings(
    name := "satalincs",
    settings,
    libraryDependencies ++= commonDependencies
  )

// SETTINGS

lazy val settings = Seq(scalacOptions ++= compilerOptions)

lazy val compilerOptions = Seq(
  "-unchecked",
  "-feature",
  "-language:existentials",
  "-language:higherKinds",
  "-language:implicitConversions",
  "-language:postfixOps",
  "-deprecation",
  "-encoding",
  "utf8"
)

lazy val akkaHttpVersion = "10.1.12"
lazy val akkaVersion    = "2.6.6"

lazy val dependencies = new {
  val akka_http = "com.typesafe.akka" %% "akka-http" % akkaHttpVersion
  val akka_http_spray_json = "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion
  val actor_typed = "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion
  val akka_streams = "com.typesafe.akka" %% "akka-stream" % akkaVersion
  val logback = "ch.qos.logback"  % "logback-classic" % "1.2.3"

  val scala_jackson = "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.11.0"
  val jackson_databinding = "com.fasterxml.jackson.core" % "jackson-databind" % "2.11.0"
  val playJson = "com.typesafe.play" %% "play-json" % "2.9.0"
  val json = "org.json" % "json" % "20200518"
  val jsonPath = "com.jayway.jsonpath" % "json-path" % "2.4.0"

  val http_testkit = "com.typesafe.akka" %% "akka-http-testkit"        % akkaHttpVersion % Test
  val actor_testkit = "com.typesafe.akka" %% "akka-actor-testkit-typed" % akkaVersion     % Test
  val scala_test = "org.scalatest"   %% "scalatest"                % "3.0.8"         % Test
}

lazy val commonDependencies = Seq(
  dependencies.akka_http,
  dependencies.akka_http_spray_json,
  dependencies.actor_typed,
  dependencies.akka_streams,
  dependencies.logback,
  dependencies.scala_jackson,
  dependencies.jackson_databinding,
  dependencies.playJson,
  dependencies.json,
  dependencies.jsonPath,
  dependencies.http_testkit,
  dependencies.actor_testkit,
  dependencies.scala_test
)