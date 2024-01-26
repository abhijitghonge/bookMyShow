package com.ag.bookMyShow.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BookinRequestDTO {

    private List<Long> showSeatIds;

    private Long showId;

    private Long userId;
}
