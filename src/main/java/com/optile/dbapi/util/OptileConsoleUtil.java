package com.optile.dbapi.util;

import com.dropbox.core.DbxAuthFinish;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.FolderMetadata;
import com.dropbox.core.v2.files.Metadata;
import com.dropbox.core.v2.users.FullAccount;
import com.dropbox.core.v2.users.Name;

import java.util.List;

import static com.optile.dbapi.util.Prettifier.*;

public class OptileConsoleUtil {
    public static void printAccessToken(DbxAuthFinish authFinish) {
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("Your access token:");
        System.out.println(authFinish.getAccessToken());
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
    }

    public static void printAccountInfo(FullAccount account) {
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("User ID: " + account.getAccountId());
        System.out.println("Display Name: " + account.getName().getDisplayName());
        System.out.println(buildName(account.getName()));
        System.out.println("E-mail: " + account.getEmail());
        System.out.println("Country: " + account.getCountry());
        System.out.println("Referral link: " + account.getReferralLink());
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
    }

    private static String buildName(Name name) {
        StringBuilder builder = new StringBuilder("Name: ");
        builder.append(name.getGivenName());
        builder.append(" ");
        builder.append(name.getSurname());
        builder.append(" (");
        builder.append(name.getFamiliarName());
        builder.append(")");
        return builder.toString();
    }

    public static void printFolderEntries(List<Metadata> result, String path) {
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println(path);
        for (Metadata metadata : result) {
            printMetadataEntry(metadata);
        }
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
    }

    public static void printStandaloneFileMetadataEntry(FileMetadata fileMetadata) {
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println(fileMetadata.getPathDisplay() + ": file, " + prettifyFileSize(fileMetadata.getSize()) + ", "
                + buildMimeType(fileMetadata.getName()) + ", modified at: " + formatDate(fileMetadata.getServerModified()));
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
    }

    private static void printMetadataEntry(Metadata metadata) {
        if (metadata instanceof FileMetadata) {
            FileMetadata fileMetadata = (FileMetadata) metadata;
            System.out.println("  -  /" + fileMetadata.getName() + ": file, " + prettifyFileSize(fileMetadata.getSize()) + ", "
                    + buildMimeType(fileMetadata.getName()) + ", modified at: " + formatDate(fileMetadata.getServerModified()));
        } else if (metadata instanceof FolderMetadata) {
            FolderMetadata folderMetadata = (FolderMetadata) metadata;
            System.out.println("  -  /" + folderMetadata.getName() + ": dir");
        }
    }

    public static void printCommandSuggestion(){
        System.out.println("------------------Command suggestions-------------------");
        System.out.println("Please try following commands:");
        System.out.println("1. Authentication and authorisation. Syntax: auth <APP KEY> <APP SECRET>");
        System.out.println("2. Show current user information. Syntax: info <ACCESS TOKEN> <locale>");
        System.out.println("3: Show files and directories in given path. Syntax: list <ACCESS TOKEN> <PATH> <locale>");
        System.out.println("---------Capitalised arguments are mandatory------------");
    }
}
