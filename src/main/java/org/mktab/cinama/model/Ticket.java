package org.mktab.cinama.model;


import java.sql.Date;

public class Ticket {

    private int id;
    private String name;
    private int price;
    private int count;
    private Date date;
    private boolean approved;
    private int cinemaId;

    public Ticket(int id, String name, int price, int count, Date date, boolean approved, int cinemaId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.count = count;
        this.date = date;
        this.approved = approved;
        this.cinemaId = cinemaId;
    }

    public static Builder builder(){
        return new Builder();
    }

    public int getId() {
        return id;
    }

    public Ticket setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Ticket setName(String name) {
        this.name = name;
        return this;
    }

    public int getPrice() {
        return price;
    }

    public Ticket setPrice(int price) {
        this.price = price;
        return this;
    }

    public int getCount() {
        return count;
    }

    public Ticket setCount(int count) {
        this.count = count;
        return this;
    }

    public Date getDate() {
        return date;
    }

    public Ticket setDate(Date date) {
        this.date = date;
        return this;
    }

    public boolean isApproved() {
        return approved;
    }

    public Ticket setApproved(boolean approved) {
        this.approved = approved;
        return this;
    }

    public int getCinemaId() {
        return cinemaId;
    }

    public Ticket setCinemaId(int cinemaId) {
        this.cinemaId = cinemaId;
        return this;
    }

    public static class Builder{

        private int id;
        private String name;
        private int price;
        private int count;
        private Date date;
        private boolean approved;
        private int cinemaId;

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder price(int price) {
            this.price = price;
            return this;
        }

        public Builder count(int count) {
            this.count = count;
            return this;
        }

        public Builder date(Date date) {
            this.date = date;
            return this;
        }

        public Builder approved(boolean approved) {
            this.approved = approved;
            return this;
        }

        public Builder cinemaId(int cinemaId) {
            this.cinemaId = cinemaId;
            return this;
        }

        public Ticket build(){
            return new Ticket(id,name,price,count,date,approved,cinemaId);
        }
    }
    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", count=" + count +
                ", date=" + date +
                ", approved=" + approved +
                ", cinemaId=" + cinemaId +
                '}';
    }
}
