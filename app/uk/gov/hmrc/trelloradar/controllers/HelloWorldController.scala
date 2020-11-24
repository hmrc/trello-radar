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

import javax.inject.{Inject, Singleton}
import play.api.libs.json.Json
import play.api.mvc._
import uk.gov.hmrc.play.bootstrap.frontend.controller.FrontendController
import uk.gov.hmrc.trelloradar.config.AppConfig
import uk.gov.hmrc.trelloradar.connectors.TrelloConnector
import uk.gov.hmrc.trelloradar.model.{Quadrant, Ring, TrelloCard, TrelloLabel}
import uk.gov.hmrc.trelloradar.views.html.HelloWorldPage
import uk.gov.hmrc.trelloradar.views.html.RadarPage
import scala.concurrent.{ExecutionContext, Future}
import uk.gov.hmrc.trelloradar.model.Readers._




@Singleton
class HelloWorldController @Inject()(
  appConfig: AppConfig,
  mcc: MessagesControllerComponents,
  helloWorldPage: HelloWorldPage,
  radarPage: RadarPage,
  tc: TrelloConnector)(implicit ec: ExecutionContext)
    extends FrontendController(mcc) {

  implicit val config: AppConfig = appConfig

  val helloWorld: Action[AnyContent] = Action.async { implicit request =>
    Future.successful(Ok(helloWorldPage()))
  }




  val radar: Action[AnyContent] = Action.async { implicit request =>

    for {
      cardsString <- tc.getCardsForBoard()
    } yield {
      val cards: List[TrelloCard] = Json.parse(cardsString.get).as[List[TrelloCard]]

      Ok(radarPage(
        buildQuadrant(0, name = "1: The right tools", displayName = "1: The right tools at the right times", cards),
        buildQuadrant(1, name = "2: Effective and secure", displayName = "2: Effective and secure", cards),
        buildQuadrant(2, name = "3: Learning", displayName = "3: Propagate learning and experience", cards),
        buildQuadrant(3, name = "4: Great place", displayName = "4: Great place to work", cards)
      )())
    }
  }





  // TODO - make the time periods configurable
  def buildQuadrant(id: Int, name: String, displayName: String, cards: List[TrelloCard]) = {
    Quadrant(id, name, displayName,
      filterCards(ringId = 0, quadrant = name, timeframe = "now", cards),
      filterCards(ringId = 1, quadrant = name, timeframe = "soon", cards),
      filterCards(ringId = 2, quadrant = name, timeframe = "later", cards),
      filterCards(ringId = 3, quadrant = name, timeframe = "someday", cards))
  }


  def filterCards(ringId: Int, quadrant: String, timeframe: String, cards: List[TrelloCard]): Ring = {
    val ringCards = cards.filter{ c => c.labels.exists(_.name == quadrant) && c.labels.exists(_.name == timeframe) }
    Ring(ringId, quadrant, ringCards)
  }

}
