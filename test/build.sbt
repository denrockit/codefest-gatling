enablePlugins(GatlingPlugin)

scalaVersion := "2.12.8"

scalacOptions := Seq(
	"-encoding", "UTF-8", "-target:jvm-1.8", "-deprecation",
	"-feature", "-unchecked", "-language:implicitConversions", "-language:postfixOps")

libraryDependencies ++= Seq(
	"io.gatling.highcharts" % "gatling-charts-highcharts" % "2.3.1" % "test",
	"io.gatling" % "gatling-test-framework" % "2.3.1" % "test",
	"org.postgresql" % "postgresql" % "42.2.5" % "test"
)
