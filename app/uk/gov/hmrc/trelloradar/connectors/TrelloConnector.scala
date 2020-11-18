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

package uk.gov.hmrc.trelloradar.connectors

import com.google.inject.Inject
import uk.gov.hmrc.http.logging.Authorization
import uk.gov.hmrc.http.{HeaderCarrier, HttpClient, HttpResponse}

import scala.concurrent.{ExecutionContext, Future}

class TrelloConnector @Inject()(http: HttpClient)(implicit ec: ExecutionContext){


  val apiToken = "d6879ac4676eab445ceffc2434efd55dc00db902d650117828f7445bab7cfc6f"
  val apiKey = "08ff6a872da775510499e53b65c28e72"
  val boardId = "5fb2a4ebabe51e0a03d10116"

  val cardsEndpoint = s"https://api.trello.com/1/boards/$boardId/cards?key=$apiKey&token=$apiToken"

    def getCardsForBoard(): Future[Option[String]] = {

      implicit val hc = HeaderCarrier()
      http.GET[Option[HttpResponse]](url = cardsEndpoint).map(_.map(_.body))
    }

}
