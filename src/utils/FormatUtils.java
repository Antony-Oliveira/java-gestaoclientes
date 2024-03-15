package utils;

public class FormatUtils {

    public static final String EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$";
    public static final String CPF_REGEX = "(^\\d{3}\\x2E\\d{3}\\x2E\\d{3}\\x2D\\d{2}$)";
    public static final String PHONE_REGEX ="^\\s*(\\d{2}|\\d{0})[-. ]?(\\d{5}|\\d{4})[-. ]?(\\d{4})[-. ]?\\s*$";
    
    public static String cpfFormat(String cpf) {
        if (cpf == null) {
            return "";
        }
    
        cpf = cpf.replaceAll("[^0-9]", "");
    
        if (cpf.length() == 11) {
            return cpf.substring(0, 3) + "." + cpf.substring(3, 6) + "." + cpf.substring(6, 9) + "-" + cpf.substring(9);
        }
    
        return cpf;
    }

    public static String formatPhoneNumber(String phoneNumber) {
        if (phoneNumber == null) {
            return "";
        }

        phoneNumber = phoneNumber.replaceAll("[^0-9]", "");

        if (phoneNumber.length() == 11) {
            return "(" + phoneNumber.substring(0, 2) + ") " + phoneNumber.substring(2, 7) + "-" + phoneNumber.substring(7);
        }

        return phoneNumber;
    }
}
