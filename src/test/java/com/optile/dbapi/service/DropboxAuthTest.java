package com.optile.dbapi.service;

import com.dropbox.core.DbxAuthFinish;
import com.dropbox.core.DbxException;
import org.junit.Before;
import org.junit.Test;

import java.util.Scanner;

import static org.junit.Assert.*;

public class DropboxAuthTest {

    DropboxService service;
    Scanner scanner;
    @Before
    public void prepare(){
        service = new DropboxServiceImpl();
        scanner = new Scanner(System.in);
    }
    @Test
    public void testAuth() {
        System.out.println("Enter app key:");
        String appKey = scanner.nextLine();
        System.out.println("Enter app secret:");
        String appSecret = scanner.nextLine();
        try {
            DbxAuthFinish authFinish = service.auth(appKey, appSecret);
            assertNotNull(authFinish);
        } catch (DbxException e) {
            fail(e.getMessage());
        }
    }

}
