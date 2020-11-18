package uk.gov.hmrc.trelloradar.connectors

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.http.Status
import play.api.test.FakeRequest
import play.api.test.Helpers._
import play.api.{Configuration, Environment}
import uk.gov.hmrc.play.bootstrap.config.ServicesConfig
import uk.gov.hmrc.play.bootstrap.tools.Stubs.stubMessagesControllerComponents
import uk.gov.hmrc.trelloradar.config.AppConfig
import uk.gov.hmrc.trelloradar.views.html.HelloWorldPage



//class TrelloConnectorSpec extends AnyWordSpec with Matchers with GuiceOneAppPerSuite {
//
//  "TrelloConnector" should {
//    "make a call to a board and retrieve cards" in {
//      val tc = new TrelloConnector(n)
//    }
//  }
//
//
//}



