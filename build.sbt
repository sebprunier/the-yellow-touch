name := """the-yellow-touch"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.8"

libraryDependencies += guice
libraryDependencies += "fr.maif" %% "izanami-client" % "1.4.0"
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "4.0.1" % Test

resolvers ++= Seq(
  "jsonlib-repo" at "https://raw.githubusercontent.com/mathieuancelin/json-lib-javaslang/master/repository/releases",
  Resolver.jcenterRepo
)
