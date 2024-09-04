import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

public class RestfullBooking_Tests {

    public static Integer bookingId;
    public static BookingModel client = new BookingModel();
    private static final String name = "Daniel";
    private static final String lastname = "Torrico";
    private static final Integer price = 10;
    private static final Boolean paid = true;
    private static final String checkin = "2018-11-11";
    private static final String checkout = "2018-12-12";
    private static final String add = "None";

    //BUG se espera mensaje de error 400 Bad Request, se recibe 404
    @Test
    public void GetBookingConIdInvalido()
    {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com/booking";
        Response response = RestAssured.given().pathParam("id", "abc")
                .when().get("/{id}");
        response.then().assertThat().statusCode(400);
        response.then().log().body();
    }

    @Test
    public void GetBookingMuestraInfoCorrecta() throws JsonProcessingException {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        client.setFirstname(name);
        client.setLastname(lastname);
        client.setTotalprice(price);
        client.setDepositpaid(paid);
        BookingModel.BookingDates bookingDates = new BookingModel.BookingDates();
        bookingDates.setCheckin(checkin);
        bookingDates.setCheckout(checkout);
        client.setBookingdates(bookingDates);
        client.setAdditionalneeds(add);

        ObjectMapper mapper = new ObjectMapper();
        String booking = mapper.writeValueAsString(client);

        Response response = RestAssured
                .given().contentType(ContentType.JSON).body(booking).when()
                .post("/booking");
        response.then().log().body();
        response.then().assertThat().statusCode(200);

        bookingId = response.jsonPath().getInt("bookingid");
        Response response2 = RestAssured.given().pathParam("id", bookingId)
                .when().get("/booking/{id}");
        response2.then().assertThat().statusCode(200);
        response2.then().log().body();

        response2.then().assertThat().body("firstname", Matchers.equalTo(client.getFirstname()))
                .body("lastname", Matchers.equalTo(client.getLastname()))
                .body("totalprice", Matchers.equalTo(client.getTotalprice()))
                .body("depositpaid", Matchers.equalTo(client.getDepositpaid()))
                .body("bookingdates.checkin", Matchers.equalTo(client.getBookingdates().getCheckin()))
                .body("bookingdates.checkout", Matchers.equalTo(client.getBookingdates().getCheckout()))
                .body("additionalneeds", Matchers.equalTo(client.getAdditionalneeds()));
    }

    @Test
    public void CreateBookingAlmacenaInfoCorrecta() throws JsonProcessingException {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        client.setFirstname(name);
        client.setLastname(lastname);
        client.setTotalprice(price);
        client.setDepositpaid(paid);
        BookingModel.BookingDates bookingDates = new BookingModel.BookingDates();
        bookingDates.setCheckin(checkin);
        bookingDates.setCheckout(checkout);
        client.setBookingdates(bookingDates);
        client.setAdditionalneeds(add);

        ObjectMapper mapper = new ObjectMapper();
        String booking = mapper.writeValueAsString(client);

        Response response = RestAssured
                .given().contentType(ContentType.JSON).body(booking).when()
                .post("/booking");
        response.then().log().body();
        response.then().assertThat().statusCode(200);

        bookingId = response.jsonPath().getInt("bookingid");

        response.then().assertThat().body("booking.firstname", Matchers.equalTo(client.getFirstname()))
                .body("booking.lastname", Matchers.equalTo(client.getLastname()))
                .body("booking.totalprice", Matchers.equalTo(client.getTotalprice()))
                .body("booking.depositpaid", Matchers.equalTo(client.getDepositpaid()))
                .body("booking.bookingdates.checkin", Matchers.equalTo(client.getBookingdates().getCheckin()))
                .body("booking.bookingdates.checkout", Matchers.equalTo(client.getBookingdates().getCheckout()))
                .body("booking.additionalneeds", Matchers.equalTo(client.getAdditionalneeds()));
    }

    //BUG se espera mensaje de error 400 Bad Request, se recibe 500
    @Test
    public void CreateBookingSinFirstName() throws JsonProcessingException {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        //client.setFirstname(name);
        client.setLastname(lastname);
        client.setTotalprice(price);
        client.setDepositpaid(paid);
        BookingModel.BookingDates bookingDates = new BookingModel.BookingDates();
        bookingDates.setCheckin(checkin);
        bookingDates.setCheckout(checkout);
        client.setBookingdates(bookingDates);
        client.setAdditionalneeds(add);

        ObjectMapper mapper = new ObjectMapper();
        String booking = mapper.writeValueAsString(client);

        Response response = RestAssured
                .given().contentType(ContentType.JSON).body(booking).when()
                .post("/booking");
        response.then().log().body();
        response.then().assertThat().statusCode(400);
    }


    //BUG se espera mensaje de error 400 Bad Request, se recibe 200
    @Test
    public void CreateBookingFirstNameMalFormato() throws JsonProcessingException {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        client.setFirstname("12345");
        client.setLastname(lastname);
        client.setTotalprice(price);
        client.setDepositpaid(paid);
        BookingModel.BookingDates bookingDates = new BookingModel.BookingDates();
        bookingDates.setCheckin(checkin);
        bookingDates.setCheckout(checkout);
        client.setBookingdates(bookingDates);
        client.setAdditionalneeds(add);

        ObjectMapper mapper = new ObjectMapper();
        String booking = mapper.writeValueAsString(client);

        Response response = RestAssured
                .given().contentType(ContentType.JSON).body(booking).when()
                .post("/booking");
        response.then().log().body();
        response.then().assertThat().statusCode(400);
    }
}
