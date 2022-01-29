package cinema.controller;

import cinema.model.Error;
import cinema.model.Seat;
import cinema.service.CinemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class CinemaController {

    @Autowired
    private CinemaService cinemaService;

    @GetMapping("/seats")
    public ResponseEntity getSeats() {
        Map<String, Object> res = new LinkedHashMap<>();
            res.put("total_rows", cinemaService.getTotal_rows());
            res.put("total_columns", cinemaService.getTotal_columns());
            res.put("available_seats", cinemaService.getAvailable_seats());
        return ResponseEntity.ok().body(res);
    }

    @PostMapping("/purchase")
    public ResponseEntity booked(@RequestBody Seat seat) {
        if (seat.getRow() > cinemaService.getTotal_rows() || seat.getRow() < 0
                || seat.getColumn()< 0 || seat.getColumn() > cinemaService.getTotal_columns() )
            return ResponseEntity.badRequest()
                    .body(new Error("The number of a row or a column is out of bounds!"));
        seat = cinemaService.availableSeat(seat);
        if (seat == null)
            return ResponseEntity.badRequest()
                    .body(new Error("The ticket has been already purchased!"));

        Map<String, Object> res = new LinkedHashMap<>();
            res.put("token", seat.getUuid());
            res.put("ticket", seat);
        return ResponseEntity.ok().body(res);
    }

    @PostMapping("/return")
    public ResponseEntity returnedTicket(@RequestBody Map<String, String> req) {
        Seat seat = cinemaService.availableSeat(req.get("token"));
        if (seat == null)
            return ResponseEntity.badRequest()
                    .body(new Error("Wrong token!"));

        Map<String, Object> res = new HashMap<>();
            res.put("returned_ticket", seat);
        return ResponseEntity.ok().body(res);
    }

    @PostMapping("/stats")
    public ResponseEntity stats(@RequestParam(required = false) String password) {
        if (password == null || !password.equals("super_secret"))
            return new ResponseEntity(new Error("The password is wrong!"), HttpStatus.UNAUTHORIZED);

        Map<String, Integer> res = new LinkedHashMap<>();
            res.put("current_income", cinemaService.sum());
            res.put("number_of_available_seats", cinemaService.getAvailable_seats().size());
            res.put("number_of_purchased_tickets", cinemaService.getPurchased_tickets());
        return ResponseEntity.ok().body(res);
    }
}
