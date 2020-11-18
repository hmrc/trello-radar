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
import uk.gov.hmrc.trelloradar.views.html.HelloWorldPage
import uk.gov.hmrc.trelloradar.views.html.RadarPage

import scala.concurrent.{ExecutionContext, Future}




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
    implicit val trelloLabelReads = Json.reads[TrelloLabel]
    implicit val cardReads = Json.reads[TrelloCard]
    for {
      cardsString <- tc.getCardsForBoard()
    } yield {
      val cards: List[TrelloCard] = Json.parse(cardsString.get).as[List[TrelloCard]]
      Ok(radarPage(
        buildQuadrant(name = "1: The right tools", cards),
        buildQuadrant(name = "2: Effective and secure", cards),
        buildQuadrant(name = "3: Learning", cards),
        buildQuadrant(name = "4: Great place", cards)
      )())
    }
  }


  def buildQuadrant(name: String, cards: List[TrelloCard]) = {
    Quadrant(name,
      filterCards(quadrant = name, timeframe = "now", cards),
      filterCards(quadrant = name, timeframe = "soon", cards),
      filterCards(quadrant = name, timeframe = "later", cards),
      filterCards(quadrant = name, timeframe = "someday", cards))
  }


  def filterCards(quadrant: String, timeframe: String, cards: List[TrelloCard]): List[TrelloCard] = {
    cards.filter{ c => c.labels.exists(_.name == quadrant) && c.labels.exists(_.name == timeframe) }
  }

}


case class TrelloLabel(id: String = "", name: String)

case class TrelloCard(id: String,
                name: String,
                closed: Boolean,
                labels: List[TrelloLabel] = List.empty
               )

case class Quadrant(name: String, now: List[TrelloCard], soon: List[TrelloCard], later: List[TrelloCard], someday: List[TrelloCard])
