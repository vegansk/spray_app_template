import dependencies._

version := "0.1.0"

scalaVersion := V.scala

libraryDependencies ++= Seq(
  "io.spray"          %% "spray-can"     % V.spray withSources() withJavadoc(),
  "io.spray"          %% "spray-routing" % V.spray withSources() withJavadoc(),
  "io.spray"          %% "spray-testkit" % V.spray % "test" withSources() withJavadoc(),
  "com.typesafe.akka" %% "akka-actor"    % V.akka withSources() withJavadoc(),
  "com.typesafe.akka" %% "akka-testkit"  % V.akka % "test" withSources() withJavadoc(),
  "org.specs2"        %% "specs2-core"   % V.specs2 % "test" withSources() withJavadoc()
)

Revolver.settings
