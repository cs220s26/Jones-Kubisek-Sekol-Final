package edu.moravian;


public class Player {
    private String name;
    private int score;
    private boolean hasAnswered;

    public Player(String name){
        this.name = name;
        this.score = 0;
        this.hasAnswered = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void addPoints(int points){
        score += points;
    }

    public void subtractPoints(int points){
        score -= points;
    }

    public boolean hasAnswered(){
        return hasAnswered;
    }

    public void setHasAnswered(boolean hasAnswered){
        this.hasAnswered = hasAnswered;
    }

    @Override
    public String toString() {
        return getName() + ": " + getScore() + " points";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Player other)) {
            return false;
        }
        return this.name.equalsIgnoreCase(other.name);
    }

    @Override
    public int hashCode() {
        return name.toLowerCase().hashCode();
    }

}
