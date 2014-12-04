/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.pkg2;
import java.util.*;
import java.text.*;

/**
 *
 * @author NEWColbyCheeZe
 */
public class Project2 {

    public static Scanner CONSOLE = new Scanner(System.in);
    
    private static String twoPlaces(double d) {
      DecimalFormat df = new DecimalFormat("0.00");
      return df.format(d);
   }
    
    public static void main(String[] args) {
        System.out.println("Project 2 Written by ColbyCheeZe");
        
        double loanAmount = 0, loanRate, monthlyPayment, totalPayment;
        int numYears = 0;
        
        System.out.println("Enter Loan Amount and Number of Years");        
        if( CONSOLE.hasNextDouble()){
            loanAmount = CONSOLE.nextDouble();
        } else {
            System.out.println("Invalid Loan Amount");
            System.exit(0);
        }      
        if( CONSOLE.hasNextInt()){
            numYears = CONSOLE.nextInt();
            if(numYears <= 0) System.out.println("Invalid Number of Years");
        } else {
            System.out.println("Invalid Number of Years");
            System.exit(0);
        }
        
        loanRate = loanRate(loanAmount);
        monthlyPayment = loanMonthlyPayment(loanAmount, loanRate, numYears);
        
        
        System.out.println("Loan Amount: $" + twoPlaces(loanAmount));
        System.out.println("Loan period: " + numYears + " years");
        System.out.println("Loan rate: " + loanRate * 100 + "%");
        System.out.println("Monthly payment: $" + twoPlaces(monthlyPayment));
        totalPayment = loanTotalPayment(loanAmount, monthlyPayment, loanRate, numYears);
        
        System.out.println("Total payment amount: $" + twoPlaces(totalPayment));
        
    }
    
    public static double loanRate(double loanAmount){
//        if( loanAmount >= 350000){
//            return 0.04;
//        } else if( loanAmount >= 100000){
//            return 0.035;
//        } else return 0.03;
        return 0.0999;
    }
    
    public static double loanMonthlyPayment(double loanAmount, double loanRate, int numYears){
              
        return (loanAmount * (loanRate/12)) / (1 - Math.pow(1 + (loanRate/12), -(numYears * 12)));
  
    }
    
    public static double loanTotalPayment(double loanAmount, double monthlyPayment, double loanRate, int numYears){
        
        System.out.println("Month" + padOnLeft("Balance", 15) + padOnLeft("Payment", 15) + padOnLeft("Remaining", 15));
        
        double totalPayment = monthlyPayment * numYears * 12;
        double remainingBalance = loanAmount + loanAmount * (loanRate/12);
        String strRb;
        String strMp = "" + twoPlaces(monthlyPayment);
        String strR;
        
        for( int i=1; i<=numYears*12; i++){
            strRb = "" + twoPlaces(remainingBalance);
            strR = "" + twoPlaces((remainingBalance-monthlyPayment));
            System.out.println(padOnLeft("" + i, 3) + padOnLeft(strRb, 15) + padOnLeft(strMp, 15) + padOnLeft(strR, 15));
            remainingBalance -= monthlyPayment;
            remainingBalance += remainingBalance * (loanRate/12);
        }
        
        return totalPayment;
    }
    
    public static String padOnLeft(String s, int n){
        String newS = s;
        if(s.length() >= n){
            return s;
        } else {
            for(int i=s.length(); i<=n; i++) {
                newS = " " + newS;
            }
            return newS;
        }      
    }
    
}
