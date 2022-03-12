package com.optile.dbapi.service;

import com.dropbox.core.DbxAuthFinish;
import com.dropbox.core.DbxException;
import com.dropbox.core.v2.files.Metadata;
import com.dropbox.core.v2.users.FullAccount;

import java.util.List;

public interface DropboxService {

    //Command values
    String AUTH = "auth";
    String INFO = "info";
    String LIST = "list";

    //Default value for "locale" variable
    String DEFAULT_LOCALE = "en_US";


    //Declaration of system exit codes
    int SYSEXIT_NO_COMMAND_GIVEN = 20;
    int SYSEXIT_INAPPR_ARG = 21;

    /**
     * This method is used for authentication and authorisation processes
     *
     * @param appKey    @{@link String} required
     * @param appSecret @{@link String} required
     * @return @{@link DbxAuthFinish}
     * @throws DbxException
     */
    DbxAuthFinish auth(String appKey, String appSecret) throws DbxException;

    /**
     * This method is created to retrieve current user's information
     *
     * @param accessToken @{@link String} required
     * @param locale      @{@link String} optional
     * @return @{@link FullAccount}
     * @throws DbxException
     */
    FullAccount getUserInfo(String accessToken, String locale) throws DbxException;

    /**
     * Call this method to get a list of files and folders in the specified path
     *
     * @param accessToken @{@link String} required
     * @param path        @{@link String} required
     * @param locale      @{@link String} optional
     * @return @{@link List<Metadata>}
     * @throws DbxException
     */
    List<Metadata> getDirectoryInfo(String accessToken, String path, String locale) throws DbxException;

    /**
     * Call this method to retrieve targeted file's information
     *
     * @param accessToken @{@link String} required
     * @param path        @{@link String} required
     * @param locale      @{@link String} optional
     * @return @{@link Metadata} FileMetadata is expected
     * @throws DbxException
     */
    Metadata getFileMetadata(String accessToken, String path, String locale) throws DbxException;
}
