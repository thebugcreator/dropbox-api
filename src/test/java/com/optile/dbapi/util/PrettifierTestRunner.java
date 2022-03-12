package com.optile.dbapi.util;

import com.optile.dbapi.service.DropboxAuthTest;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class PrettifierTestRunner {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(PrettifierTest.class);
        for (Failure failure : result.getFailures()) {
            System.err.println("Failed: " + failure.toString());
        }
        if(result.wasSuccessful()){
            System.out.println("All test cases passed.");
        } else {
            System.err.println(result.getFailureCount() + " test case(s) failed.");
        }
    }
}
