package main.scala.com.ih.entities

import java.time.LocalDate

class Customer(f: String, l: String, e: String, dob: LocalDate) {
  val first: String = f
  val last: String = l
  val email: String = e
  val dateOdBirth: LocalDate = dob

  override def toString: String = s"$first,$last"


}
