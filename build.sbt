organization in ThisBuild := "cloud.quaer"
version in ThisBuild := "1.0-SNAPSHOT"

// the Scala version that will be used for cross-compiled libraries
scalaVersion in ThisBuild := "2.13.0"

val macwire = "com.softwaremill.macwire" %% "macros" % "2.3.3" % "provided"
val scalaTest = "org.scalatest" %% "scalatest" % "3.1.1" % Test

lazy val `account-service` = (project in file("."))
  .aggregate(`account-service-api`, `account-service-impl`, `account-service-stream-api`, `account-service-stream-impl`)

lazy val `account-service-api` = (project in file("account-service-api"))
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslApi
    )
  )

lazy val `account-service-impl` = (project in file("account-service-impl"))
  .enablePlugins(LagomScala)
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslPersistenceCassandra,
      lagomScaladslKafkaBroker,
      lagomScaladslTestKit,
      macwire,
      scalaTest
    )
  )
  .settings(lagomForkedTestSettings)
  .dependsOn(`account-service-api`)

lazy val `account-service-stream-api` = (project in file("account-service-stream-api"))
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslApi
    )
  )

lazy val `account-service-stream-impl` = (project in file("account-service-stream-impl"))
  .enablePlugins(LagomScala)
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslTestKit,
      macwire,
      scalaTest
    )
  )
  .dependsOn(`account-service-stream-api`, `account-service-api`)
