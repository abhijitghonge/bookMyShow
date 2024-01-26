package com.ag.bookMyShow.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.util.Objects;

@Getter
@Setter
@Entity
public class SeatType extends BaseModel{
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        SeatType seatType = (SeatType) o;
        return id != null && Objects.equals(id, seatType.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
