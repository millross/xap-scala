package com.gigaspaces.demo.processor;

import com.gigaspaces.demo.common.scala.Data;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


/**
 * A simple unit test that verifies the Processor processData method actually processes
 * the Data object.
 */
public class ProcessorTest {

    @Test
    public void verifyProcessedFlag() {
        Processor processor = new Processor();
        Data data = new Data(1L, "test");

        Data result = processor.processData(data);
        assertEquals("verify that the data object was processed", true, result.isProcessed());
        assertEquals("verify the data was processed", "PROCESSED : test", result.getData());
        assertEquals("verify the type was not changed", data.getDataType(), result.getDataType());
    }
}
