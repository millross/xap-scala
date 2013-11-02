package com.gigaspaces.demo.postprocessor.scala.configuration

import org.springframework.context.annotation.{Bean, Configuration}
import com.gigaspaces.demo.postprocessor.scala.PostProcessingRouter
import com.gigaspaces.demo.common.scala.Data
import com.j_spaces.core.client.SQLQuery
import java.lang.Boolean._
import org.springframework.integration.dsl.{Channel, handle, when, route}
import org.springframework.integration.Message

/**
 * @author Jez
 */
@Configuration
class RouterConfiguration {

  @Bean
  def router = new PostProcessingRouter

  // Relatively trivial Spring integration flow in Scala, just to show how it can be achieved, note that there is
  // a Scala version conflict
  @Bean
  def siRoutedFlow = {
    route {m:Message[Data] => m.getPayload.getData.trim.last.toString} (

      when("0") andThen
        handle {m: Message[_] => println("ROUTER: Routing to 0")},

      when ("1") andThen
        handle {m: Message[_] => println("ROUTER: Routing to 1")}
    ) -->
    Channel("Default") -->
    handle {m: Message[_] => println("ROUTER: Routing to DEFAULT")}
  }

}
