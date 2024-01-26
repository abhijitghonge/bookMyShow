package com.ag.bookMyShow.services;

import com.ag.bookMyShow.models.Show;
import com.ag.bookMyShow.models.ShowSeat;
import com.ag.bookMyShow.models.ShowSeatType;
import com.ag.bookMyShow.repositories.ShowSeatTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PriceCalcService {

    private ShowSeatTypeRepository showSeatTypeRepository;

    @Autowired
    public PriceCalcService(ShowSeatTypeRepository showSeatTypeRepository){
        this.showSeatTypeRepository = showSeatTypeRepository;
    }

    public int calculate(List<ShowSeat> showSeats, Show show){
        List<ShowSeatType> showSeatTypes = showSeatTypeRepository.findAllByShow(show);

        int amount = 0;
        for (ShowSeat showSeat:
             showSeats) {
            for (ShowSeatType showSeatType :
                    showSeatTypes) {
                if(showSeat.getSeat().getSeatType().equals(showSeatType.getSeatType())){
                    amount+=showSeatType.getPrice();
                }
            }
        }

        return amount;


    }
}
