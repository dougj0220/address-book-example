package io.doug.util;

import java.io.Serializable;

public class ApiResponse<T> implements Serializable {

    private static final long serialVersionUID = 5011111620028706233L;

    private String message;

    private T payload;

    /**
     * Generates a ApiReponse object with blank defaults for all members
     */
    public ApiResponse() {
    }

    /**
     * Generates a ApiReponse object with blank defaults for all members
     * 
     * @param payload
     *            - Payload to be send back to client UI
     */
    public ApiResponse(T payload) {
        this.payload = payload;
    }

    /**
     * Generates a ApiReponse object with blank defaults for all members
     * 
     * @param payload
     */
    public ApiResponse(String message, T payload) {
        this.message = message;
        this.payload = payload;
    }


    //----g/s---
    /**
     * Returns the message
     * 
     * @return : Returns the message contained in the ApiRepsonse
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the message of the ApiResponse
     * 
     * @param message
     *            : Message to store in the ApiResponse
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Returns the payload of type T, and defined by the instantiation of this
     * ApiResponse
     * 
     * @return : The payload of type T
     */
    public T getPayload() {
        return payload;
    }

    /**
     * Sets the payload of type T, as defined by the instantiation of this
     * ApiResponse
     * 
     * @param payload
     *            : Payload to set in the ApiResponse
     */
    public void setPayload(T payload) {
        this.payload = payload;
    }

}