import play.core.PlayVersion.current
import play.sbt.PlayImport._
import sbt.Keys.libraryDependencies
import sbt._

object AppDependencies {

  val bootstrapVersion = "5.12.0"

  val compile = Seq(
    "uk.gov.hmrc"             %% "bootstrap-frontend-play-28" % bootstrapVersion,
    "uk.gov.hmrc"             %% "play-frontend-hmrc"         % "1.1.0-play-28"
  )

  val test = Seq(
    "uk.gov.hmrc"             %% "bootstrap-test-play-28"   % bootstrapVersion % Test,
    "org.scalatest"           %% "scalatest"                % "3.2.9"                 % Test,
    "org.jsoup"               %  "jsoup"                    % "1.14.2"                % Test,
    "com.typesafe.play"       %% "play-test"                % current                 % Test,
    "com.vladsch.flexmark"    %  "flexmark-all"             % "0.36.8"               % "test, it",
    "org.scalatestplus.play"  %% "scalatestplus-play"       % "5.1.0"                 % "test, it",
    "org.mockito"            %% "mockito-scala"            % "1.16.37"                   % Test
  )
}
