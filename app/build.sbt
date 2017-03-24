enablePlugins(PlayScala)

scalaVersion := "2.11.8"

scalacOptions := Seq(
	"-encoding", "UTF-8", "-target:jvm-1.8", "-deprecation",
	"-feature", "-unchecked", "-language:implicitConversions", "-language:postfixOps")

libraryDependencies ++= Seq(
	"org.webjars" % "swagger-ui" % "2.2.10",
	"com.typesafe.play" %% "play-slick" % "2.1.0",
	"com.typesafe.play" %% "play-slick-evolutions" % "2.1.0",
	"org.postgresql" % "postgresql" % "42.0.0"
)
