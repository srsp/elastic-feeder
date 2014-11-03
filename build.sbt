name := "elastic-feeder"

organization := "com.spruenker"

scalaVersion := "2.11.2"

version := "1.0"


// Dependendies

libraryDependencies += "com.sksamuel.elastic4s" %% "elastic4s" % "1.3.2"

libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % "3.1.0"


// Test dependencies

libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.1" % "test"

libraryDependencies += "org.mockito" % "mockito-core" % "1.10.8" % "test"


// Publishing

publishMavenStyle := true

publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases"  at nexus + "service/local/staging/deploy/maven2")
}

publishArtifact in Test := false

pomIncludeRepository := { _ => false }


// POM stuff

pomExtra := (
  <url>https://github.com/srsp/elastic-feeder</url>
    <licenses>
      <license>
        <name>Apache License, Version 2.0</name>
        <url>http://www.apache.org/licenses/LICENSE-2.0</url>
        <distribution>repo</distribution>
      </license>
    </licenses>
    <scm>
      <url>git@github.com:srsp/elastic-feeder.git</url>
      <connection>scm:git:git@github.com:srsp/elastic-feeder.git</connection>
    </scm>
    <developers>
      <developer>
        <id>srsp</id>
        <name>Simon Sprünker</name>
        <url>http://www.spruenker.com</url>
      </developer>
    </developers>)