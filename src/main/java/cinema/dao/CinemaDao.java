package cinema.dao;

import cinema.model.Seat;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CinemaDao {

    private Integer total_rows = 9;
    private Integer total_columns = 9;

    List<Seat> available_seats = new ArrayList<>();

    public CinemaDao() {
        for (int i = 1; i < total_columns + 1; i++) {
            for (int j = 1; j < total_rows + 1; j++) {
                available_seats.add(new Seat(j, i, j > 4 ? 8 : 10));
            }
        }
    }

    public Integer getTotal_rows() {
        return total_rows;
    }

    public void setTotal_rows(Integer total_rows) {
        this.total_rows = total_rows;
    }

    public Integer getTotal_columns() {
        return total_columns;
    }

    public void setTotal_columns(Integer total_columns) {
        this.total_columns = total_columns;
    }

    public List<Seat> getAvailable_seats() {
        return available_seats;
    }

    public void setAvailable_seats(List<Seat> available_seats) {
        this.available_seats = available_seats;
    }

    public Seat getSeat(Seat seat) {
        return available_seats.stream().filter(s -> s.equals(seat)).findAny().orElse(null);
    }

    public Seat getSeat(String token) {
        return available_seats.stream().filter(s -> s.getUuid().equals(token)).findAny().orElse(null);
    }
}
