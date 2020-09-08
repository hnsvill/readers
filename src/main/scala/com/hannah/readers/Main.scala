package com.hannah.readers

import net.ceedubs.ficus.Ficus._
import net.ceedubs.ficus.readers.ArbitraryTypeReader._
import com.nike.fleam.sqs.configuration.SqsQueueProcessingConfiguration
import com.typesafe.config.ConfigFactory

object Main extends App {

  val config = ConfigFactory.load()
  val sqsConfig = config.as[SqsQueueProcessingConfiguration]("ourSqsSource")

  val name = config.getString("main.hello.name")
  println(s"hello, sbt. Say hi to $name for me")

}
