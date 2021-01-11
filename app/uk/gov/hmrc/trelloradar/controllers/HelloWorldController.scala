/*
 * Copyright 2021 HM Revenue & Customs
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
import uk.gov.hmrc.trelloradar.model.{FullQuadrant, Quadrant, Ring, TrelloBoard, TrelloCard}
import uk.gov.hmrc.trelloradar.views.html.RadarPage

import scala.concurrent.ExecutionContext
import uk.gov.hmrc.trelloradar.model.Readers._




@Singleton
class HelloWorldController @Inject()(
  appConfig: AppConfig,
  mcc: MessagesControllerComponents,
  radarPage: RadarPage,
  tc: TrelloConnector)(implicit ec: ExecutionContext)
    extends FrontendController(mcc) {

  implicit val config: AppConfig = appConfig


  val quadrant0 = Quadrant(id = 0, trelloLabelId = "5fb50136a9836541f1a5e935", name = "1: The right tools at the right times")
  val quadrant1 = Quadrant(id = 1, trelloLabelId = "5fb5015da5699475b5073869", name = "2: Effective and secure")
  val quadrant2 = Quadrant(id = 2, trelloLabelId = "5fb50883bb35ce08913902f9", name = "3: Propagate learning and experience")
  val quadrant3 = Quadrant(id = 3, trelloLabelId = "5fb5089911dc63879d852428", name = "4: Great place to work")




  val radar: Action[AnyContent] = Action.async {
    for {
      boardString <- tc.getBoard()
      cardsString <- tc.getCardsForBoard()
    } yield {
      val board: TrelloBoard = Json.parse(boardString.get).as[TrelloBoard]
      val cards: List[TrelloCard] = Json.parse(cardsString.get).as[List[TrelloCard]]

      Ok(radarPage(
        board.url,
        buildQuadrant(quadrant0, cards),
        buildQuadrant(quadrant1, cards),
        buildQuadrant(quadrant2, cards),
        buildQuadrant(quadrant3, cards)
      )())
    }
  }


  // TODO - make the time periods configurable
  def buildQuadrant(quadrant: Quadrant, cards: List[TrelloCard]) = {
    FullQuadrant.apply(quadrant,
      filterCards(ringId = 0, quadrant = quadrant.trelloLabelId, timeframe = "now", cards),
      filterCards(ringId = 1, quadrant = quadrant.trelloLabelId, timeframe = "soon", cards),
      filterCards(ringId = 2, quadrant = quadrant.trelloLabelId, timeframe = "later", cards),
      filterCards(ringId = 3, quadrant = quadrant.trelloLabelId, timeframe = "someday", cards))
  }


  def filterCards(ringId: Int, quadrant: String, timeframe: String, cards: List[TrelloCard]): Ring = {
    val ringCards = cards.filter{ c => c.labels.exists(_.id == quadrant) && c.labels.exists(_.name == timeframe) }
    Ring(ringId, quadrant, ringCards)
  }

}
