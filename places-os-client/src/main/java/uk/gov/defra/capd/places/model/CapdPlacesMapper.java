package uk.gov.defra.capd.places.model;

import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;

public class CapdPlacesMapper extends org.modelmapper.ModelMapper {
    {
        final Converter<OsPlacesResponse, String> postCodeConverter = new AbstractConverter<OsPlacesResponse, String>() {
            protected String convert(OsPlacesResponse osPlaces) {
                return osPlaces.getHeader().getQuery().split("=")[1].toUpperCase();
            }
        };

        TypeMap<OsPlacesResponse, CapdPlaces> placesMap =  createTypeMap(OsPlacesResponse.class, CapdPlaces.class);
        placesMap.addMappings(new PropertyMap<OsPlacesResponse, CapdPlaces>() {
            @Override
            protected void configure() {
                using(postCodeConverter).map(source).setPostCode(null);
                map(source.getResults()).setAddresses(null);
            }
        });

        TypeMap<OsPlacesResponse.Result, CapdPlaces.Address> addressMap =  createTypeMap(OsPlacesResponse.Result.class, CapdPlaces.Address.class);
        addressMap.addMappings(new PropertyMap<OsPlacesResponse.Result, CapdPlaces.Address>() {
            @Override
            protected void configure() {
                map().setAddress(source.getDpa().getAddress());
                map().setBuildingNumber(source.getDpa().getBuildingNumber());
                map().setBuildingName(source.getDpa().getBuildingName());
                map().setCity(source.getDpa().getPostTown());
                map().setStreet(source.getDpa().getThoroughFareName());
                map().setUprn(source.getDpa().getUprn());
                map().setFlatNumber(source.getDpa().getSubBuildingName());
                map().setDependentLocality(source.getDpa().getDependentLocality());
                map().setDoubleDependentLocality(source.getDpa().getDoubleDependentLocality());
            }
        });


    }
}
