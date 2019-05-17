enablePlugins(PlayScala)

scalaVersion := "2.12.8"

scalacOptions := Seq(
	"-encoding", "UTF-8", "-target:jvm-1.8", "-deprecation",
	"-feature", "-unchecked", "-language:implicitConversions", "-language:postfixOps")

libraryDependencies ++= Seq(
	guice,
	"org.webjars" % "swagger-ui" % "2.2.10",
	"com.typesafe.play" %% "play-slick" % "4.0.1",
	"com.typesafe.play" %% "play-slick-evolutions" % "4.0.1",
	"org.postgresql" % "postgresql" % "42.2.5"
)
