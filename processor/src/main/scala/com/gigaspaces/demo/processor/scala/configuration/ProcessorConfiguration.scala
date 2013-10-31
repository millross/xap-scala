package com.gigaspaces.demo.processor.scala.configuration

import com.gigaspaces.demo.common.scala.Data
import org.springframework.context.annotation.{Bean, Configuration}
import com.gigaspaces.demo.processor.scala.Processor
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer

/**
 * @author Jez
 */
@Configuration
class ProcessorConfiguration {

  /**
   * Template object bean to identify non-processed Data objects
   * @return
   */
  @Bean
  def unprocessedDataTemplate = {
    val template = new Data()
    template.setProcessed(false)
    template.setSent(false)
    template
  }

  /**
   * The data processor Spring bean for the configuration
   * @return
   */
  @Bean
  def dataProcessor = new Processor

  /**
   * Spring property configurer which allows us to use system properties (such as user.name).
   * @return the property placeholder configurer bean
   */
  @Bean
  def propertiesConfigurer = new PropertyPlaceholderConfigurer

}
