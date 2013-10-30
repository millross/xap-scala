package com.gigaspaces.demo.processor.scala

import java.util.logging.Logger
import org.openspaces.events.adapter.SpaceDataEvent
import com.gigaspaces.demo.common.scala.Data
import scala.beans.BeanProperty

/**
 * The processor simulates work done no un-processed Data object. The processData
 * accepts a Data object, simulate work by sleeping, and then sets the processed
 * flag to true and returns the processed Data.
 */
class Processor {

  val log= Logger.getLogger(this.getClass.getName)

  @BeanProperty
  var workDuration = 100

  @SpaceDataEvent
  def processData(data: Data): Data = {
    Thread.sleep(workDuration)
    data.setProcessed(true)
    data.setData("PROCESSED : " + data.getRawData)
    log.info(" ------ PROCESSED : " + data)
    data
  }

}
