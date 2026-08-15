package com.example.propertyView.service;

import com.example.propertyView.dto.*;
import com.example.propertyView.entity.*;
import com.example.propertyView.exception.ResourceNotFoundException;
import com.example.propertyView.repository.*;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class HotelService {

    private final HotelRepository hotelRepository;
    private final AddressRepository addressRepository;
    private final ContactRepository contactRepository;
    private final ArrivalTimeRepository arrivalTimeRepository;
    private final AmenityRepository amenityRepository;

    public HotelService(
            HotelRepository hotelRepository,
            AddressRepository addressRepository,
            ContactRepository contactRepository,
            ArrivalTimeRepository arrivalTimeRepository,
            AmenityRepository amenityRepository
    ) {
        this.hotelRepository = hotelRepository;
        this.addressRepository = addressRepository;
        this.contactRepository = contactRepository;
        this.arrivalTimeRepository = arrivalTimeRepository;
        this.amenityRepository = amenityRepository;
    }

    public List<HotelShortResponse> getAllHotels() {
        return hotelRepository.findAll()
                .stream()
                .map(hotel -> new HotelShortResponse(
                        hotel.getId(),
                        hotel.getName(),
                        hotel.getDescription(),
                        formatAddress(hotel),
                        hotel.getContacts() != null
                                ? hotel.getContacts().getPhone()
                                : null
                ))
                .toList();
    }

    private String formatAddress(Hotel hotel) {
        Address address = hotel.getAddress();
        if (address == null) {
            return null;
        }
        return address.getHouseNumber() + " "
                + address.getStreet() + ", "
                + address.getCity() + ", "
                + address.getPostCode() + ", "
                + address.getCountry();
    }

    public HotelShortResponse createHotel(HotelCreateRequest request) {
        Address address = new Address();
        address.setHouseNumber(request.getAddress().getHouseNumber());
        address.setStreet(request.getAddress().getStreet());
        address.setCity(request.getAddress().getCity());
        address.setCountry(request.getAddress().getCountry());
        address.setPostCode(request.getAddress().getPostCode());

        Address savedAddress = addressRepository.save(address);

        Contact contact = new Contact();
        contact.setPhone(request.getContacts().getPhone());
        contact.setEmail(request.getContacts().getEmail());

        Contact savedContact = contactRepository.save(contact);
        ArrivalTime arrivalTime = null;

        if (request.getArrivalTime() != null) {
            arrivalTime = new ArrivalTime();
            arrivalTime.setCheckIn(request.getArrivalTime().getCheckIn());
            arrivalTime.setCheckOut(request.getArrivalTime().getCheckOut());

            arrivalTime = arrivalTimeRepository.save(arrivalTime);
        }

        Hotel hotel = new Hotel();
        hotel.setName(request.getName());
        hotel.setDescription(request.getDescription());
        hotel.setBrand(request.getBrand());
        hotel.setAddress(savedAddress);
        hotel.setContacts(savedContact);
        hotel.setArrivalTime(arrivalTime);

        Hotel savedHotel = hotelRepository.save(hotel);

        return new HotelShortResponse(
                savedHotel.getId(),
                savedHotel.getName(),
                savedHotel.getDescription(),
                formatAddress(savedHotel),
                savedHotel.getContacts() != null
                        ? savedHotel.getContacts().getPhone()
                        : null
        );
    }

    public HotelResponse getHotelById(Long id) {

        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Hotel not found with id: " + id
                        ));

        List<String> amenities = hotel.getAmenities()
                .stream()
                .map(Amenity::getName)
                .toList();

        Address address = hotel.getAddress();
        Contact contact = hotel.getContacts();
        ArrivalTime arrivalTime = hotel.getArrivalTime();

        AddressResponse addressResponse = address == null
                ? null
                : new AddressResponse(
                address.getHouseNumber(),
                address.getStreet(),
                address.getCity(),
                address.getCountry(),
                address.getPostCode()
        );

        ContactResponse contactResponse = contact == null
                ? null
                : new ContactResponse(
                contact.getPhone(),
                contact.getEmail()
        );

        ArrivalTimeResponse arrivalTimeResponse = arrivalTime == null
                ? null
                : new ArrivalTimeResponse(
                arrivalTime.getCheckIn(),
                arrivalTime.getCheckOut()
        );

        return new HotelResponse(
                hotel.getId(),
                hotel.getName(),
                hotel.getDescription(),
                hotel.getBrand(),
                addressResponse,
                contactResponse,
                arrivalTimeResponse,
                amenities
        );
    }

    public List<HotelShortResponse> search(
            String name,
            String brand,
            String city,
            String country,
            String amenities
    ) {
        return hotelRepository.findAll()
                .stream()
                .filter(hotel -> name == null ||
                        hotel.getName().toLowerCase().contains(name.toLowerCase()))
                .filter(hotel -> brand == null ||
                        hotel.getBrand().equalsIgnoreCase(brand))
                .filter(hotel -> city == null ||
                        hotel.getAddress() != null &&
                                hotel.getAddress().getCity().equalsIgnoreCase(city))
                .filter(hotel -> country == null ||
                        hotel.getAddress() != null &&
                                hotel.getAddress().getCountry().equalsIgnoreCase(country))
                .filter(hotel -> amenities == null ||
                        hotel.getAmenities().stream()
                                .anyMatch(amenity ->
                                        amenity.getName().equalsIgnoreCase(amenities)))
                .map(hotel -> new HotelShortResponse(
                        hotel.getId(),
                        hotel.getName(),
                        hotel.getDescription(),
                        formatAddress(hotel),
                        hotel.getContacts() != null
                                ? hotel.getContacts().getPhone()
                                : null
                ))
                .toList();
    }

    public HotelResponse addAmenities(Long hotelId, List<String> amenityNames) {

        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Hotel not found with id: " + hotelId
                        ));

        Set<Amenity> amenities = hotel.getAmenities();

        for (String amenityName : amenityNames) {
            Amenity amenity = amenityRepository
                    .findByNameIgnoreCase(amenityName)
                    .orElseGet(() -> {
                        Amenity newAmenity = new Amenity();
                        newAmenity.setName(amenityName);
                        return amenityRepository.save(newAmenity);
                    });

            amenities.add(amenity);
        }

        hotel.setAmenities(amenities);
        hotelRepository.save(hotel);

        return getHotelById(hotelId);
    }

    public Map<String, Long> getHistogram(String param) {
        List<Hotel> hotels = hotelRepository.findAll();
        Map<String, Long> result = new HashMap<>();

        for (Hotel hotel : hotels) {
            if (param.equalsIgnoreCase("brand")) {
                if (hotel.getBrand() != null) {
                    result.merge(
                            hotel.getBrand(),
                            1L,
                            Long::sum
                    );
                }

            } else if (param.equalsIgnoreCase("city")) {
                if (hotel.getAddress() != null &&
                        hotel.getAddress().getCity() != null) {
                    result.merge(
                            hotel.getAddress().getCity(),
                            1L,
                            Long::sum
                    );
                }

            } else if (param.equalsIgnoreCase("country")) {
                if (hotel.getAddress() != null &&
                        hotel.getAddress().getCountry() != null) {
                    result.merge(
                            hotel.getAddress().getCountry(),
                            1L,
                            Long::sum
                    );
                }

            } else if (param.equalsIgnoreCase("amenities")) {
                for (Amenity amenity : hotel.getAmenities()) {
                    result.merge(
                            amenity.getName(),
                            1L,
                            Long::sum
                    );
                }
            } else {
                throw new IllegalArgumentException(
                        "Unsupported histogram parameter: " + param
                );
            }
        }

        return result;
    }
}