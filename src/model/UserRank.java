package model;

public class UserRank {
   private double avgTime;
   private String user;

    public UserRank() {
    }
    public UserRank(double avgTime, String user) {
        this.avgTime = avgTime;
        this.user = user;
    }
    public double getAvgTime() {
        return avgTime;
    }
    public void setAvgTime(double avgTime) {
        this.avgTime = avgTime;
    }
    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
    }
    @Override
    public String toString() {
        return "UserRank{" + "avgTime=" + avgTime + ", user=" + user + '}';
    }
}