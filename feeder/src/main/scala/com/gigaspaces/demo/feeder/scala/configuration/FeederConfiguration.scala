package com.gigaspaces.demo.feeder.scala.configuration

import org.springframework.context.annotation.{Configuration, DependsOn, Bean}
import com.gigaspaces.demo.feeder.scala.Feeder

/**
 * @author Jez
 */
@Configuration
class FeederConfiguration {

  @Bean
  //@DependsOn(Array("gigaSpace"))
  def dataFeeder = {
    val feeder = new Feeder
    feeder.numberOfTypes = 100
    feeder
  }

}
