package uk.gov.defra.capd.places.model;



import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OsPlacesResponse {

    private Header header;

    private List<Result> results;

    public Header getHeader() { return header; }

    public List<Result> getResults() { return results; }

    public static class Header {

        private String uri;
        private String query;
        private String offset;
        @JsonProperty(value = "totalresults")
        private String totalResults;
        private String format;
        private String dataset;
        private String lr;
        @JsonProperty(value = "maxresults")
        private String maxResults;

        public String getUri() { return uri; }

        public String getQuery() { return query; }

        public String getOffset() { return offset; }

        public String getTotalresults() { return totalResults; }

        public String getFormat() { return format; }

        public String getDataset() { return dataset; }

        public String getLr() { return lr; }

        public String getMaxResults() { return maxResults; }
    }


    public static class Result {

        @JsonProperty(value = "DPA")
        private DPA dpa;

        public DPA getDpa() {
            return dpa;
        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class DPA {
            @JsonProperty(value = "UPRN")
            private String uprn;
            @JsonProperty(value = "ADDRESS")
            private String address;
            @JsonProperty(value = "BUILDING_NUMBER")
            private String buildingNumber;
            @JsonProperty(value = "THOROUGHFARE_NAME")
            private String thoroughFareName;
            @JsonProperty(value = "POST_TOWN")
            private String postTown;
            @JsonProperty(value = "POSTCODE")
            private String postCode;
            @JsonProperty(value = "RPC")
            private String rpc;
            @JsonProperty(value = "X_COORDINATE")
            private String xCoordinate;
            @JsonProperty(value = "Y_COORDINATE")
            private String yCoordinate;
            @JsonProperty(value = "STATUS")
            private String status;
            @JsonProperty(value = "MATCH")
            private String match;
            @JsonProperty(value = "MATCH_DESCRIPTION")
            private String matchDescription;
            @JsonProperty(value = "ORGANISATION_NAME")
            private String organisationName;
            @JsonProperty(value = "SUB_BUILDING_NAME")
            private String subBuildingName;
            @JsonProperty(value = "BUILDING_NAME")
            private String buildingName;
            @JsonProperty(value = "DEPENDENT_LOCALITY")
            private String dependentLocality;
            @JsonProperty(value = "DOUBLE_DEPENDENT_LOCALITY")
            private String doubleDependentLocality;

            public String getUprn() { return uprn; }

            public String getAddress() { return address; }

            public String getBuildingNumber() { return buildingNumber; }

            public String getThoroughFareName() { return thoroughFareName; }

            public String getPostTown() { return postTown; }

            public String getPostCode() { return postCode; }

            public String getRpc() { return rpc; }

            public String getxCoordinate() { return xCoordinate; }

            public String getyCoordinate() { return yCoordinate; }

            public String getStatus() { return status; }

            public String getMatch() { return match; }

            public String getMatchDescription() { return matchDescription; }

            public String getBuildingName() { return buildingName; }

            public String getOrganisationName() { return organisationName; }

            public String getSubBuildingName() { return subBuildingName; }

            public String getDependentLocality() {
                return dependentLocality;
            }

            public String getDoubleDependentLocality() {
                return doubleDependentLocality;
            }
        }

    }


}
