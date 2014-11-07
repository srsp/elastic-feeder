name := "elastic-feeder"

organization := "com.spruenker"

version := "1.1.0"

val currentScala = "2.11.4"

scalaVersion := currentScala


// Dependencies

libraryDependencies += "com.sksamuel.elastic4s" %% "elastic4s" % "1.3.2"

// Test dependencies

libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.1" % "test"

libraryDependencies += "org.mockito" % "mockito-core" % "1.10.8" % "test"


// Publishing

crossScalaVersions := Seq("2.10.4", currentScala)

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
