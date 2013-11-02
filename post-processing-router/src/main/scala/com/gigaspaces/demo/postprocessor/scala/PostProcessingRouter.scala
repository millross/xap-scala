package com.gigaspaces.demo.postprocessor.scala

import java.util.logging.Logger
import org.openspaces.events.adapter.SpaceDataEvent
import com.gigaspaces.demo.common.scala.Data
import org.openspaces.events.polling.{ReceiveHandler, Polling}
import org.openspaces.events.{EventTemplate, TransactionalEvent, EventDriven}
import com.j_spaces.core.client.SQLQuery
import java.lang.Boolean._
import org.openspaces.events.polling.receive.SingleTakeReceiveOperationHandler
import org.springframework.beans.factory.annotation.{Autowired, Qualifier}
import scala.beans.BeanProperty
import org.springframework.integration.dsl._
import org.springframework.integration.Message

/**
 * @author Jez
 */
@EventDriven @Polling @TransactionalEvent
class PostProcessingRouter {

  val log= Logger.getLogger(this.getClass.getName)

  //@Autowired
  //@Qualifier("siRoutedFlow")
  @BeanProperty
  var siRoutedFlow = route {m:Message[Data] => (m.getPayload.getData.trim.last > '4').toString } (

    when("false") andThen
      handle {m: Message[Data] => println(s"ROUTER: Routing ${m.getPayload.getData.trim.last} to low value workflow")},
    when("true") andThen
      handle {m: Message[Data] => println(s"ROUTER: Routing ${m.getPayload.getData.trim.last} to high value workflow")}
  ) --> handle {m: Message[_] => println("ROUTER: Routing to DEFAULT")}

  @EventTemplate
  def routingTemplate = new SQLQuery[Data] (classOf[Data], "processed = ? AND sent = ?", TRUE, FALSE)

  @SpaceDataEvent
  def sendData(data: Data): Data = {
    Thread.sleep(100)
    data.sent = true
    siRoutedFlow.send(data)
    log.info(" ------ SENT : " + data)
    data
  }

  @ReceiveHandler
  def receiveHandler = {
    val receiveHandler = new SingleTakeReceiveOperationHandler
    receiveHandler.setNonBlocking (true)
    receiveHandler.setNonBlockingFactor(10); // Intentional batching up
    receiveHandler
  }

}
