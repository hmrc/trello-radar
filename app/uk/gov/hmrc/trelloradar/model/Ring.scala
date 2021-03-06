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

case class Ring(id: Int, trelloLabelId: String, name: String)

case class FullRing(id: Int, trelloLabelId: String, name: String, cards: List[TrelloCard])

object FullRing {
  def apply(ring: Ring, cards: List[TrelloCard]): FullRing = {
    FullRing(ring.id, trelloLabelId = ring.trelloLabelId, name = ring.name, cards)
  }
}
