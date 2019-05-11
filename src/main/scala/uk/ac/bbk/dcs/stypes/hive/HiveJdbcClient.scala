package uk.ac.bbk.dcs.stypes.hive

import java.sql.SQLException
import java.sql.DriverManager


object HiveJdbcClient {
  private val driverName = "org.apache.hive.jdbc.HiveDriver"

  @throws[SQLException]
  def main(args: Array[String]): Unit = {
    try
      Class.forName(driverName)
    catch {
      case e: ClassNotFoundException =>
        // TODO Auto-generated catch block
        e.printStackTrace()
        System.exit(1)
    }
    val con = DriverManager.getConnection("jdbc:hive://localhost:10000/default", "", "")
    val stmt = con.createStatement
    val tableName = "testHiveDrxiverTable"
    stmt.executeQuery("drop table " + tableName)
    var res = stmt.executeQuery("create table " + tableName + " (key int, value string)")
    // show tables
    var sql = "show tables '" + tableName + "'"
    System.out.println("Running: " + sql)
    res = stmt.executeQuery(sql)
    if (res.next) System.out.println(res.getString(1))
    // describe table
    sql = "describe " + tableName
    System.out.println("Running: " + sql)
    res = stmt.executeQuery(sql)
    while ( {
      res.next
    }) System.out.println(res.getString(1) + "\t" + res.getString(2))
    // load data into table
    // NOTE: filepath has to be local to the hive server
    // NOTE: /tmp/a.txt is a ctrl-A separated file with two fields per line
    val filepath = "/tmp/a.txt"
    sql = "load data local inpath '" + filepath + "' into table " + tableName
    System.out.println("Running: " + sql)
    res = stmt.executeQuery(sql)
    // select * query
    sql = "select * from " + tableName
    System.out.println("Running: " + sql)
    res = stmt.executeQuery(sql)
    while ( {
      res.next
    }) System.out.println(String.valueOf(res.getInt(1)) + "\t" + res.getString(2))
    // regular hive query
    sql = "select count(1) from " + tableName
    System.out.println("Running: " + sql)
    res = stmt.executeQuery(sql)
    while ( {
      res.next
    }) System.out.println(res.getString(1))
  }
}