name := "readers"

version := "0.1"

scalaVersion := "2.13.3"

val fleamVersion = "5.0.0"
val ficusVersion = "1.5.0"

libraryDependencies ++= Seq(
  "com.nike.fleam" %% "fleam" % fleamVersion,
  "com.nike.fleam" %% "fleam-aws-sqs" % fleamVersion,
  "com.nike.fleam" %% "fleam-aws-cloudwatch" % fleamVersion,
  "com.iheart" %% "ficus" % ficusVersion)