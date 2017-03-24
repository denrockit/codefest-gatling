import com.typesafe.config.ConfigFactory

object Conf {

	private val conf = ConfigFactory.load()

	val baseUrl: String = conf.getString("base-url")
	val feederSize: Int = conf.getInt("feeder-size")

	val dbHost: String = conf.getString("db.host")
	val dbPort: Int = conf.getInt("db.port")
	val dbName: String = conf.getString("db.name")
	val dbUrl: String = s"jdbc:postgresql://$dbHost:$dbPort/$dbName"
	val dbUser: String = conf.getString("db.user")
	val dbPassword: String = conf.getString("db.pass")
}
