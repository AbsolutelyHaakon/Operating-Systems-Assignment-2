package org.example;

public class MovieTicketClient extends Thread {
  private MovieTicketServer movieTicketServer;
  private String customerName;
  private int numberOfTickets;

  public MovieTicketClient(MovieTicketServer movieTicketServer, String customerName, int numberOfTickets) {
    this.movieTicketServer = movieTicketServer;
    this.customerName = customerName;
    this.numberOfTickets = numberOfTickets;
  }

  public void run() {
    movieTicketServer.bookTicket(customerName,numberOfTickets);
  }
}
