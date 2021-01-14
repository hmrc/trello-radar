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
import uk.gov.hmrc.trelloradar.model.{FullQuadrant, FullRing, Quadrant, Ring, TrelloBoard, TrelloCard}
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

  val quadrant0 = Quadrant(id = 0, trelloLabelId = appConfig.quadrant0TrelloLabelId, name = "1: The right tools at the right times")
  val quadrant1 = Quadrant(id = 1, trelloLabelId = appConfig.quadrant1TrelloLabelId, name = "2: Effective and secure")
  val quadrant2 = Quadrant(id = 2, trelloLabelId = appConfig.quadrant2TrelloLabelId, name = "3: Propagate learning and experience")
  val quadrant3 = Quadrant(id = 3, trelloLabelId = appConfig.quadrant3TrelloLabelId, name = "4: Great place to work")

  val ring0 = Ring(0, appConfig.ring0TrelloLabelId, name = "now")
  val ring1 = Ring(1, appConfig.ring0TrelloLabelId, name = "soon")
  val ring2 = Ring(2, appConfig.ring0TrelloLabelId, name = "later")
  val ring3 = Ring(3, appConfig.ring0TrelloLabelId, name = "someday")



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

  def buildQuadrant(quadrant: Quadrant, cards: List[TrelloCard]) = {
    FullQuadrant.apply(quadrant,
      filterCards(ring0, quadrant, cards),
      filterCards(ring1, quadrant, cards),
      filterCards(ring2, quadrant, cards),
      filterCards(ring3 , quadrant, cards))
  }

  def filterCards(ring: Ring, quadrant: Quadrant, cards: List[TrelloCard]): FullRing = {
    val ringCards = cards.filter{ c => c.labels.exists(_.id == quadrant.trelloLabelId) && c.labels.exists(_.id == ring.trelloLabelId) }
    FullRing(ring, ringCards)
  }

}
