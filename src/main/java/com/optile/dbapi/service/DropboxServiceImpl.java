package com.optile.dbapi.service;

import com.dropbox.core.*;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;
import com.dropbox.core.v2.users.FullAccount;

import java.util.List;
import java.util.Scanner;

public class DropboxServiceImpl implements DropboxService {

    public DbxAuthFinish auth(String appKey, String appSecret) throws DbxException {

        final DbxAppInfo appInfo = new DbxAppInfo(appKey, appSecret);
        DbxRequestConfig requestConfig = new DbxRequestConfig("dbx-auth");
        DbxWebAuth webAuth = new DbxWebAuth(requestConfig, appInfo);
        DbxWebAuth.Request dbxWAuthRequest = DbxWebAuth.newRequestBuilder().withNoRedirect().build();
        String authoriseUrl = webAuth.authorize(dbxWAuthRequest);
        //Receiving scanned authCode
        String authCode = instructAndScanAuthCode(authoriseUrl);

        DbxAuthFinish authFinish;
        try {
            authFinish = webAuth.finishFromCode(authCode);
            if (null == authFinish)
                throw new DbxException("DbxAuthFinish is null, authorisation failed.");
        } catch (DbxException exception) {
            throw new DbxException("Authorisation failed, message: " + exception.getMessage());
        }
        return authFinish;
    }

    public FullAccount getUserInfo(String accessToken, String locale) throws DbxException {
        //As the constructors that accept @var locale has been deprecated, I won't be implementing.
        DbxRequestConfig requestConfig = new DbxRequestConfig("dbx-get-user-info");

        DbxClientV2 dbxClient = new DbxClientV2(requestConfig, accessToken);
        return dbxClient.users().getCurrentAccount();
    }

    public List<Metadata> getDirectoryInfo(String accessToken, String path, String locale) throws DbxException {
        //As the constructors that accept @var locale has been deprecated, I won't be implementing.
        DbxRequestConfig requestConfig = new DbxRequestConfig("dbx-get-files");

        DbxClientV2 dbxClient = new DbxClientV2(requestConfig, accessToken);
        ListFolderResult listFolderResult = dbxClient.files().listFolder(path);
        while (listFolderResult.getHasMore()) {
            listFolderResult = dbxClient.files().listFolderContinue(listFolderResult.getCursor());
        }
        return listFolderResult.getEntries();
    }

    public Metadata getFileMetadata(String accessToken, String path, String locale) throws DbxException {
        //As the constructors that accept @var locale has been deprecated, I won't be implementing.
        DbxRequestConfig requestConfig = new DbxRequestConfig("dbx-get-file-details");

        DbxClientV2 dbxClient = new DbxClientV2(requestConfig, accessToken);
        return dbxClient.files().getMetadata(path);
    }

    private String instructAndScanAuthCode(String authoriseUrl) {

        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("1. Go to: " + authoriseUrl);
        System.out.println("2. Click \"Allow\" (you might have to log in first)");
        System.out.println("3. Copy the authorisation code and paste it here:");

        return new Scanner(System.in).nextLine();
    }

}
