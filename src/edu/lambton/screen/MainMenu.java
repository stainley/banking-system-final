package edu.lambton.screen;

public class MainMenu {
// This is the main menu where the user can get registered and  if is already registered then can sign in or can exit//

    public void createMainScreen() {

        System.out.println("""
                ********************************************************************************************************
                *                                                                                                      *
                *                              MAIN MENU                                                               *
                *                           1. Register                                                                *
                *                           2. Sign In                                                                 *
                *                           3. Exit                                                                    *
                ********************************************************************************************************
                """);

    }
// THis is the options menu where user can have multiple options to do the transactions//

    public void optionsMenu() {
        System.out.println("""
                **********************************************************************************************************  
                *                                  Options
                *                               1. My Accounts
                *                               2. Deposit Money
                *                               3. Withdraw Money
                *                               4. Interac e-transfer
                *                               5. Bill Payments
                *                               6. Transfer Funds
                *                               7. Find Us 
                *                               8. Logout
                * ******************************************************************************************************
                """);
    }
// The user gets Registered here by filling the form with the options below//

    public void Register() {
        System.out.println("""
                ******************************************************************************************************
                *                                 1. Name
                *                                 2. Card Number
                *                                 3. Phone
                *                                 4. E-MAIL
                *                                 5. Date Of Birth
                *                                 6. Password
                *                                 7. Signature
                *                                 8. Go Back
                ******************************************************************************************************                     
                                
                """);
    }
// The user needs to enter the registered name and password to signin into the Account//

    public void Signin() {
        System.out.println("""
                ***********************************************************************************************
                *                           1. Name
                *                           2. Password
                *                           3. Go Back
                ***********************************************************************************************
                                        
                """);

    }
// Myaccounts gives the options to check all your accounts and total amount the user has in the account//

    public void Myaccounts() {
        System.out.println("""
                ******************************************************************************************************
                *                                       1. Chequing
                *                                       2. Savings
                *                                       3. Total CAD
                *                                       4. Credit
                *                                       5. Go back
                ******************************************************************************************************
                                
                """);
    }
// Depositmoney option lets the user to deposit the money to one of the accounts//

    public void DepositMoney() {
        System.out.println("""
                *****************************************************************************************************
                *                                      1.Amount
                *                                      2.Deposit to chequing
                *                                      3.Deposit to Savings 
                *                                      4.Go Back                         
                *****************************************************************************************************                                      
                                
                """);
    }
// Withdraw option lets the user to withdraw the money from one of the accounts//

    public void WithdrawMoney() {
        System.out.println("""
                *****************************************************************************************************
                *                                       1.Amount
                *                                       2.Withdraw from Chequing
                *                                       3.Withdraw from Savings
                *                                       4.Go Back
                *****************************************************************************************************
                """);
    }
// here by using this option the user can send the money or can request the money //
    public void Interacetransfer() {
        System.out.println("""
                *******************************************************************************************************
                *                                         1.Send Money
                *                                         2.Request Money
                *                                         3.Stop Transaction
                *                                         4.Review History
                *                                         5.Manage Transaction
                *                                         6.Go Back
                ********************************************************************************************************
                              
                              
                """);
    }
// here the user can pay the bills //
    public void BillPayments() {
        System.out.println("""
                ********************************************************************************************************
                *                                   1.Pay a Bill
                *                                   2.From Account
                *                                   3.Next
                *                                   4.Go Back
                ********************************************************************************************************
                """);
    }
// this option allow the user to trnafer funds from one acccount to another//
    public void TransferFunds() {
    System.out.println("""
            ************************************************************************************************************
            * 1.Amount
            * 2.From Account
            * 3.To Account
            * 4.Next
            * 5.Go back
            ***********************************************************************************************************
            """);
    }

    //this option will let the user to know the location of the bank //
    public void Findus()
    {
        System.out.println("""
                *******************************************************************************************************
                * 1.Location
                * 2.Go Back
                *******************************************************************************************************
                
                
                
                
                """);
    }
}