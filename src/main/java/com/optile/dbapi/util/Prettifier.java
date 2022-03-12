package com.optile.dbapi.util;

import javax.activation.MimetypesFileTypeMap;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Johnny
 */
public class Prettifier {
    /**
     * Prettify the file size for a better appearance
     *
     * @param size size of the file in byte (B)
     * @return size of the file in a prettified format, followed by a converted unit
     */
    public static String prettifyFileSize(long size) {

        int unitLvl = 1;
        double currentSize = size;
        while (currentSize >= 1024) {
            currentSize = currentSize / 1024;
            unitLvl++;
            //Limit supported file size unit to GB
            if (unitLvl >= 4)
                break;
        }
        StringBuilder builder = new StringBuilder();

        builder.append(roundTo2DecimalPlaces(currentSize));
        builder.append(" ");
        //Set the unit
        switch (unitLvl) {
            case 2:
                builder.append("KB");
                break;
            case 3:
                builder.append("MB");
                break;
            case 4:
                builder.append("GB");
                break;
            default:
                builder.append("B");
                break;
        }
        return builder.toString();
    }

    /**
     * This method is used to indicate the mime type of a file based on the file name
     *
     * @param fileName name of the file in format [file name][.][file extension]
     * @return a string that indicate file's mime type
     */
    public static String buildMimeType(String fileName) {
        try {
            String contentType = MimetypesFileTypeMap.getDefaultFileTypeMap().getContentType(fileName);
            //Specify the mime types of application that are all recognised as octet-stream
            if ("application/octet-stream".equals(contentType)) {
                return "application/" + fileName.substring(fileName.lastIndexOf('.') + 1);
            }
            return contentType;
        } catch (Exception exception) {
            return "unknown";
        }
    }

    /**
     * This method is used to round a double number to 2 decimal places
     *
     * @param input a double number that is not rounded
     * @return a number in String presentation, which is rounded to 2 decimal places
     */
    private static String roundTo2DecimalPlaces(double input) {
        BigDecimal bd = BigDecimal.valueOf(input);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.toString();
    }

    /**
     * This method is for formatting the date resulted whilst calling APIs
     *
     * @param date @{@link Date} given date that is not formatted
     * @return a @{@link String} that is the representation of the given date
     */
    public static String formatDate(Date date) {
        String pattern = "dd-MM-yyyy HH:mm:ss";
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(date);
    }
}
