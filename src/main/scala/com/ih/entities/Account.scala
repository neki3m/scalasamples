package main.scala.com.ih.entities

import java.time.temporal.TemporalAmount

class Account(c: Customer, p: Product, b: Int) {
  val customer: Customer = c
  val product: Product = p
  private var balance: Int = b

  def deposit(amount: Int): Unit = {
    print(s"Depositing $amount to $customer account")
    balance += amount
  }

  def withDraw(amount: Int): Unit = {
    println(s"Withdrawing $amount to $customer account")
    balance -= amount
  }

  override def toString = s"$customer with $product has remaining balance of $balance"
}
