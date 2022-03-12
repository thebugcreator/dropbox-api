package com.optile.dbapi.util;

import com.dropbox.core.v2.files.ListFolderError;
import com.dropbox.core.v2.files.ListFolderErrorException;
import com.dropbox.core.v2.files.LookupError;

public class ExceptionUtil {
    public static boolean targetIsNotFolder(ListFolderErrorException e) {
        return ListFolderError.path(LookupError.NOT_FOLDER).getPathValue().equals(e.errorValue.getPathValue());
    }
}
