package uk.gov.defra.capd.places.model;


import java.util.List;
public class CapdPlaces {

    private String postCode;

    private List<Address> addresses;

    private String message;


    public static class Address {
        private String uprn;
        private String address;
        private String buildingNumber;
        private String buildingName;
        private String flatNumber;
        private String street;
        private String city;
        private String dependentLocality;
        private String doubleDependentLocality;

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getUprn() {
            return uprn;
        }

        public void setUprn(String uprn) {
            this.uprn = uprn;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getBuildingNumber() {
            return buildingNumber;
        }

        public void setBuildingNumber(String buildingNumber) {
            this.buildingNumber = buildingNumber;
        }

        public String getBuildingName() { return buildingName; }

        public void setBuildingName(String buildingName) { this.buildingName = buildingName; }

        public String getFlatNumber() {
            return flatNumber;
        }

        public void setFlatNumber(String flatNumber) {
            this.flatNumber = flatNumber;
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public String getDependentLocality() {
            return dependentLocality;
        }

        public void setDependentLocality(String dependentLocality) {
            this.dependentLocality = dependentLocality;
        }

        public String getDoubleDependentLocality() {
            return doubleDependentLocality;
        }

        public void setDoubleDependentLocality(String doubleDependentLocality) {
            this.doubleDependentLocality = doubleDependentLocality;
        }
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getMessage() { return message; }

    public void setMessage(String message) { this.message = message; }

}
