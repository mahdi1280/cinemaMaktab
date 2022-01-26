package org.mktab.cinama.model;

public class Reserve {

    private int id;
    private int count;
    private int price;
    private int ticketId;
    private int userId;

    public Reserve(int id, int count, int price, int ticketId, int userId) {
        this.id = id;
        this.count = count;
        this.price = price;
        this.ticketId = ticketId;
        this.userId = userId;
    }

    public static Builder builder() {
        return new Builder();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Reserve{" +
                "id=" + id +
                ", count=" + count +
                ", price=" + price +
                ", ticketId=" + ticketId +
                ", userId=" + userId +
                '}';
    }

    public static class Builder {

        private int id;
        private int count;
        private int price;
        private int ticketId;
        private int userId;

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setCount(int count) {
            this.count = count;
            return this;
        }

        public Builder setPrice(int price) {
            this.price = price;
            return this;
        }

        public Builder setTicketId(int ticketId) {
            this.ticketId = ticketId;
            return this;
        }

        public Builder setUserId(int userId) {
            this.userId = userId;
            return this;
        }

        public Reserve build() {
            return new Reserve(id, count, price, ticketId, userId);
        }
    }
}
