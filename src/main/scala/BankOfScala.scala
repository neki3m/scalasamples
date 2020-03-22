import java.time.LocalDate

import main.scala.com.ih.entities._

object BankOfScala {
  def main(args: Array[String]): Unit = {

    println("Opening Bank")

    val bank = new Bank(name = "Bank of Scala", country = "New Zealand", city = "Auckland", email = Email("bank", "scala.com"))
    val customerIds = getCustomers map (c => bank.createNewCustomer(c._1, c._2, c._3, c._4))
    val depositProductIds = getDepositProducts map (p => bank.addNewDepositProduct(p._1, p._2, p._3))
    val lendingProductIds = getLendingProducts map (l => bank.addNewLendingProduct(l._2, l._3, l._4))

    /* logging */
    println(s"Bank: $bank")
    println(s"CustomerIds: $customerIds")
    println(s"Deposits Products Ids: $depositProductIds")
    println(s"LendingProductIds: $lendingProductIds")


    /*
    Bank clerk opening the account.
    There is no money deposited in the account yet, so the accounts are not active
    */
    val depositAccounts = for {
      c <- customerIds
      p <- depositProductIds
    } yield bank.openDepositAccount(c, p, _: Dollars)

    /* Depositing money into the accounts */
    val random = new scala.util.Random()
    val depositAccountIds = depositAccounts.map(account => account(Dollars(10000 + random.nextInt(10000))))


    /* logging */
    println(s"Deposits Accounts: $depositAccounts")
    println(s"Deposits Account Ids: $depositAccountIds")

    /*
     Open credit card accounts.
     The bank process has not finished the credit check, so, balance will be known later
    */
    val lendingAccounts = for {
      c <- customerIds
      p <- lendingProductIds
    } yield bank.openLendingAccount(c, p, _: Dollars)
    val lendingAccountIds = lendingAccounts.map(account => account(Dollars(random.nextInt(500))))

    /* logging */
    println(s"Lending Accounts: $lendingAccounts")
    println(s"Lending Account Ids: $lendingAccountIds")
    println(s"Bank: $bank")

    /*
      Performing Deposit Accounts transactions
     */
    val randomAmount = new scala.util.Random(100)
    depositAccountIds.foreach(bank deposit(_, Dollars(1 + randomAmount.nextInt(100))))
    depositAccountIds.foreach(bank withdraw(_, Dollars(1 + randomAmount.nextInt(50))))

    /*
      Performing Lending Accounts transactions
    */
    lendingAccountIds.foreach(bank useCreditCard (_, Dollars(1 + randomAmount.nextInt(500))))
    lendingAccountIds.foreach(bank payCreditCardBill (_, Dollars(1 + randomAmount.nextInt(100))))
  }

  /* ------------------- Data ------------------- */
  def getCustomers: Seq[(String, String, String, String)] = {
    Seq(
      ("Bob", "Martin", "bob@martin.com", "1976/11/25"),
      ("Amy", "Jones", "amy.jones@google.com", "1983/4/12"),
      ("Taylor", "Jackson", "taylor33@jackson.com", "1985/4/5")
    )
  }

  def getDepositProducts: Seq[(String, Int, Double)] = {
    Seq(
      ("CoreChecking", 1000, 0.025),
      ("StudentCheckings", 0, 0.010),
      ("RewardsSavings", 10000, 0.10),
    )
  }

  def getLendingProducts: Seq[(String, Double, Double, Double)] = {
    Seq(("CreditCard", 99.00, 14.23, 20.00))
  }
}
