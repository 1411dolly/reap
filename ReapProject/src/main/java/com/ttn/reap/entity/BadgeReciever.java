package com.ttn.reap.entity;

import javax.persistence.*;

@Entity
public class BadgeReciever {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @OneToOne
    User userId;
    int goldRecieved;
    int silverRecieved;
    int bronzeRecieved;
    int totalPoints;

    @Override
    public String toString() {
        return "BadgeReciever{" +
                "id=" + id +
                ", userId=" + userId +
                ", goldRecieved=" + goldRecieved +
                ", silverRecieved=" + silverRecieved +
                ", bronzeRecieved=" + bronzeRecieved +
                ", totalPoints=" + totalPoints +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public int getGoldCount() {
        return goldRecieved;
    }

    public void setGoldCount(int goldRecieved) {
        this.goldRecieved = goldRecieved;
    }

    public int getSilverCount() {
        return silverRecieved;
    }

    public void setSilverCount(int silverRecieved) {
        this.silverRecieved = silverRecieved;
    }

    public int getBronzeCount() {
        return bronzeRecieved;
    }

    public void setBronzeCount(int bronzeRecieved) {
        this.bronzeRecieved = bronzeRecieved;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }
}
