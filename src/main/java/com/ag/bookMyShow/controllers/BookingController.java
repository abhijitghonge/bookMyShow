package com.ag.bookMyShow.controllers;

import com.ag.bookMyShow.dtos.BookinRequestDTO;
import com.ag.bookMyShow.dtos.BookingResponseDTO;
import com.ag.bookMyShow.enums.ResponseStatus;
import com.ag.bookMyShow.models.Booking;
import com.ag.bookMyShow.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class BookingController {

    private BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService){
        this.bookingService = bookingService;
    }

    public BookingResponseDTO bookTickets(BookinRequestDTO requestDTO){

        try {
            Booking booking = bookingService.bookTickets(
                    requestDTO.getShowSeatIds(),
                    requestDTO.getShowId(),
                    requestDTO.getUserId()
            );
            BookingResponseDTO responseDTO = BookingResponseDTO.builder().booking(booking)
                    .responseStatus(ResponseStatus.SUCCESS)
                    .build();
//            response.setResponseStatus(ResponseStatus.SUCCESS).setBookingId()
            return responseDTO;
        } catch (Exception ex){
            BookingResponseDTO responseDTO = BookingResponseDTO.builder()
                    .responseStatus(ResponseStatus.FAILURE)
                    .failureMessage(ex.getMessage())
                    .build();
            return responseDTO;

        }
    }
}
