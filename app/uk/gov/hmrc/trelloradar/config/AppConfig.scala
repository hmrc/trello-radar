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

package uk.gov.hmrc.trelloradar.config

import javax.inject.{Inject, Singleton}
import play.api.Configuration
import uk.gov.hmrc.play.bootstrap.config.ServicesConfig

@Singleton
class AppConfig @Inject()(config: Configuration, servicesConfig: ServicesConfig) {
  val footerLinkItems: Seq[String] = config.getOptional[Seq[String]]("footerLinkItems").getOrElse(Seq())

  val apiToken: String = config.get[String]("platform-priorites.apiToken")
  val apiKey: String = config.get[String]("platform-priorites.apiKey")
  val boardId: String = config.get[String]("platform-priorites.boardId")


  val quadrant0TrelloLabelId: String = config.get[String]("platform-priorites.quadrant0TrelloLabelId")
  val quadrant1TrelloLabelId: String = config.get[String]("platform-priorites.quadrant1TrelloLabelId")
  val quadrant2TrelloLabelId: String = config.get[String]("platform-priorites.quadrant2TrelloLabelId")
  val quadrant3TrelloLabelId: String = config.get[String]("platform-priorites.quadrant3TrelloLabelId")

  val ring0TrelloLabelId: String = config.get[String]("platform-priorities.ring0TrelloLabelId")
  val ring1TrelloLabelId: String = config.get[String]("platform-priorities.ring1TrelloLabelId")
  val ring2TrelloLabelId: String = config.get[String]("platform-priorities.ring2TrelloLabelId")
  val ring3TrelloLabelId: String = config.get[String]("platform-priorities.ring3TrelloLabelId")

}
