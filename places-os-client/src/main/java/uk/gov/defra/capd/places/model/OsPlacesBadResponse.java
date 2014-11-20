package uk.gov.defra.capd.places.model;

public class OsPlacesBadResponse {

    private Error error;

    public Error getError() {
        return error;
    }

    public static class Error {
        private int statuscode;
        private String message;

        public String getMessage() {
            return message;
        }

        public int getStatuscode() {
            return statuscode;
        }

    }
}
