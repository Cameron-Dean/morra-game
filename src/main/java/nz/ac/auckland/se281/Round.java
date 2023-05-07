package nz.ac.auckland.se281;

public class Round {
  
  private int fingers;
  private int sum;
  
  public Round(int fingers, int sum) {
    this.fingers = fingers;
    this.sum = sum;
  }
  
  // getter methods
  public int getFingers() {
    return this.fingers;
  }
  
  public int getSum() {
    return this.sum;
  }
  
  // setter methods
  public void setFingers(int fingers) {
    this.fingers = fingers;
  }
  
  public void setSum(int sum) {
    this.sum = sum;
  }
}
