`# Operating-Systems-Assignment-2
Assignment 2 for the Operating Systems class. Contributing members: Di Xie &amp; Haakon Svensen Karlsen

# How to run
The application runs from the Main class. The main class does the following:

First it sets up a movieTicketServer with 10 total tickets:
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
