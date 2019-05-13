package com.jurgaitis.validationedittext;

import android.util.Patterns;

import java.util.regex.Pattern;

public class CustomValidator {
    private int maxLength;
    private int minLength;
    private String notEmptyErrorMessage;
    private String emailErrorMessage;
    private String onlyLettersErrorMessage;
    private String onlyLettersWithSpacesErrorMessage;
    private String onlyLatinLettersErrorMessage;
    private String onlyLatinLettersWithSpacesErrorMessage;
    private String alphanumericErrorMessage;
    private String onlyDigitsErrorMessage;
    private String maxLengthErrorMessage;
    private String minLengthErrorMessage;
    private String lowercaseErrorMessage;
    private String uppercaseErrorMessage;
    private String atLeastOneLowercaseErrorMessage;
    private String atLeastOneUppercaseErrorMessage;
    private String atLeastOneDigitErrorMessage;
    private String atLeastOneSpecialCharacterErrorMessage;
    private String atLeastOneLetterErrorMessage;
    private String postalCodeErrorMessage;
    private String onlyCustomRegexErrorMessage;
    private String atLeastOneCustomRegexErrorMessage;

    /**
     * Instantiates a new CustomValidator with default values.
     */
    public CustomValidator() {
        maxLength = 32;
        minLength = 3;
        notEmptyErrorMessage = "Cannot be empty";
        emailErrorMessage = "Invalid email address";
        onlyLettersErrorMessage = "Please enter only letters";
        onlyLettersWithSpacesErrorMessage = "Please enter only letters";
        onlyLatinLettersErrorMessage = "Please enter only letters";
        onlyLatinLettersWithSpacesErrorMessage = "Please enter only letters";
        alphanumericErrorMessage = "Please enter only letters or digits";
        onlyDigitsErrorMessage = "Please enter only digits";
        maxLengthErrorMessage = "Length should be less than or equal to ";
        minLengthErrorMessage = "Length should be greater than or equal to ";
        lowercaseErrorMessage = "All letters should be in lower case";
        uppercaseErrorMessage = "All letters should be in upper case";
        atLeastOneLowercaseErrorMessage = "At least one letter should be in lower case";
        atLeastOneUppercaseErrorMessage = "At least one letter should be in upper case";
        atLeastOneDigitErrorMessage = "Should contain at least one digit";
        atLeastOneSpecialCharacterErrorMessage = "Should contain at least one special character";
        atLeastOneLetterErrorMessage = "Should contain at least one letter";
        postalCodeErrorMessage = "Invalid postal code";
        onlyCustomRegexErrorMessage = "Please enter only custom regex";
        atLeastOneCustomRegexErrorMessage = "At least one letter should be like custom regex";
    }

    /**
     * Checks if the string is not empty.
     *
     * @param value the string value that to be checked
     * @return true if the string value is not empty or false if the string value is empty
     */
    public boolean isNotEmpty(String value) {
        return value != null && !value.isEmpty();
    }

    /**
     * Checks if the string is an email address.
     *
     * @param value the string value that to be checked
     * @return true if the string value is an email address, otherwise false
     */
    public boolean isEmail(String value) {
        return Patterns.EMAIL_ADDRESS.matcher(value).matches();
    }

    /**
     * Checks if the string contains only letters.
     *
     * @param value the string value that to be checked
     * @return true if the string value contains only letters, otherwise false
     */
    public boolean isOnlyLetters(String value) {
        return Pattern.matches("\\p{L}+", value);
    }

    /**
     * Checks if the string contains only letters with spaces.
     *
     * @param value the string value that to be checked
     * @return true if the string value contains only letters with spaces or without spaces, otherwise false
     */
    public boolean isOnlyLettersWithSpaces(String value) {
        return Pattern.matches("[\\p{L}\\s]+", value);
    }

    /**
     * Checks if the string contains only latin letters.
     *
     * @param value the string value that to be checked
     * @return true if the string value contains only latin letters, otherwise false
     */
    public boolean isOnlyLatinLetters(String value) {
        return Pattern.matches("[a-zA-Z]+", value);
    }

    /**
     * Checks if the string contains only latin letters with spaces.
     *
     * @param value the string value that to be checked
     * @return true if the string value contains only latin letters with spaces or without spaces, otherwise false
     */
    public boolean isOnlyLatinLetterWithSpaces(String value) {
        return Pattern.matches("[a-zA-Z\\s]+", value);
    }

    /**
     * Checks if the string contains only letters and numbers.
     *
     * @param value the string value that to be checked
     * @return true if the string value contains only letters and numbers, otherwise false
     */
    public boolean isAlphanumeric(String value) {
        return Pattern.matches("^[a-zA-Z0-9]+$", value);
    }

    /**
     * Checks if the string contains only digits.
     *
     * @param value the string value that to be checked
     * @return true if the string value contains only digits, otherwise false
     */
    public boolean isOnlyDigits(String value) {
        return Pattern.matches("\\d+", value);
    }

