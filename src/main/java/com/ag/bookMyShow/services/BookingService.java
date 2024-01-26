package com.ag.bookMyShow.services;

import com.ag.bookMyShow.enums.BookingStatus;
import com.ag.bookMyShow.exceptions.ShowSeatNotAvailableException;
import com.ag.bookMyShow.models.*;
import com.ag.bookMyShow.repositories.BookingRepository;
import com.ag.bookMyShow.repositories.ShowRepository;
import com.ag.bookMyShow.repositories.ShowSeatRepository;
import com.ag.bookMyShow.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    private ShowSeatRepository showSeatRepository;
    private ShowRepository showRepository;
    private UserRepository userRepository;
    private BookingRepository bookingRepository;
    private PriceCalcService priceCalcService;

    @Autowired
    public BookingService(ShowSeatRepository showSeatRepository,
                          ShowRepository showRepository,
                          UserRepository userRepository,
                          BookingRepository bookingRepository,
                          PriceCalcService priceCalcService){
        this.showSeatRepository = showSeatRepository;
        this.showRepository = showRepository;
        this.userRepository = userRepository;
        this.bookingRepository = bookingRepository;
        this.priceCalcService = priceCalcService;
    }

    public Booking bookTickets(List<Long> showSeatIds, Long showId,Long userId ){

        // 1. Get a user with UserId
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isEmpty()){
            throw new RuntimeException("User not found!");
        }
        User bookedBy = userOptional.get();
        // 2. Get the corresponding show with ShowId
        Optional<Show> showOptional = showRepository.findById(showId);
        if(showOptional.isEmpty()){
            throw new RuntimeException("Show doesn't exist!");
        }
        Show savedShow = showOptional.get();

        List<ShowSeat> showSeats = showSeatRepository.findAllById(showSeatIds);

        for (ShowSeat showSeat :
                showSeats) {
            if (!showSeat.getShowSeatStatus().equals(ShowSeatStatus.AVAILABLE) ||
                    (showSeat.getShowSeatStatus().equals(ShowSeatStatus.BLOCKED)
                            && Duration.between(showSeat.getBlockedAt().toInstant(), new Date().toInstant()).toMinutes()<=15)){
                throw new ShowSeatNotAvailableException("Show Seat not available!:"+showSeat);
            }
        }

        for(ShowSeat showSeat : showSeats){
            showSeat.setShowSeatStatus(ShowSeatStatus.BLOCKED);
            showSeat.setBlockedAt(new Date());
            // 7. Save updated show seats in the database
        }

        List<ShowSeat> savedShowSeats = new ArrayList<>(showSeatRepository.saveAll(showSeats));

        // 8. Create the Booking object and add the details
        Booking booking = new Booking();
        booking.setBookedAt(new Date());
        booking.setBookingStatus(BookingStatus.PENDING);
        booking.setShow(savedShow);
        booking.setUser(bookedBy);
        booking.setShowSeats(savedShowSeats);

        booking.setAmount(priceCalcService.calculate(savedShowSeats, savedShow));
        booking.setPayments(new ArrayList<>());
        // 9. save it in the db
        // 10. return the Booking object
        return bookingRepository.save(booking);
    }
}
