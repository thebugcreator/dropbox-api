package com.optile.dbapi.service;

import com.dropbox.core.DbxException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Scanner;

import static org.junit.Assert.*;

public class DropboxServiceTest {

    //This is a made up token for testing purpose
    static String fakeAccessToken;

    //Default locale argument
    static String locale;

    //Service provider
    static DropboxService service;

    //Live access token
    static String accessToken;

    @BeforeClass
    public static void init() {
        System.out.println("Access token:");
        accessToken = new Scanner(System.in).nextLine();
        fakeAccessToken = "IetCrRpYQkvUM5RG4d0YFotWj4yy3tP_jcCCR5hL11gJAAAAAAAAAAaQ6uT1UgCq";
        locale = "en_US";
    }

    @Before
    public void prepare(){
        service = new DropboxServiceImpl();
    }

    @Test
    public void testGetUserInfo() {
        try {
            assertNotNull(service.getUserInfo(accessToken, locale));
        } catch (DbxException ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    public void testGetUserInfoWithFakeAccessToken() {
        try {
            service.getUserInfo(fakeAccessToken, locale);
        } catch (DbxException ex) {
            assertNotNull(ex.getMessage());
        }
    }

    @Test
    public void testGetDirectoryInfo() {
        String path = "/hithere";
        try {
            assertNotNull(service.getDirectoryInfo(accessToken, path, locale));
        } catch (DbxException ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    public void testGetFileMetadata() {
        String path = "/hithere/IMG_20200201_155404.jpg";
        try {
            assertNotNull(service.getFileMetadata(accessToken, path, locale));
        } catch (DbxException ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    public void testGetDirectoryInfoWithFakeAccessToken() {
        String path = "/hithere";
        try {
            service.getDirectoryInfo(fakeAccessToken, path, locale);
        } catch (DbxException ex) {
            assertNotNull(ex.getMessage());
        }
    }

    @Test
    public void testGetFileMetadataWithFakeAccessToken() {
        String path = "/hithere/IMG_20200201_155404.jpg";
        try {
            service.getFileMetadata(fakeAccessToken, path, locale);
        } catch (DbxException ex) {
            assertNotNull(ex.getMessage());
        }
    }
}
