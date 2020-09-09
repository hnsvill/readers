import sbt._

ThisBuild / name := "readers"

ThisBuild / version := "0.1"

ThisBuild / scalaVersion := "2.13.3"

lazy val fleamVersion = "5.0.0"
lazy val ficusVersion = "1.5.0"

lazy val readers = (project in file(".")).aggregate(domain, processor)

lazy val domain = project
  .settings(
    libraryDependencies ++= Seq(
      "com.nike.fleam" %% "fleam" % fleamVersion,
      "com.nike.fleam" %% "fleam-aws-sqs" % fleamVersion,
      "com.nike.fleam" %% "fleam-aws-cloudwatch" % fleamVersion,
      "com.iheart" %% "ficus" % ficusVersion
    )
  )

lazy val processor = project
  .dependsOn(domain)
  .settings(
    libraryDependencies ++= Seq(
      "com.nike.fleam" %% "fleam" % fleamVersion,
      "com.nike.fleam" %% "fleam-aws-sqs" % fleamVersion,
      "com.nike.fleam" %% "fleam-aws-cloudwatch" % fleamVersion,
      "com.iheart" %% "ficus" % ficusVersion
    )
  )