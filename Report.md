# Report - Results of synchronization

In this short report we detail our observations on a movie ticketing system with and without synchronization.

## Setup
### The Movie Server:
The application consists of two classes: A client and  a "server". The server hosts a single movie screening and <br>
the amount of available tickets for that movie. Here is the constructor for the class:
```java
  public MovieTicketServer(String movieName,int availableSeats) {
    this.movieName = movieName;
    this.availableSeats = availableSeats;
  }
```
Clients can use the **bookTicket()** command to book their tickets for that specific movie. <br>
The function check whether there are enough available seats left for a user to purchase and provides the correct response <br>
<br>Here is the bookTicket() function:
```java
public void bookTicket(String customerName, int numberOfSeats) {
    System.out.println("Hi " + customerName + " Available seats: " + availableSeats + ".");
    if ((availableSeats - numberOfSeats) < 0) {
      System.out.println("Not enough seats remaining. Remaining seats: " + availableSeats);
    } else {
      System.out.println(numberOfSeats + " seats booked for the " + movieName + " movie, enjoy!");
      availableSeats = availableSeats -numberOfSeats;
    }
  }
```
### The Client:
The client requires three parameters: The name of the customer, the amount of tickets they want to buy and the 
movieTicketServer (The movie screening) that the customer wants to see.

<br> Here is the constructor for the class:

```java
public MovieTicketClient(MovieTicketServer movieTicketServer, String customerName, int numberOfTickets) {
    this.movieTicketServer = movieTicketServer;
    this.customerName = customerName;
    this.numberOfTickets = numberOfTickets;
  }
```
To book tickets, a run command is performed:
```java
public void run() {
movieTicketServer.bookTicket(customerName,numberOfTickets);
}
}
```
### Testing
To test the performance of the application we set up four threads that all want to book tickets for the same movie. <br>
The customers in this test want to buy an insufficient amount of tickets, and it is up to the system to ensure that one or 
more customers are denied the possibility to purchase any tickets that are duplicate or does not exist.

To achieve this it sets up a movieTicketServer with 10 total tickets:
```java
  public static void main(String[] args) {
    MovieTicketServer movieTicketServer = new MovieTicketServer(
    "Troll",
    10
    );
```    

Then it sets up four threads:
```java
    Thread t1 = new MovieTicketClient(movieTicketServer, "Xiangming", 3);
    Thread t2 = new MovieTicketClient(movieTicketServer, "Ilaria", 2);
    Thread t3 = new MovieTicketClient(movieTicketServer, "Sam", 3);
    Thread t4 = new MovieTicketClient(movieTicketServer, "Andreas", 4);
```

Finally, it starts all the threads:
```java
    t1.start();
    t2.start();
    t3.start();
    t4.start();
```

Let's have a look  at the results!

## Result Without Synchronization
Running the application without any protections put in place provides this output:

```
Hi Sam Available seats: 10.
Hi Ilaria Available seats: 10.
Hi Andreas Available seats: 10.
Hi Xiangming Available seats: 10.
2 seats booked for the Troll movie, enjoy!
3 seats booked for the Troll movie, enjoy!
4 seats booked for the Troll movie, enjoy!
3 seats booked for the Troll movie, enjoy!
```
The server attempts to handle each client simultaneously resulting in these operation being disregarded:

```java
if ((availableSeats - numberOfSeats) < 0)
```
```java
availableSeats = availableSeats -numberOfSeats;
```

The server does not have time to check on the remaining seats and remove the occupied seats before the next
customer is served resulting in 11 tickets being sold to a movie with only 10 available. This might be optimal for the 
airline industry, but it is a rare practice in cinemas. This outcome is highly undesirable.

## Results With Synchronization

To ensure the clients are handled sequentially and that the application is able to perform its actions before handling 
another operation the **synchronized** tag is added to the function:

```java
public synchronized void bookTicket(String customerName, int numberOfSeats) 
```
Running the application with the new change results in this output:
```
Hi Xiangming Available seats: 10.
3 seats booked for the Troll movie, enjoy!
Hi Sam Available seats: 7.
3 seats booked for the Troll movie, enjoy!
Hi Andreas Available seats: 4.
4 seats booked for the Troll movie, enjoy!
Hi Ilaria Available seats: 0.
Not enough seats remaining. Remaining seats: 0
```
In this instance each client is handed sequentially with a FIFO principle ensuring that the last customer, which in this 
case is Ilaria is not allowed to buy any tickets for the movie. This is a highly desired outcome.