    /**
     * Checks if the string length is less than or equal to the max length.
     *
     * @param value the string value that to be checked
     * @param maxLength max string length
     * @return true if the string length is less than or equal to the max length, otherwise false
     */
    public boolean isLessThanOrEqualToTheMaxLength(String value, int maxLength) {
        this.maxLength = maxLength;
        return value.length() <= maxLength;
    }

    /**
     * Checks if the string length is greater than or equal to the max length.
     *
     * @param value the string value that to be checked
     * @param minLength min string length
     * @return true if the string length is greater than or equal to the max length, otherwise false
     */
    public boolean isGreaterThanOrEqualToMinLength(String value, int minLength) {
        this.minLength = minLength;
        return value.length() >= minLength;
    }

    /**
     * Checks if the string is all lowercase.
     *
     * @param value the string value that to be checked
     * @return true if the string is all lowercase, otherwise false
     */
    public boolean isLowercase(String value) {
        return value.equals(value.toLowerCase());
    }

    /**
     * Checks if the string is all uppercase.
     *
     * @param value the string value that to be checked
     * @return true if the string is all uppercase, otherwise false
     */
    public boolean isUppercase(String value) {
        return value.equals(value.toUpperCase());
    }

    /**
     * Checks if the string has at least one lowercase character.
     *
     * @param value the string value that to be checked
     * @return true if the string has at least one lowercase character, otherwise false
     */
    public boolean hasAtLeastOneLowercase(String value) {

        return Pattern.compile("[a-z]").matcher(value).find();
    }

    /**
     * Checks if the string has at least one uppercase character.
     *
     * @param value the string value that to be checked
     * @return true if the string has at least one uppercase character, otherwise false
     */
    public boolean hasAtLeastOneUppercase(String value) {
        return Pattern.compile("[A-Z]").matcher(value).find();
    }

    /**
     * Checks if the string has at least one digit.
     *
     * @param value the string value that to be checked
     * @return true if the string has at least one digit, otherwise false
     */
    public boolean hasAtLeastOneDigit(String value) {
        return Pattern.compile(".*\\d.*").matcher(value).find();
    }

    /**
     * Checks if the string has at least one special character.
     *
     * @param value the string value that to be checked
     * @return true if the string has at least one special character, otherwise false
     */
    public boolean hasAtLeastOneSpecialCharacter(String value) {
        return Pattern.compile("[^a-zA-Z0-9\\s]").matcher(value).find();
    }

    /**
     * Checks if the string has at least one letter.
     *
     * @param value the string value that to be checked
     * @return true if the string has at least one letter, otherwise false
     */
    public boolean hasAtLeastOneLetter(String value) {
        return Pattern.compile("[a-zA-Z]").matcher(value).find();
    }

    /**
     * Checks if the string is valid postal code.
     *
     * @param value the string value that to be checked
     * @return true if the string is valid postal code, otherwise false
     */
    public boolean isValidPostalCode(String value, String postalCodeRegex) {
        return Pattern.matches(postalCodeRegex, value);
    }

    /**
     * Checks if the string contains only custom regex.
     *
     * @param value the string value that to be checked
     * @return true if the string contains only custom regex, otherwise false
     */
    public boolean isOnlyCustomRegex(String value, String myRegexPattern) {
        return Pattern.matches(myRegexPattern, value);
    }

    /**
     * Checks if the string has at least one custom regex character.
     *
     * @param value the string value that to be checked
     * @return true if the string has at least one custom regex character, otherwise false
     */
    public boolean hasAtLeastOneCustomRegex(String value, String myRegexPattern) {
        return Pattern.compile(myRegexPattern).matcher(value).find();
    }

    public int getMaxLength() {
        return maxLength;
    }

    void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    public int getMinLength() {
        return minLength;
    }

    void setMinLength(int minLength) {
        this.minLength = minLength;
    }

    public String getNotEmptyErrorMessage() {
        return notEmptyErrorMessage;
    }

    public void setNotEmptyErrorMessage(String notEmptyErrorMessage) {
        this.notEmptyErrorMessage = notEmptyErrorMessage;
    }

    public String getEmailErrorMessage() {
        return emailErrorMessage;
    }

    public void setEmailErrorMessage(String emailErrorMessage) {
        this.emailErrorMessage = emailErrorMessage;
    }

    public String getOnlyLettersErrorMessage() {
        return onlyLettersErrorMessage;
    }

    public void setOnlyLettersErrorMessage(String onlyLettersErrorMessage) {
        this.onlyLettersErrorMessage = onlyLettersErrorMessage;
    }

    public String getOnlyLettersWithSpacesErrorMessage() {
        return onlyLettersWithSpacesErrorMessage;
    }

    public void setOnlyLettersWithSpacesErrorMessage(String onlyLettersWithSpacesErrorMessage) {
        this.onlyLettersWithSpacesErrorMessage = onlyLettersWithSpacesErrorMessage;
    }

