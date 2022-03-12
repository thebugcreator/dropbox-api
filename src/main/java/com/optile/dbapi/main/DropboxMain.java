package com.optile.dbapi.main;

import com.dropbox.core.DbxAuthFinish;
import com.dropbox.core.DbxException;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.ListFolderErrorException;
import com.dropbox.core.v2.files.Metadata;
import com.dropbox.core.v2.users.FullAccount;
import com.optile.dbapi.service.DropboxService;
import com.optile.dbapi.service.DropboxServiceImpl;
import com.optile.dbapi.service.MissingArgumentException;
import com.optile.dbapi.service.MissingRequiredArgumentException;
import com.optile.dbapi.util.ExceptionUtil;
import com.optile.dbapi.util.OptileConsoleUtil;

import java.util.List;

import static com.optile.dbapi.service.DropboxService.SYSEXIT_INAPPR_ARG;
import static com.optile.dbapi.service.DropboxService.SYSEXIT_NO_COMMAND_GIVEN;

public class DropboxMain {
    public static void main(String[] args) {
        DropboxService dropboxService = new DropboxServiceImpl();
        String command = "";
        try {
            command = args[0];
        } catch (IndexOutOfBoundsException ex) {
            System.out.println("No command has been entered.");
            OptileConsoleUtil.printCommandSuggestion();
            System.exit(SYSEXIT_NO_COMMAND_GIVEN);
        }
        if (null == command) {
            System.out.println("Entered command is null.");
            OptileConsoleUtil.printCommandSuggestion();
            System.exit(SYSEXIT_NO_COMMAND_GIVEN);
        } else {
            command = command.toLowerCase();
            //Check the chosen functionality
            switch (command) {
                //Authentication and authorisation
                case DropboxService.AUTH:
                    String appKey = "";
                    String appSecret = "";
                    try {
                        try {
                            appKey = args[1];
                            appSecret = args[2];
                        } catch (IndexOutOfBoundsException exception) {
                            //If appKey or appSecret is missing, throw a "serious" exception
                            throw new MissingRequiredArgumentException("appKey and/or appSecret");
                        }
                    } catch (MissingRequiredArgumentException ex) {
                        System.out.println(ex.getMessage());
                        System.exit(SYSEXIT_INAPPR_ARG);
                    }
                    try {
                        DbxAuthFinish dbxAuthFinish = dropboxService.auth(appKey, appSecret);
                        OptileConsoleUtil.printAccessToken(dbxAuthFinish);
                    } catch (DbxException ex) {
                        System.out.println(ex.getMessage());
                    }
                    break;
                //Account details of the user whose API key has been entered
                case DropboxService.INFO: {
                    String accessToken = "";
                    String locale = "";
                    try {
                        try {
                            accessToken = args[1];
                        } catch (IndexOutOfBoundsException ex) {
                            throw new MissingRequiredArgumentException("accessToken");
                        }
                        try {
                            locale = args[2];
                        } catch (IndexOutOfBoundsException ex) {
                            locale = DropboxService.DEFAULT_LOCALE;
                            throw new MissingArgumentException("locale");
                        }
                    } catch (MissingRequiredArgumentException exception) {
                        System.out.println(exception.getMessage());
                        System.exit(SYSEXIT_INAPPR_ARG);
                    } catch (MissingArgumentException ex) {
                        System.out.println(ex.getMessage());
                    }
                    try {
                        FullAccount account = dropboxService.getUserInfo(accessToken, locale);
                        OptileConsoleUtil.printAccountInfo(account);
                    } catch (DbxException ex) {
                        System.out.println(ex.getMessage());
                    }
                    break;
                }
                //Shows files and folders in a chosen directory
                case DropboxService.LIST: {
                    String accessToken = "";
                    String path = "";
                    String locale = "";
                    try {
                        try {
                            accessToken = args[1];
                        } catch (IndexOutOfBoundsException exception) {
                            throw new MissingRequiredArgumentException("accessToken");
                        }
                        try {
                            path = args[2];
                        } catch (IndexOutOfBoundsException exception) {
                            throw new MissingRequiredArgumentException("path");
                        }
                        try {
                            locale = args[3];
                        } catch (IndexOutOfBoundsException exception) {
                            locale = DropboxService.DEFAULT_LOCALE;
                            throw new MissingArgumentException("locale");
                        }

                    } catch (MissingRequiredArgumentException exception) {
                        System.out.println(exception.getMessage());
                        System.exit(SYSEXIT_INAPPR_ARG);
                    } catch (MissingArgumentException exception) {
                        System.out.println(exception.getMessage());
                    }
                    try {
                        try {
                            List<Metadata> result = dropboxService.getDirectoryInfo(accessToken, path, locale);
                            OptileConsoleUtil.printFolderEntries(result, path);
                        } catch (ListFolderErrorException ex) {
                            if (ExceptionUtil.targetIsNotFolder(ex)) {
                                FileMetadata fileMetadata = (FileMetadata) dropboxService.getFileMetadata(accessToken, path, locale);
                                OptileConsoleUtil.printStandaloneFileMetadataEntry(fileMetadata);
                            } else
                                throw ex;
                        }
                    } catch (DbxException ex) {
                        System.out.println("File or directory not found.");
                    }
                    break;
                }
                default:
                    System.out.println("Inappropriate command has been entered.");
                    OptileConsoleUtil.printCommandSuggestion();
                    break;
            }
        }
    }

}
