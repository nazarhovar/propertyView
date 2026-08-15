package com.example.propertyView.controller;

import com.example.propertyView.dto.HotelCreateRequest;
import com.example.propertyView.dto.HotelResponse;
import com.example.propertyView.dto.HotelShortResponse;
import com.example.propertyView.service.HotelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "Hotels", description = "Operations for managing hotels")
@RestController
@RequestMapping("/property-view")
public class HotelController {

    private final HotelService hotelService;
    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @Operation(
            summary = "Get all hotels",
            description = "Returns a list of hotels with short information"
    )
    @GetMapping("/hotels")
    public List<HotelShortResponse> getAllHotels() {
        return hotelService.getAllHotels();
    }

    @Operation(
            summary = "Get hotel by ID",
            description = "Returns detailed information about a specific hotel"
    )
    @GetMapping("/hotels/{id}")
    public HotelResponse getHotelById(@PathVariable Long id) {
        return hotelService.getHotelById(id);
    }

    @Operation(
            summary = "Search hotels",
            description = "Search hotels by name, brand, city, country or amenities"
    )
    @GetMapping("/search")
    public List<HotelShortResponse> search(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String amenities
    ) {
        return hotelService.search(
                name,
                brand,
                city,
                country,
                amenities
        );
    }

    @Operation(
            summary = "Get hotel histogram",
            description = "Returns the number of hotels grouped by brand, city, country or amenities"
    )
    @GetMapping("/histogram/{param}")
    public Map<String, Long> getHistogram(
            @PathVariable String param
    ) {
        return hotelService.getHistogram(param);
    }

    @Operation(
            summary = "Create hotel",
            description = "Creates a new hotel"
    )
    @PostMapping("/hotels")
    public ResponseEntity<HotelShortResponse> createHotel(
            @Valid @RequestBody HotelCreateRequest request
    ) {
        HotelShortResponse hotel = hotelService.createHotel(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(hotel);
    }

    @Operation(
            summary = "Add amenities",
            description = "Adds amenities to an existing hotel"
    )
    @PostMapping("/hotels/{id}/amenities")
    public HotelResponse addAmenities(
            @PathVariable Long id,
            @RequestBody List<String> amenities
    ) {
        return hotelService.addAmenities(id, amenities);
    }
}