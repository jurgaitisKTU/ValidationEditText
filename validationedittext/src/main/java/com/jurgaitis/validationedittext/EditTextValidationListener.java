package com.jurgaitis.validationedittext;

/**
 * EditText validation listener interface
 */
public interface EditTextValidationListener {
    /**
     *
     * @param isValid EditTex is valid or not. Put "false", if field is invalid
     * @param message validation response message. Error message, if field is invalid
     */
    void onValidationResponse(boolean isValid, String message);
}
