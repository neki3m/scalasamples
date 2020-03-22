package main.scala.com.ih.entities

import java.util.UUID

abstract class Account {
  val id: UUID = UUID.randomUUID()
  val customer: Customer
  val product: Product
  def getBalance: Dollars

}

class DepositsAccount(val customer: Customer,
                      val product: Deposits,
                      private var balance: Dollars) extends Account {

  def deposit(dollars: Dollars): Unit = {
    require(dollars.amount > 0, "amount deposited should be greater than zero.")
    println(s"Depositing ${dollars.amount} to $customer account")
    balance += dollars
  }

  def withdraw(dollars: Dollars): Unit = {
    require(dollars > Dollars(0) && balance > dollars,
      "amount should be greater than zero and requested amount should be less than or equal to balance.")
    println(s"Withdrawing ${dollars.amount} to $customer account")
    balance -= dollars
  }

  override def getBalance: Dollars = balance

  override def toString = s"$customer with $product has remaining balance of $balance"
}

class LendingAccount(val customer: Customer,
                     val product: Lending,
                     private var balance: Dollars) extends Account {

  def payBill(dollars: Dollars): Unit = {
    require(dollars > Dollars(0), "The payment must be made for amount greater than zero.")
    println(s"Paying bill of ${dollars.amount} against $customer account")
    balance += dollars
  }

  def withdraw(dollars: Dollars): Unit = {
    require(dollars > Dollars(0), "The withdrawal amount must be greater than zero.")
    println(s"debiting ${dollars.amount} from $customer account")
    balance -= dollars
  }

  override def getBalance: Dollars = balance

  override def toString = s"$customer with $product has remaining balance of $balance"
}
