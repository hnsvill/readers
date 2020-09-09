package com.hannah.readers.domain.models

import com.amazonaws.services.sqs.model.Message

sealed trait WODPostedMessage
case class MessageTooOld(message: Message) extends WODPostedMessage
