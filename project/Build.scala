import sbt.Keys._
import sbt._
import sbtassembly.Plugin.AssemblyKeys._
import sbtassembly.Plugin._

object ApplicationBuild extends Build {

  lazy val serviceDependencies = Seq(
    "com.yammer.dropwizard" % "dropwizard-core" % "0.6.2",
    "uk.gov.defra" % "capd-common" % "1.0.4"
  )

  lazy val clientDependencies = Seq (
    "com.sun.jersey" % "jersey-client" % "1.17.1",
    "com.sun.jersey" % "jersey-core" % "1.17.1",
    "com.sun.jersey" % "jersey-json" % "1.17.1",
    "org.modelmapper"  %   "modelmapper" %   "0.6.2",
    "org.apache.commons" % "commons-lang3" % "3.3.2",
    "com.google.guava" % "guava" % "18.0"
  )

  lazy val testDependencies = Seq (
    "com.novocode" % "junit-interface" % "0.11" % "test",
    "org.mockito" % "mockito-core" % "1.9.5" % "test" exclude("org.hamcrest", "hamcrest-core"),
    "org.hamcrest" % "hamcrest-all" % "1.3" % "test"
  )

  val appReleaseSettings = Seq(
    // Publishing options:
    publishMavenStyle := true,
    publishArtifact in Test := false,
    pomIncludeRepository := { x => false },
    publishTo <<= version { (v: String) =>
      val nexus = "https://defranexus.kainos.com/"
      if (v.trim.endsWith("SNAPSHOT"))
        Some("sonatype-snapshots" at nexus + "content/repositories/snapshots")
      else
        Some("sonatype-releases"  at nexus + "content/repositories/releases")
    },
    credentials += Credentials(Path.userHome / ".ivy2" / ".credentials")
  )

  def defaultResolvers = Seq(
    "DEFRA Nexus Release repo" at "https://defranexus.kainos.com/content/repositories/releases/"
  )

  def commonSettings = Seq(
    organization := "uk.gov.defra",
    autoScalaLibrary := false,
    scalaVersion := "2.10.2",
    crossPaths := false,
    resolvers ++= defaultResolvers
  )

  def standardSettingsWithAssembly = commonSettings ++ assemblySettings ++ appReleaseSettings ++ Seq(
    mergeStrategy in assembly <<= (mergeStrategy in assembly) { (old) =>
      {
        case "about.html" => MergeStrategy.rename
        case "META-INF/spring.tooling" => MergeStrategy.discard
        case x => old(x)
      }
    },
    test in assembly := {}

  )

  lazy val root = Project("places", file("."), settings = appReleaseSettings ++ Seq(
    name := "places",
    resolvers ++= defaultResolvers
  )) aggregate(PlacesService, PlacesOsClient)

  lazy val PlacesService: Project = Project("places-service", file("places-service"),
    settings = standardSettingsWithAssembly ++ Seq(
      jarName in assembly := "places-service.jar",
      name := "places-serivce",
      libraryDependencies ++= serviceDependencies ++ testDependencies
    )) dependsOn(PlacesOsClient % "compile")

  lazy val PlacesOsClient = Project("places-os-client", file("places-os-client"),
    settings = standardSettingsWithAssembly ++ Seq(
      name := "places-os-client",
      libraryDependencies ++= clientDependencies ++ testDependencies
    ))
}
