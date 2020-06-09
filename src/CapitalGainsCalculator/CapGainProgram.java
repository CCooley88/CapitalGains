//******************
// Christopher kulig
// 3/2020
// Data Structures
// Capital Gains - queue FIFO project
/*
* This project is designed to calculate capital gains based on trades input by the user. "Trade" objects representing purchases of an individual stock are held in a queue. As the user
* sells shares at varying prices and quantities they are deducted from the queue accordingly - capital gains are subsequently calculated based on these sales and the contents of the queue.
*
* */

package CapitalGainsCalculator;


import java.util.*;
import java.util.Scanner;

public class CapGainProgram{

    public static void main(String []args){

        String numShares = "";
        String price = "";
        capGain cg = new capGain();
        Scanner input = new Scanner (System.in);

        printMenu();

        System.out.println("Enter command: ");
        String command = input.nextLine();

        while(!command.equals("4")){
            switch(command){
                case "1": System.out.println("How many shares to purchase: ");
                    numShares = input.nextLine();
                    System.out.println("At what price: ");
                    price = input.nextLine();
                    cg.totalShares+=Integer.parseInt(numShares);
                    Trade t1 = new Trade(Integer.parseInt(numShares), Double.parseDouble(price));
                    cg.q.add(t1);
                    break;
                case "2":
                    System.out.println("How many shares to sell: ");
                    numShares = input.nextLine();
                    System.out.println("At what price: ");
                    price = input.nextLine();
                    cg.sellShares(Integer.parseInt(numShares), Double.parseDouble(price));
                    break;
                case "3": System.out.println("Capital Gains/Loss: " + cg.totCapGain);
                    break;
                case "0":  printMenu();
                    break;
            }
            System.out.println("Enter Command: ");
            command = input.nextLine();
        }
        System.out.println("Exiting program.");
    }

    public static void printMenu(){
        System.out.println("1: Buy");
        System.out.println("2: Sell");
        System.out.println("3: Print Capital Gain");
        System.out.println("4: Quit");
        System.out.println("0: Help");
    }
}

class capGain{
    int totalShares = 0;
    double totCapGain = 0;
    Queue<Trade> q = new LinkedList<>();

    int getTotalSahres(){
        return totalShares;
    }

    void setTotalShares(int totalShares){
        this.totalShares = totalShares;
    }

    double getTotCapGain(){
        return totCapGain;
    }

    void setTotCapGain(double totCapGain){
        this.totCapGain = totCapGain;
    }

    void sellShares(int toSell, double currentPrice){
        int selling = toSell;
        // If there are enough shares to sell
        if(totalShares >= selling) {
            // While there are still shares that need to be sold
            while (selling > 0) {
                // If there are less shares in this Trade object than the amount that still needs to be sold the appropriate calculation is performed and the Trade object cleared form the queue)
                if (q.peek().getShares() <= selling) {
                    totCapGain += q.peek().getShares() * (currentPrice - q.peek().getPrice());
                    totalShares -= q.peek().getShares();
                    selling -= q.remove().getShares();
                // Else the amount needed to be sold is less than the amount in the current Trade object, the number of shares left in the Trade are decremented
                } else {
                    totCapGain += selling * (currentPrice - q.peek().getPrice());
                    q.peek().setShares(q.peek().getShares() - selling);
                    totalShares-=selling;
                    selling = 0;
                    // remove shares from the queue, set sharesLeft to 0
                }
            }
        }
        // If there are not enough shares to sell
        else {
            System.out.println("Not enough shares - Total shares available: " + totalShares);
        }
    }
}

// Trade object gets added to the queue to hold information about each trade, they are removed from the quque or decremented as needed
class Trade{
    int numShares;
    double price;

    Trade(int numShares, double price){
        this.numShares = numShares;
        this.price = price;
    }

    void setShares(int numShares){
        this.numShares = numShares;
    }

    void setPrice(double price){
        this.price = price;
    }

    int getShares(){
        return numShares;
    }

    double getPrice() {
        return price;
    }
}
