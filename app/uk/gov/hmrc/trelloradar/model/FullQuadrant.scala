/*
 * Copyright 2023 HM Revenue & Customs
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

case class Quadrant(id: Int, trelloLabelId: String, name: String)

case class FullQuadrant(id: Int,
                        trelloLabelId: String,
                        name: String,
                        ring0: FullRing,
                        ring1: FullRing,
                        ring2: FullRing,
                        ring3: FullRing) {
}

object FullQuadrant {
  def apply(quadrant: Quadrant, ring0: FullRing, ring1: FullRing, ring2: FullRing, ring3: FullRing): FullQuadrant = {
    FullQuadrant(quadrant.id, quadrant.trelloLabelId, quadrant.name, ring0, ring1, ring2, ring3)
  }
}
