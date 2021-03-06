package com.example.trafficinfo.ui;

// Author: Liam Rutherford
// Matriculation Number: S2024208
public class DataFormat {
    public String getDescription(String desc) {
        int result = desc.lastIndexOf("<br />");
        if (result == -1) {
            return desc;
        } else return desc.substring(result + 6);
    }

    public String[] getDates(String date) throws StringIndexOutOfBoundsException {
        if (!date.contains("Start Date: ") || !date.contains("End Date: ")) {
            return null;
        } else {
            String startDateIndex = date.substring(date.indexOf("Start Date: "), date.indexOf(':'));
            String data1 = date.substring(startDateIndex.length() + 2, date.indexOf('<'));
            String leftOverString = date.substring(date.indexOf('>'));

            String endDateIndex = leftOverString.substring(leftOverString.indexOf("End Date: "), date.indexOf(':'));
            String data2;
            if (date.contains("<br />Delay")) {
                data2 = leftOverString.substring(endDateIndex.length() + 2, leftOverString.indexOf('<'));
            } else {
                data2 = leftOverString.substring(endDateIndex.length() + 2);
            }
            String[] results = new String[2];
            results[0] = data1;
            results[1] = data2;
            return results;
        }

    }

    public String convertLongDateToShort(String dateString) {
        String regex = "\\d+";
        StringBuilder result = new StringBuilder();
        String[] words = dateString.split(" ");
        for (String word : words) {
            if (word.matches(regex)) {
                result.append(word).append("/");
            } else if (!getNumOfMonth(word).isEmpty()) {
                result.append(getNumOfMonth(word)).append("/");
            }
        }
        return result.substring(0, result.length() - 1); // take the last / off the end
    }

    public String getNumOfMonth(String month) {
        switch (month) {
            case "January":
                return "01";
            case "February":
                return "02";
            case "March":
                return "03";
            case "April":
                return "04";
            case "May":
                return "05";
            case "June":
                return "06";
            case "July":
                return "07";
            case "August":
                return "08";
            case "September":
                return "09";
            case "October":
                return "10";
            case "November":
                return "11";
            case "December":
                return "12";
            default:
                return "";
        }
    }
}