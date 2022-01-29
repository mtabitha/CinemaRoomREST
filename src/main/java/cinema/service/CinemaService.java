package cinema.service;

import cinema.dao.CinemaDao;
import cinema.model.Seat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CinemaService {

    @Autowired
    private CinemaDao cinemaDao;

    public Integer getTotal_rows() {
        return cinemaDao.getTotal_rows();
    }

    public Integer getTotal_columns() {
        return cinemaDao.getTotal_columns();
    }

    public Seat availableSeat(Seat seat) {
        Seat seatFromDb = cinemaDao.getSeat(seat);
        if (!seatFromDb.getUuid().isEmpty()) {
            return null;
        }
        seatFromDb.setUuid(UUID.randomUUID().toString());
        return seatFromDb;
    }

    public Seat availableSeat(String token) {
        Seat seatFromDb = cinemaDao.getSeat(token);
        if (seatFromDb == null) {
            return null;
        }
        seatFromDb.setUuid("");
        return seatFromDb;
    }

    public CinemaService() {
    }

    public CinemaDao getCinemaDao() {
        return cinemaDao;
    }

    public void setCinemaDao(CinemaDao cinemaDao) {
        this.cinemaDao = cinemaDao;
    }

    public List<Seat> getAvailable_seats() {
        return cinemaDao.getAvailable_seats().stream().filter(s -> s.getUuid().isEmpty()).collect(Collectors.toList());
    }

    public Integer sum() {
        return cinemaDao.getAvailable_seats().stream().filter(s -> !s.getUuid().isEmpty()).mapToInt(Seat::getPrice).sum();
    }

    public Integer getPurchased_tickets() {
        return getTotal_columns() * getTotal_rows() - getAvailable_seats().size();
    }
}
