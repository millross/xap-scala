package com.gigaspaces.demo.feeder.scala

import org.springframework.beans.factory.{DisposableBean, InitializingBean}
import java.util.logging.Logger
import org.openspaces.core.GigaSpace
import org.openspaces.core.context.GigaSpaceContext
import scala.concurrent.{future}
import scala.concurrent._
import ExecutionContext.Implicits.global
import scala.beans.BeanProperty
import com.gigaspaces.demo.common.Data

/**
 * @author Jez
 */
class Feeder extends InitializingBean with DisposableBean {

  val log= Logger.getLogger(this.getClass().getName());

  @BeanProperty
  var defaultDelay = 1000;

  @BeanProperty
  var numberOfTypes = 10;

  @GigaSpaceContext
  var gigaSpace: GigaSpace = null;

  private var cancelled = false;

  def destroy = {
    cancelled = true;
  }

  def afterPropertiesSet() = {
    future[Unit] {
      var counter = 1;
      while (!cancelled) {
        val time = System.currentTimeMillis
        val data: Data = new Data((({
          counter += 1; counter - 1
        }) % numberOfTypes), "FEEDER " + time)

        gigaSpace.write(data)
        log.info("--- FEEDER WROTE " + data)
        Thread.sleep(defaultDelay)
      }
    }
  }

}
