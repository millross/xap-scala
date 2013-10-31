package com.gigaspaces.demo.postprocessor.scala

import java.util.logging.Logger
import scala.beans.BeanProperty
import org.openspaces.events.adapter.SpaceDataEvent
import com.gigaspaces.demo.common.scala.Data
import org.springframework.beans.factory.annotation.{Autowired, Qualifier}

/**
 * @author Jez
 */
class PostProcessingRouter {

  val log= Logger.getLogger(this.getClass.getName)

  /*
  @Autowired
  @Qualifier("siRoutedFlow")
  @BeanProperty
  var siRoutedFlow: AnyRef = null
  */

  @SpaceDataEvent
  def sendData(data: Data): Data = {
    Thread.sleep(100)
    data.sent = true
    log.info(" ------ SENT : " + data)
    data
  }

}
