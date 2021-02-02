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

package uk.gov.hmrc.trelloradar.model

case class TrelloCard(id: String,
                      name: String,
                      closed: Boolean,
                      labels: List[TrelloLabel] = List.empty
                     ) {

  // TODO - move this list out to config and use ids
  val teamsLabelList = List[String]("Cross-team", "Infrastructure", "PlatOps", "Build & Deploy", "Platform Security", "CIP", "Platform UI", "Telemetry", "DDC Ops", "Tiger", "Test Leads", "Architecture", "API Platform", "Platform Operations")

  val teams = labels.filter(l => teamsLabelList.contains(l.name))

  val teamsString = teams.toString()


  val shortname = name

//  val shortname = {
//    val lenghtOfShortname = 65
//
//    println("My name is: " + name)
//    if (name.length > lenghtOfShortname) {
//      name.substring(0, lenghtOfShortname) + "..."
//    } else name
//
//  }
}







