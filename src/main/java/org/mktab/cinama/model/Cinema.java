package org.mktab.cinama.model;

public class Cinema {

    private int id;
    private String name;
    private boolean approved;
    private int userId;

    public Cinema(int id, String name, boolean approved, int userId) {
        this.id = id;
        this.name = name;
        this.approved = approved;
        this.userId = userId;
    }

    public static Builder builder(){
        return new Builder();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public static class Builder{

        private int id;
        private String name;
        private boolean approved;
        private int userId;

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder approved(boolean approved) {
            this.approved = approved;
            return this;
        }

        public Builder userId(int userId) {
            this.userId = userId;
            return this;
        }

        public Cinema build(){
            return new Cinema(id,name,approved,userId);
        }
    }
    @Override
    public String toString() {
        return "Cinema{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", approved=" + approved +
                '}';
    }

}
