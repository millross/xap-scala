package com.gigaspaces.demo.monitor.scala

import org.openspaces.core.space.UrlSpaceConfigurer
import org.openspaces.core.GigaSpaceConfigurer
import java.util.logging.Logger
import org.openspaces.scala.core.ScalaGigaSpacesImplicits._
import com.gigaspaces.demo.common.scala.Data

/**
 * Requires gs-openspaces-jar on the classpath
 * @author Jez
 */
object MonitorApp extends App {


  val log= Logger.getLogger(this.getClass.getName)
  val url = "jini://*/*/space"
  val space = new UrlSpaceConfigurer(url).space
  val predicateGigaSpace = new GigaSpaceConfigurer(space).gigaSpace.predicate

  // Loop writing out the info (> and < seemed to cause problems for macro expansion)
  val count = predicateGigaSpace count { data: Data => data.dataType == 10L }

  log.info("Count is " + count)

}
