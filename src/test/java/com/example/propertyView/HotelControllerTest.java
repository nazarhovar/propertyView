package com.example.propertyView;

import com.example.propertyView.entity.Address;
import com.example.propertyView.entity.Contact;
import com.example.propertyView.entity.Hotel;
import com.example.propertyView.repository.AddressRepository;
import com.example.propertyView.repository.ContactRepository;
import com.example.propertyView.repository.HotelRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class HotelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ContactRepository contactRepository;


    @Test
    void shouldGetAllHotels() throws Exception {

        mockMvc.perform(
                        get("/property-view/hotels")
                )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/json"))
                .andExpect(jsonPath("$").isArray());
    }


    @Test
    void shouldGetHotelById() throws Exception {

        Address address = new Address();
        address.setHouseNumber(10);
        address.setStreet("Test Street");
        address.setCity("Minsk");
        address.setCountry("Belarus");
        address.setPostCode("220000");

        Address savedAddress = addressRepository.save(address);

        Contact contact = new Contact();
        contact.setPhone("+375291234567");
        contact.setEmail("test@test.com");

        Contact savedContact = contactRepository.save(contact);

        Hotel hotel = new Hotel();
        hotel.setName("Test Hotel");
        hotel.setDescription("Test description");
        hotel.setBrand("Test Brand");
        hotel.setAddress(savedAddress);
        hotel.setContacts(savedContact);

        Hotel savedHotel = hotelRepository.save(hotel);

        mockMvc.perform(
                        get("/property-view/hotels/" + savedHotel.getId())
                )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/json"))
                .andExpect(jsonPath("$.id").value(savedHotel.getId()))
                .andExpect(jsonPath("$.name").value("Test Hotel"))
                .andExpect(jsonPath("$.brand").value("Test Brand"))
                .andExpect(jsonPath("$.address.houseNumber").value(10))
                .andExpect(jsonPath("$.address.street").value("Test Street"))
                .andExpect(jsonPath("$.address.city").value("Minsk"))
                .andExpect(jsonPath("$.address.country").value("Belarus"))
                .andExpect(jsonPath("$.address.postCode").value("220000"))
                .andExpect(jsonPath("$.contacts.phone").value("+375291234567"))
                .andExpect(jsonPath("$.contacts.email").value("test@test.com"));
    }


    @Test
    void shouldReturn404WhenHotelNotFound() throws Exception {

        mockMvc.perform(
                        get("/property-view/hotels/123124")
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message")
                        .value("Hotel not found with id: 123124"));
    }


    @Test
    void shouldReturn400WhenNameIsBlank() throws Exception {

        String request = """
                {
                    "name": "",
                    "brand": "Hilton",
                    "address": {
                        "houseNumber": 10,
                        "street": "Test Street",
                        "city": "Minsk",
                        "country": "Belarus",
                        "postCode": "220000"
                    },
                    "contacts": {
                        "phone": "+375291234567",
                        "email": "test@test.com"
                    }
                }
                """;

        mockMvc.perform(
                        post("/property-view/hotels")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(request)
                )
                .andExpect(status().isBadRequest());
    }


    @Test
    void shouldGetCityHistogram() throws Exception {

        mockMvc.perform(
                        get("/property-view/histogram/city")
                )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/json"))
                .andExpect(jsonPath("$").isMap());
    }

    @Test
    void shouldReturn400WhenContactsAreMissing() throws Exception {

        String request = """
            {
                "name": "Test Hotel",
                "brand": "Test Brand",
                "address": {
                    "houseNumber": 10,
                    "street": "Test Street",
                    "city": "Minsk",
                    "country": "Belarus",
                    "postCode": "220000"
                }
            }
            """;

        mockMvc.perform(
                        post("/property-view/hotels")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(request)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.contacts")
                        .value("Contacts are required"));
    }
}