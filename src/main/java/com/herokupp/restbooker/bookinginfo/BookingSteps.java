package com.herokupp.restbooker.bookinginfo;

import com.herokupp.restbooker.constants.EndPoints;
import com.herokupp.restbooker.model.BookingPojo;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

import java.util.HashMap;

public class BookingSteps {
    @Step("Getting Access Token")
    public ValidatableResponse authorizeUser(String username, String password) {
        BookingPojo bookingPojo = new BookingPojo();
        bookingPojo.setUsername(username);
        bookingPojo.setPassword(password);

        return SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .body(bookingPojo)
                .when()
                .post(EndPoints.CREATE_AUTH)
                .then();
    }

    @Step("Creating new booking with firstName : {0}, lastName: {1}, totalprice: {2} depositpaid: {3}, bookingdates: {4} and additonalneeds: {5}")
    public ValidatableResponse createBooking(String firstname, String lastname, int totalprice, boolean depositpaid, HashMap<Object, Object> bookingsDatesMap, String additionalneeds) {
        BookingPojo bookingPojo = new BookingPojo();
        bookingPojo.setFirstname(firstname);
        bookingPojo.setLastname(lastname);
        bookingPojo.setTotalprice(totalprice);
        bookingPojo.setDepositpaid(depositpaid);
        bookingPojo.setBookingdates(bookingsDatesMap);
        bookingPojo.setAdditionalneeds(additionalneeds);

        return SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .body(bookingPojo)
                .when()
                .post(EndPoints.GET_BOOKING)
                .then();
    }

    @Step("Updating record with bookingID:{0}, firstName: {1}, lastName: {2}, totalprice: {3} depositpaid: {4}, bookingdates: {5} and additonalneeds: {6} ")
    public ValidatableResponse updateBooking(int bookingID, String firstname, String lastname, int totalprice, boolean depositpaid, HashMap<Object, Object> bookingsDatesMap, String additionalneeds) {
        BookingPojo bookingPojo = new BookingPojo();
        bookingPojo.setFirstname(firstname);
        bookingPojo.setLastname(lastname);
        bookingPojo.setTotalprice(totalprice);
        bookingPojo.setDepositpaid(depositpaid);
        bookingPojo.setBookingdates(bookingsDatesMap);
        bookingPojo.setAdditionalneeds(additionalneeds);

        return SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .body(bookingPojo)
                .pathParam("bookingID", bookingID)
                .auth().preemptive().basic("admin", "password123")
                .when()
                .put(EndPoints.UPDATE_BOOKING_BY_ID)
                .then();
    }

    @Step("Deleting existing booking with id: {0}")
    public ValidatableResponse deleteBooking(int bookingID) {
        return SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .pathParam("bookingID", bookingID)
                .auth().preemptive().basic("admin", "password123")
                .when()
                .delete(EndPoints.DELETE_BOOKING_BY_ID)
                .then();
    }

    @Step("Getting booking info by ID")
    public ValidatableResponse getBookingByID(int bookingId) {
        return SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .pathParam("bookingID", bookingId)
                .when()
                .get(EndPoints.GET_BOOKING_BY_ID)
                .then();
    }
}
