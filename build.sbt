name := "stypes-hive"

version := "0.1"

scalaVersion := "2.12.8"

val hiveVersion = "3.1.1"
val hadoopVersion = "3.2.0"

libraryDependencies ++= Seq(
  "org.apache.hive" % "hive-exec" % hiveVersion excludeAll ExclusionRule(organization = "org.pentaho")
  , "org.apache.hadoop" % "hadoop-common" % hadoopVersion
  , "org.apache.httpcomponents" % "httpclient" % "4.5.8"
  , "org.apache.hadoop" % "hadoop-client" % hadoopVersion
  , "org.apache.hive" % "hive-service" % hiveVersion
  , "org.apache.hive" % "hive-cli" % hiveVersion
  , "org.scalatest" % "scalatest_2.12" % "3.0.7"
  , "ch.qos.logback" % "logback-classic" % "1.2.3"
  , "org.apache.logging.log4j" % "log4j-api" % "2.11.2"
  , "org.apache.derby" % "derby" % "10.15.1.3"

)

