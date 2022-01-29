package cinema.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Seat {

    private Integer row;
    private Integer column;
    private Integer price;

    @JsonIgnore
    private String uuid;

    public Seat() {
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Seat(Integer row, Integer column, Integer price) {
        this.row = row;
        this.column = column;
        this.price = price;
        this.uuid = "";
    }

    public Integer getRow() {
        return row;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

    public Integer getColumn() {
        return column;
    }

    public void setColumn(Integer column) {
        this.column = column;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Seat seat = (Seat) o;

        if (row != null ? !row.equals(seat.row) : seat.row != null) return false;
        return column != null ? column.equals(seat.column) : seat.column == null;
    }

    @Override
    public int hashCode() {
        int result = row != null ? row.hashCode() : 0;
        result = 31 * result + (column != null ? column.hashCode() : 0);
        return result;
    }
}
