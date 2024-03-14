package controllers

import javax.inject._
import play.api.mvc._

@Singleton
class CurrencyController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.currency("Enter the amount and select the currencies"))
  }

  def convert() = Action { implicit request: Request[AnyContent] =>
    val amount = request.getQueryString("amount").map(_.toDouble).getOrElse(0.0)
    val from = request.getQueryString("from").getOrElse("USD")
    val to = request.getQueryString("to").getOrElse("EUR")

    // For simplicity, let's assume some static conversion rates
    val rate = (from, to) match {
      case ("USD", "EUR") => 0.85
      case ("EUR", "USD") => 1.18
      case ("GBP", "USD") => 1.39
      case ("USD", "GBP") => 0.72
      case ("TRY", "USD") => 0.12
      case ("USD", "TRY") => 8.33
      case ("EUR", "GBP") => 1.16
      case ("GBP", "EUR") => 0.86
      case ("EUR", "TRY") => 10.00
      case ("TRY", "EUR") => 0.10
      case ("GBP", "TRY") => 12.00
      case ("TRY", "GBP") => 0.08
      // Add more cases as needed
      case _ => 1.0
    }

    val convertedAmount = amount * rate

    Ok(views.html.currency(s"$amount $from = $convertedAmount $to"))
  }
}