    public String getOnlyLatinLettersErrorMessage() {
        return onlyLatinLettersErrorMessage;
    }

    public void setOnlyLatinLettersErrorMessage(String onlyLatinLettersErrorMessage) {
        this.onlyLatinLettersErrorMessage = onlyLatinLettersErrorMessage;
    }

    public String getOnlyLatinLettersWithSpacesErrorMessage() {
        return onlyLatinLettersWithSpacesErrorMessage;
    }

    public void setOnlyLatinLettersWithSpacesErrorMessage(String onlyLatinLettersWithSpacesErrorMessage) {
        this.onlyLatinLettersWithSpacesErrorMessage = onlyLatinLettersWithSpacesErrorMessage;
    }

    public String getAlphanumericErrorMessage() {
        return alphanumericErrorMessage;
    }

    public void setAlphanumericErrorMessage(String alphanumericErrorMessage) {
        this.alphanumericErrorMessage = alphanumericErrorMessage;
    }

    public String getOnlyDigitsErrorMessage() {
        return onlyDigitsErrorMessage;
    }

    public void setOnlyDigitsErrorMessage(String onlyDigitsErrorMessage) {
        this.onlyDigitsErrorMessage = onlyDigitsErrorMessage;
    }

    public String getMaxLengthErrorMessage() {
        return maxLengthErrorMessage + maxLength;
    }

    public void setMaxLengthErrorMessage(String maxLengthErrorMessage) {
        this.maxLengthErrorMessage = maxLengthErrorMessage;
    }

    public String getMinLengthErrorMessage() {
        return minLengthErrorMessage + minLength;
    }

    public void setMinLengthErrorMessage(String minLengthErrorMessage) {
        this.minLengthErrorMessage = minLengthErrorMessage;
    }

    public String getLowercaseErrorMessage() {
        return lowercaseErrorMessage;
    }

    public void setLowercaseErrorMessage(String lowercaseErrorMessage) {
        this.lowercaseErrorMessage = lowercaseErrorMessage;
    }

    public String getUppercaseErrorMessage() {
        return uppercaseErrorMessage;
    }

    public void setUppercaseErrorMessage(String uppercaseErrorMessage) {
        this.uppercaseErrorMessage = uppercaseErrorMessage;
    }

    public String getAtLeastOneLowercaseErrorMessage() {
        return atLeastOneLowercaseErrorMessage;
    }

    public void setAtLeastOneLowercaseErrorMessage(String atLeastOneLowercaseErrorMessage) {
        this.atLeastOneLowercaseErrorMessage = atLeastOneLowercaseErrorMessage;
    }

    public String getAtLeastOneUppercaseErrorMessage() {
        return atLeastOneUppercaseErrorMessage;
    }

    public void setAtLeastOneUppercaseErrorMessage(String atLeastOneUppercaseErrorMessage) {
        this.atLeastOneUppercaseErrorMessage = atLeastOneUppercaseErrorMessage;
    }

    public String getAtLeastOneDigitErrorMessage() {
        return atLeastOneDigitErrorMessage;
    }

    public void setAtLeastOneDigitErrorMessage(String atLeastOneDigitErrorMessage) {
        this.atLeastOneDigitErrorMessage = atLeastOneDigitErrorMessage;
    }

    public String getAtLeastOneSpecialCharacterErrorMessage() {
        return atLeastOneSpecialCharacterErrorMessage;
    }

    public void setAtLeastOneSpecialCharacterErrorMessage(String atLeastOneSpecialCharacterErrorMessage) {
        this.atLeastOneSpecialCharacterErrorMessage = atLeastOneSpecialCharacterErrorMessage;
    }

    public String getAtLeastOneLetterErrorMessage() {
        return atLeastOneLetterErrorMessage;
    }

    public void setAtLeastOneLetterErrorMessage(String atLeastOneLetterErrorMessage) {
        this.atLeastOneLetterErrorMessage = atLeastOneLetterErrorMessage;
    }

    public String getPostalCodeErrorMessage() {
        return postalCodeErrorMessage;
    }

    public void setPostalCodeErrorMessage(String postalCodeErrorMessage) {
        this.postalCodeErrorMessage = postalCodeErrorMessage;
    }

    public String getOnlyCustomRegexErrorMessage() {
        return onlyCustomRegexErrorMessage;
    }

    public void setOnlyCustomRegexErrorMessage(String onlyCustomRegexErrorMessage) {
        this.onlyCustomRegexErrorMessage = onlyCustomRegexErrorMessage;
    }

    public String getAtLeastOneCustomRegexErrorMessage() {
        return atLeastOneCustomRegexErrorMessage;
    }

    public void setAtLeastOneCustomRegexErrorMessage(String atLeastOneCustomRegexErrorMessage) {
        this.atLeastOneCustomRegexErrorMessage = atLeastOneCustomRegexErrorMessage;
    }
}
