package com.ag.bookMyShow.dtos;

import com.ag.bookMyShow.enums.ResponseStatus;
import com.ag.bookMyShow.models.Booking;
import lombok.Builder;

@Builder
public class BookingResponseDTO {
    private ResponseStatus responseStatus;
    // The booking is not finalised yet
    // It will be finalised once the payment is done
    private Booking booking;
    private String failureMessage;
}
