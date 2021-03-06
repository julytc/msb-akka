
val akka_version = "2.3.9"
import sbtrelease._
import ReleaseTransformations._

lazy val root = (project in file(".")).
  settings (
    organization := "io.github.tcdl.msb",
    name := "msb-akka",
    version := "0.1.4-SNAPSHOT",
    scalaVersion := "2.11.5",
    bintrayOrganization := Some("tcdl"),
    bintrayRepository := "releases",
    releaseVersionBump := sbtrelease.Version.Bump.Minor,
    releaseNextVersion :=  { ver => Version(ver).map(_.bumpMinor.string).getOrElse(versionFormatError) },
    libraryDependencies ++= (dependencies ++ testDependencies),
    resolvers ++= dependencyResolvers,
    licenses += ("MIT", url("http://opensource.org/licenses/MIT"))
  )

val dependencyResolvers = Seq(
  "TCDL" at "https://dl.bintray.com/tcdl/releases",
  "Maven Central" at "http://repo1.maven.org/maven2"
)

val dependencies = Seq (
  "io.github.tcdl.msb" % "msb-java-core" % "1.1.0",
  "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.5.3",
  "com.typesafe.akka" %% "akka-actor" % akka_version
)

val testDependencies = Seq (
  "org.scalatest" % "scalatest_2.11" % "2.2.4" % "test",
  "com.typesafe.akka" %% "akka-testkit" % akka_version % "test"
)

releaseProcess := Seq(
  inquireVersions,
  setReleaseVersion,
  tagRelease,
  commitReleaseVersion, 
  setNextVersion,                         
  commitNextVersion,
  pushChanges 
 
)

