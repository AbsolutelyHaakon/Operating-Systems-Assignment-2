package org.example;


public class MovieTicketServer {
  private String movieName;
  private int availableSeats;


  public MovieTicketServer(String movieName,int availableSeats) {
    this.movieName = movieName;
    this.availableSeats = availableSeats;
  }

  public synchronized void bookTicket(String customerName, int numberOfSeats) {
    System.out.println("Hi " + customerName + " Available seats: " + availableSeats + ".");
    if ((availableSeats - numberOfSeats) < 0) {
      System.out.println("Not enough seats remaining. Remaining seats: " + availableSeats);
    } else {
      System.out.println(numberOfSeats + " seats booked for the " + movieName + " movie, enjoy!");
      availableSeats = availableSeats -numberOfSeats;
    }
  }
}
