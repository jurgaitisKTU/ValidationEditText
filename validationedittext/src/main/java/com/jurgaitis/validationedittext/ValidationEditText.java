package com.jurgaitis.validationedittext;

import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

import java.util.Arrays;
import java.util.List;

public class ValidationEditText extends AppCompatEditText {
    /**
     * Response message, when field is valid
     */
    private final String CORRECT_MESSAGE = "Correct";
    private List<String> activeValidators;
    private boolean validateWhenTextChanged = false;
    private String postalCodeRegex = "";
    private String onlyCustomRegex = "";
    private String atLeastOneRegex = "";
    private EditTextValidationListener editTextResponseListener;
    private CustomValidator customValidator = new CustomValidator();
    private long delayWhenStopsTyping = 1200; // 1,2 seconds after user stops typing
    private long lastTimeWhenTextChanged = 0;
    private Handler editTextHandler = new Handler();
    private boolean showErrorMessageWhenStoppedTyping = true;

    public ValidationEditText(Context context) {
        super(context);
    }

    public ValidationEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ValidationEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * Adds validation listener.
     *
     * @param editTextResponseListener
     */
    public void addValidationListener(EditTextValidationListener editTextResponseListener) {
        this.editTextResponseListener = editTextResponseListener;
        validateWhenTextChanged = true;
        validate(false);
    }

    /**
     * Sets the rules list to check for validation.
     *
     * @param validators validators, which must be selected from ValidationSettings
     * @return current ValidationEditText
     */
    public ValidationEditText setValidators(String... validators) {
        validateWhenTextChanged = true;
        activeValidators = Arrays.asList(validators);
        return this;
    }

    /**
     * Sets max editText length.
     *
     * @param maxLength max string length
     * @return current ValidationEditText
     */
    public ValidationEditText setMaxLength(int maxLength) {
        this.customValidator.setMaxLength(maxLength);
        return this;
    }

    /**
     * Sets min editText length.
     *
     * @param minLength min string length
     * @return current ValidationEditText
     */
    public ValidationEditText setMinLength(int minLength) {
        this.customValidator.setMinLength(minLength);
        return this;
    }

    /**
     * Sets postal code regex.
     *
     * @param postalCodeRegex postal code regex, which can be selected from PostalCodeRegex
     * @return current ValidationEditText
     */
    public ValidationEditText setPostalCodeRegex(String postalCodeRegex) {
        this.postalCodeRegex = postalCodeRegex;
        return this;
    }

    /**
     * Sets customValidator.
     *
     * @param customValidator custom validator. Here you can change default validation error messages
     * @return current ValidationEditText
     */
    public ValidationEditText setCustomValidator(CustomValidator customValidator) {
        customValidator.setMaxLength(this.customValidator.getMaxLength());
        customValidator.setMinLength(this.customValidator.getMinLength());
        this.customValidator = customValidator;
        return this;
    }

    /**
     * Sets custom validation regex.
     *
     * @param onlyCustomRegex custom validation regex
     * @return current ValidationEditText
     */
    public ValidationEditText setOnlyCustomRegex(String onlyCustomRegex) {
        this.onlyCustomRegex = onlyCustomRegex;
        return this;
    }

    /**
     * Sets custom validation regex.
     *
     * @param atLeastOneRegex custom validation regex
     * @return current ValidationEditText
     */
    public ValidationEditText setAtLeastOneRegex(String atLeastOneRegex) {
        this.atLeastOneRegex = atLeastOneRegex;
        return this;
    }

    /**
     * Sets to show or hide error message.
     *
     * @param showErrorMessageWhenStoppedTyping true - show error message when user stops typing
     * @return current ValidationEditText
     */
    public ValidationEditText setShowErrorMessageWhenStoppedTyping(boolean showErrorMessageWhenStoppedTyping) {
        this.showErrorMessageWhenStoppedTyping = showErrorMessageWhenStoppedTyping;
        return this;
    }

    /**
     * Sets delay time in milliseconds.
     *
     * @param delayWhenStopsTyping delay time in milliseconds, when user stop typing
     * @return current ValidationEditText
     */
    public ValidationEditText setDelayWhenStopsTyping(long delayWhenStopsTyping) {
        this.delayWhenStopsTyping = delayWhenStopsTyping;
        return this;
    }

