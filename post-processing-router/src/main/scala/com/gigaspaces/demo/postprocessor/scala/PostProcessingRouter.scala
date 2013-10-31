package com.gigaspaces.demo.postprocessor.scala

import java.util.logging.Logger
import org.openspaces.events.adapter.SpaceDataEvent
import com.gigaspaces.demo.common.scala.Data
import org.openspaces.events.polling.{ReceiveHandler, Polling}
import org.openspaces.events.{EventTemplate, TransactionalEvent, EventDriven}
import com.j_spaces.core.client.SQLQuery
import java.lang.Boolean._
import org.openspaces.events.asyncpolling.AsyncPolling
import org.openspaces.events.polling.receive.SingleTakeReceiveOperationHandler

/**
 * @author Jez
 */
@EventDriven @Polling @TransactionalEvent
class PostProcessingRouter {

  val log= Logger.getLogger(this.getClass.getName)

  /*
  @Autowired
  @Qualifier("siRoutedFlow")
  @BeanProperty
  var siRoutedFlow: AnyRef = null
  */

  @EventTemplate
  def routingTemplate = new SQLQuery[Data] (classOf[Data], "processed = ? AND sent = ?", TRUE, FALSE)

  @SpaceDataEvent
  def sendData(data: Data): Data = {
    Thread.sleep(100)
    data.sent = true
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
