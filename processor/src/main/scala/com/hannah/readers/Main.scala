package com.hannah.readers

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Flow, Sink}
import com.amazonaws.regions.Regions
import com.amazonaws.services.sqs.model.Message
import com.amazonaws.services.sqs.{AmazonSQSAsync, AmazonSQSAsyncClientBuilder}
import com.nike.fleam.StreamDaemon
import com.nike.fleam.sqs.configuration.SqsQueueProcessingConfiguration
import com.nike.fleam.sqs.{SqsDelete, SqsSource}
import com.typesafe.config.ConfigFactory

import scala.util.Failure

object Main extends App {

  implicit val system = ActorSystem("QuickStart")
  implicit val materializer = ActorMaterializer()

  val config = ConfigFactory.load()
  val sqsConfig = config.as[SqsQueueProcessingConfiguration]("ourSqsSource")

  val sqsClient : AmazonSQSAsync = AmazonSQSAsyncClientBuilder.standard()
    .withRegion(Regions.fromName(sqsConfig.region)).build()

  val source = SqsSource(sqsClient).forQueue(sqsConfig)

  val pipeline1 = Flow[Message].map { message =>
    println(s"message: ${message.getMessageId}, BODY: ${message.getBody}")
    message
  }

  val sqsDelete = SqsDelete(sqsClient).forQueue(sqsConfig.queue.url).toFlow[Message](sqsConfig.delete)

  val pipeline2 = Flow[Message].map { message =>
    println(s"deleting message: ${message.getMessageId}, BODY: ${message.getBody}")
    message
  }.via(sqsDelete)

  val daemon = new StreamDaemon("example daemon")
  daemon.start(
    source = source,
    pipeline = pipeline2,
    sink = Sink.ignore
  ) andThen { case Failure(ex) => println("Unable to start daemon", ex) }
}
