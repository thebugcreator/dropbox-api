package com.optile.dbapi.util;

import org.junit.Assert;
import org.junit.Test;

public class PrettifierTest {
    @Test
    public void testBuildMimeType() {
        String fileName = "something.zip";
        String expected = "application/zip";
        String actual = Prettifier.buildMimeType(fileName);
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void testConvertUnit() {
        long fileSize = 2048;
        String expected = "2.00 KB";
        String actual = Prettifier.prettifyFileSize(fileSize);
        Assert.assertEquals(actual, expected);
    }
}
