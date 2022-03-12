package com.optile.dbapi.service;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class DropboxServiceTestRunner {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(DropboxServiceTest.class);
        for (Failure failure : result.getFailures()) {
            System.out.println("Failed: " + failure.toString());
        }
        if(result.wasSuccessful()){
            System.out.println("All test cases passed.");
        } else {
            System.out.println(result.getFailureCount() + " test case(s) failed.");
        }

    }
}
