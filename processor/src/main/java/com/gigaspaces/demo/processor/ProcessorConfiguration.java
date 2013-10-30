package com.gigaspaces.demo.processor;

import com.gigaspaces.demo.common.scala.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Jez
 */
@Configuration
public class ProcessorConfiguration {

    @Bean
    public Data unprocessedDataTemplate() {
        Data template = new Data();
        template.setProcessed(false);
        return template;
    }
}
