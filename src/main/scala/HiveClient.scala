import java.io.IOException
import scala.util.Try
import org.apache.hadoop.hive.cli.CliSessionState
import org.apache.hadoop.hive.conf.HiveConf
import org.apache.hadoop.hive.ql.Driver
import org.apache.hadoop.hive.ql.session.SessionState


class HiveClient {

  val hiveConf = new HiveConf(classOf[HiveClient])
  /**
    * Get the hive ql driver to execute ddl or dml
    *
    * @return
    */
  private def getDriver: Driver = {
    val driver = new Driver(hiveConf)
    SessionState.start(new CliSessionState(hiveConf))
    driver
  }
  /**
    * @param hql
    * @throws {{org.apache.hadoop.hive.ql.CommandNeedRetryException}}
    * @return int
    */
  def executeHQL(hql: String): Int = {
    val responseOpt = Try(getDriver.run(hql)).toEither
    val response = responseOpt match {
      case Right(response) => response
      case Left(exception) => throw new Exception(s"${ exception.getMessage }")
    }
    val responseCode = response.getResponseCode
    if (responseCode != 0) {
      val err: String = response.getErrorMessage
      throw new IOException("Failed to execute hql [" + hql + "], error message is: " + err)
    }
    responseCode
  }

}