    /**
     * Validate input when user stops typing.
     */
    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        if (validateWhenTextChanged) {
            validate(false);
            if (editTextHandler != null && showErrorMessageWhenStoppedTyping) {
                editTextHandler.removeCallbacks(stoppedToWriteChecker);
                lastTimeWhenTextChanged = System.currentTimeMillis();
                editTextHandler.postDelayed(stoppedToWriteChecker, delayWhenStopsTyping);
            }
        }
    }

    private Runnable stoppedToWriteChecker = new Runnable() {
        public void run() {
            if (System.currentTimeMillis() > (lastTimeWhenTextChanged + delayWhenStopsTyping - 500)) {
                validate(true);
            }
        }
    };

    /**
     * Validate input when user stops typing.
     */

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        if (!focused && validateWhenTextChanged && showErrorMessageWhenStoppedTyping) {
            validate(true);
        }
    }

    /**
     * Validating editText field, when text changed.
     *
     * @param showErrorMessage show error message in editText? true - show
     */
    private void validate(boolean showErrorMessage) {
        if (!isNotEmpty() && activeValidators.contains(ValidationSettings.NOT_EMPTY)) {
            sendValidationResponse(showErrorMessage, false, customValidator.getNotEmptyErrorMessage());
            return;
        }

        if (!isEmail() && activeValidators.contains(ValidationSettings.EMAIL)) {
            sendValidationResponse(showErrorMessage, false, customValidator.getEmailErrorMessage());
            return;
        }

        if (!isOnlyLetters() && activeValidators.contains(ValidationSettings.ONLY_LETTERS)) {
            sendValidationResponse(showErrorMessage, false, customValidator.getOnlyLettersErrorMessage());
            return;
        }

        if (!isOnlyLettersWithSpaces() && activeValidators.contains(ValidationSettings.ONLY_LETTERS_WITH_SPACES)) {
            sendValidationResponse(showErrorMessage, false, customValidator.getOnlyLettersWithSpacesErrorMessage());
            return;
        }

        if (!isOnlyLatinLetters() && activeValidators.contains(ValidationSettings.ONLY_LATIN_LETTERS)) {
            sendValidationResponse(showErrorMessage, false, customValidator.getOnlyLatinLettersErrorMessage());
            return;
        }

        if (!isOnlyLatinLetterWithSpaces() && activeValidators.contains(ValidationSettings.ONLY_LATIN_LETTERS_WITH_SPACES)) {
            sendValidationResponse(showErrorMessage, false, customValidator.getOnlyLatinLettersWithSpacesErrorMessage());
            return;
        }

        if (!isAlphanumeric() && activeValidators.contains(ValidationSettings.ALPHANUMERIC)) {
            sendValidationResponse(showErrorMessage, false, customValidator.getAlphanumericErrorMessage());
            return;
        }

        if (!isOnlyDigits() && activeValidators.contains(ValidationSettings.ONLY_DIGITS)) {
            sendValidationResponse(showErrorMessage, false, customValidator.getOnlyDigitsErrorMessage());
            return;
        }

        if (!isLessThanOrEqualToTheMaxLength(this.customValidator.getMaxLength()) && activeValidators.contains(ValidationSettings.MAX_LENGTH)) {
            sendValidationResponse(showErrorMessage, false, customValidator.getMaxLengthErrorMessage());
            return;
        }

        if (!isGreaterThanOrEqualToMinLength(this.customValidator.getMinLength()) && activeValidators.contains(ValidationSettings.MIN_LENGTH)) {
            sendValidationResponse(showErrorMessage, false, customValidator.getMinLengthErrorMessage());
            return;
        }

        if (!isLowercase() && activeValidators.contains(ValidationSettings.ALL_LOWERCASE)) {
            sendValidationResponse(showErrorMessage, false, customValidator.getLowercaseErrorMessage());
            return;
        }

        if (!isUppercase() && activeValidators.contains(ValidationSettings.ALL_UPPERCASE)) {
            sendValidationResponse(showErrorMessage, false, customValidator.getUppercaseErrorMessage());
            return;
        }

        if (!hasAtLeastOneLowercase() && activeValidators.contains(ValidationSettings.AT_LEAST_ONE_LOWERCASE)) {
            sendValidationResponse(showErrorMessage, false, customValidator.getAtLeastOneLowercaseErrorMessage());
            return;
        }

        if (!hasAtLeastOneUppercase() && activeValidators.contains(ValidationSettings.AT_LEAST_ONE_UPPERCASE)) {
            sendValidationResponse(showErrorMessage, false, customValidator.getAtLeastOneUppercaseErrorMessage());
            return;
        }

        if (!hasAtLeastOneDigit() && activeValidators.contains(ValidationSettings.AT_LEAST_ONE_DIGIT)) {
            sendValidationResponse(showErrorMessage, false, customValidator.getAtLeastOneDigitErrorMessage());
            return;
        }

        if (!hasAtLeastOneSpecialCharacter() && activeValidators.contains(ValidationSettings.AT_LEAST_ONE_SPECIAL_CHARACTER)) {
            sendValidationResponse(showErrorMessage, false, customValidator.getAtLeastOneSpecialCharacterErrorMessage());
            return;
        }

        if (!hasAtLeastOneLetter() && activeValidators.contains(ValidationSettings.AT_LEAST_ONE_LETTER)) {
            sendValidationResponse(showErrorMessage, false, customValidator.getAtLeastOneLetterErrorMessage());
            return;
        }

        if (!isValidPostalCode(this.postalCodeRegex) && activeValidators.contains(ValidationSettings.VALID_POSTAL_CODE) && !postalCodeRegex.equals("")) {
            sendValidationResponse(showErrorMessage, false, customValidator.getPostalCodeErrorMessage());
            return;
        }

        if (!isOnlyCustomRegex(onlyCustomRegex) && activeValidators.contains(ValidationSettings.ONLY_CUSTOM_REGEX) && !onlyCustomRegex.equals("")) {
            sendValidationResponse(showErrorMessage, false, customValidator.getOnlyCustomRegexErrorMessage());
            return;
        }

        if (!hasAtLeastOneCustomRegex(atLeastOneRegex) && activeValidators.contains(ValidationSettings.AT_LEAST_ONE_CUSTOM_REGEX) && !atLeastOneRegex.equals("")) {
            sendValidationResponse(showErrorMessage, false, customValidator.getAtLeastOneCustomRegexErrorMessage());
            return;
        }

        sendValidationResponse(showErrorMessage, true, CORRECT_MESSAGE);
    }

    /**
     * Sends validation response to user app
     *
     * @param showErrorMessage show or hide error message
     * @param validField       is valid field response to user
     * @param responseMessage  response message to user
     */
    private void sendValidationResponse(boolean showErrorMessage, boolean validField, String responseMessage) {
        if (editTextResponseListener != null) {
            editTextResponseListener.onValidationResponse(validField, responseMessage);
        }
        if (showErrorMessage) {
            if (validField) {
                setError(null);
            } else {
                setError(responseMessage);
            }
        }
    }

    /* validation methods */

    /**
     * Checks if the ValidationEditText is not empty.
     *
     * @return true if the ValidationEditText value is not empty or false if the string value is empty
     */
    public boolean isNotEmpty() {
        if (this.getText() != null) {
            return customValidator.isNotEmpty(this.getText().toString());
        }
        return false;
    }

    /**
     * Checks if the ValidationEditText is an email address.
     *
     * @return true if the ValidationEditText value is an email address, otherwise false
     */
    public boolean isEmail() {
        if (this.getText() != null) {
            return customValidator.isEmail(this.getText().toString());
        }
        return false;
    }

    /**
     * Checks if the ValidationEditText contains only letters.
     *
     * @return true if the ValidationEditText value contains only letters, otherwise false
     */
    public boolean isOnlyLetters() {
        if (this.getText() != null) {
            return customValidator.isOnlyLetters(this.getText().toString());
        }
        return false;
    }

    /**
     * Checks if the ValidationEditText contains only letters with spaces.
     *
     * @return true if the ValidationEditText value contains only letters with spaces or without spaces, otherwise false
     */
    public boolean isOnlyLettersWithSpaces() {
        if (this.getText() != null) {
            return customValidator.isOnlyLettersWithSpaces(this.getText().toString());
        }
        return false;
    }

    /**
     * Checks if the ValidationEditText contains only latin letters.
     *
     * @return true if the ValidationEditText value contains only latin letters, otherwise false
     */
    public boolean isOnlyLatinLetters() {
        if (this.getText() != null) {
            return customValidator.isOnlyLatinLetters(this.getText().toString());
        }
        return false;
    }

    /**
     * Checks if the ValidationEditText contains only latin letters with spaces.
     *
     * @return true if the ValidationEditText value contains only latin letters with spaces or without spaces, otherwise false
     */
    public boolean isOnlyLatinLetterWithSpaces() {
        if (this.getText() != null) {
            return customValidator.isOnlyLatinLetterWithSpaces(this.getText().toString());
        }
        return false;
    }

    /**
     * Checks if the ValidationEditText contains only letters and numbers.
     *
     * @return true if the ValidationEditText value contains only letters and numbers, otherwise false
     */
    public boolean isAlphanumeric() {
        if (this.getText() != null) {
            return customValidator.isAlphanumeric(this.getText().toString());
        }
        return false;
    }

    /**
     * Checks if the ValidationEditText contains only digits.
     *
     * @return true if the ValidationEditText value contains only digits, otherwise false
     */
    public boolean isOnlyDigits() {
        if (this.getText() != null) {
            return customValidator.isOnlyDigits(this.getText().toString());
        }
        return false;
    }

    /**
     * Checks if the ValidationEditText length is less than or equal to the max length.
     *
     * @param maxLength max ValidationEditText length
     * @return true if the ValidationEditText length is less than or equal to the max length, otherwise false
     */
    public boolean isLessThanOrEqualToTheMaxLength(int maxLength) {
        if (this.getText() != null) {
            return customValidator.isLessThanOrEqualToTheMaxLength(this.getText().toString(), maxLength);
        }
        return false;
    }

    /**
     * Checks if the ValidationEditText length is greater than or equal to the max length.
     *
     * @param minLength min ValidationEditText length
     * @return true if the ValidationEditText length is greater than or equal to the max length, otherwise false
     */
    public boolean isGreaterThanOrEqualToMinLength(int minLength) {
        if (this.getText() != null) {
            return customValidator.isGreaterThanOrEqualToMinLength(this.getText().toString(), minLength);
        }
        return false;
    }

    /**
     * Checks if the ValidationEditText is all lowercase.
     *
     * @return true if the ValidationEditText is all lowercase, otherwise false
     */
    public boolean isLowercase() {
        if (this.getText() != null) {
            return customValidator.isLowercase(this.getText().toString());
        }
        return false;
    }

    /**
     * Checks if the ValidationEditText is all uppercase.
     *
     * @return true if the ValidationEditText is all uppercase, otherwise false
     */
    public boolean isUppercase() {
        if (this.getText() != null) {
            return customValidator.isUppercase(this.getText().toString());
        }
        return false;
    }

    /**
     * Checks if the ValidationEditText has at least one lowercase character.
     *
     * @return true if the ValidationEditText has at least one lowercase character, otherwise false
     */
    public boolean hasAtLeastOneLowercase() {
        if (this.getText() != null) {
            return customValidator.hasAtLeastOneLowercase(this.getText().toString());
        }
        return false;
    }

    /**
     * Checks if the ValidationEditText has at least one uppercase character.
     *
     * @return true if the ValidationEditText has at least one uppercase character, otherwise false
     */
    public boolean hasAtLeastOneUppercase() {
        if (this.getText() != null) {
            return customValidator.hasAtLeastOneUppercase(this.getText().toString());
        }
        return false;
    }

    /**
     * Checks if the ValidationEditText has at least one digit.
     *
     * @return true if the ValidationEditText has at least one digit, otherwise false
     */
    public boolean hasAtLeastOneDigit() {
        if (this.getText() != null) {
            return customValidator.hasAtLeastOneDigit(this.getText().toString());
        }
        return false;
    }

    /**
     * Checks if the ValidationEditText has at least one special character.
     *
     * @return true if the ValidationEditText has at least one special character, otherwise false
     */
    public boolean hasAtLeastOneSpecialCharacter() {
        if (this.getText() != null) {
            return customValidator.hasAtLeastOneSpecialCharacter(this.getText().toString());
        }
        return false;
    }

    /**
     * Checks if the ValidationEditText has at least one letter.
     *
     * @return true if the ValidationEditText has at least one letter, otherwise false
     */
    public boolean hasAtLeastOneLetter() {
        if (this.getText() != null) {
            return customValidator.hasAtLeastOneLetter(this.getText().toString());
        }
        return false;
    }

    /**
     * Checks if the ValidationEditText is valid postal code.
     *
     * @return true if the ValidationEditText is valid postal code, otherwise false
     */
    public boolean isValidPostalCode(String postalCodeRegex) {
        if (this.getText() != null) {
            return customValidator.isValidPostalCode(this.getText().toString(), postalCodeRegex);
        }
        return false;
    }

    /**
     * Checks if the ValidationEditText contains only custom regex.
     *
     * @return true if the ValidationEditText contains only custom regex, otherwise false
     */
    public boolean isOnlyCustomRegex(String onlyCustomRegex) {
        if (this.getText() != null) {
            return customValidator.isOnlyCustomRegex(this.getText().toString(), onlyCustomRegex);
        }
        return false;
    }

    /**
     * Checks if the ValidationEditText has at least one custom regex character.
     *
     * @return true if the ValidationEditText has at least one custom regex character, otherwise false
     */
    public boolean hasAtLeastOneCustomRegex(String atLeastOneRegex) {
        if (this.getText() != null) {
            return customValidator.hasAtLeastOneCustomRegex(this.getText().toString(), atLeastOneRegex);
        }
        return false;
    }
}