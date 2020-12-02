/*
 * Copyright 2020 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.trelloradar.controllers

import org.mockito.MockitoSugar
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.http.Status
import play.api.libs.ws.WSClient
import play.api.test.FakeRequest
import play.api.test.Helpers._
import play.api.{Configuration, Environment}
import uk.gov.hmrc.http.HttpClient
import uk.gov.hmrc.play.bootstrap.config.ServicesConfig
import uk.gov.hmrc.play.bootstrap.tools.Stubs.stubMessagesControllerComponents
import uk.gov.hmrc.trelloradar.config.AppConfig
import uk.gov.hmrc.trelloradar.connectors.TrelloConnector
import uk.gov.hmrc.trelloradar.views.html.RadarPage

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class HelloWorldControllerSpec extends AnyWordSpec with Matchers with GuiceOneAppPerSuite with MockitoSugar {
  private val fakeRequest = FakeRequest("GET", "/")

  private val env           = Environment.simple()
  private val configuration = Configuration.load(env)

  private val serviceConfig = new ServicesConfig(configuration)
  private val appConfig     = new AppConfig(configuration, serviceConfig)

  val radarPage: RadarPage = app.injector.instanceOf[RadarPage]


  private[this] lazy val httpClient = app.injector.instanceOf[HttpClient]

  val trelloConnector: TrelloConnector = mock[TrelloConnector]

  private val controller = new HelloWorldController(appConfig, stubMessagesControllerComponents(), radarPage, trelloConnector)

  when(trelloConnector.getCardsForBoard()).thenReturn(Future(Some("[]")))
  when(trelloConnector.getBoard()).thenReturn(Future(Some(
    """
      |{
      |    "id": "123456",
      |    "name": "Some board",
      |    "desc": "Some description",
      |    "url": "http://plop.com"
      |}
      |    """.stripMargin)))

  "GET /" should {
    "return 200" in {
      val result = controller.radar(fakeRequest)
      status(result) shouldBe Status.OK
    }

    "return HTML" in {
      val result = controller.radar(fakeRequest)
      contentType(result) shouldBe Some("text/html")
      charset(result)     shouldBe Some("utf-8")
    }
  }
}
