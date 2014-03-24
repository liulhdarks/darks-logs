Darks Logs 
==========

Darks logs is a log library like log4j for Java, Android and Web project. It can be configured and used like log4j,. At the same time it can be easy to used on android application. Darks logs can output log message to console, logcat, file, socket, sqlite and so on. Developers can even customize its output style.
Let's have a look how to use it.

Configuration File
-----------
Before we use darks-logs, we must create the configuration file named logd.properties. 

### Java configuration
If your project need to work on console, Web or desktop with Java, you should create the logd.properties in the root of src directory. 

### Android configuration
If your project need to work on android application, you should create it in assets directory. After ensure logd.properties file exists, we must set the android.app.Application object to Logger before used.<br/>
Example:<br/>
<pre>
  public class CustomApplication extends Application
  {
      @Override
      public void onCreate()
      {
          Logger.Android.setApplication(this);
          super.onCreate();
      }
  }
</pre>
After logd.properties created, we can configure it for logger.

Start Logger
-----------
After config logd.properties and set Application object(Only for Android environment), developers can use Logger now. You can get the Logger by called static method getLogger with class object or tag string. We recommend you define Logger as the static variable.<br/>
Example:
<pre>
  static Logger log = Logger.getLogger(TestLogger.class);
  static Logger log = Logger.getLogger("darks.logs.test.TestLogger");
  static Logger log = Logger.getLogger("TestLogger");
</pre>
After define Logger variable, you can call info, debug, warn, error and so on to output message for specify level.
Example:
<pre>
  log.debug("This is the darks logs hello world.");
  log.info("Info message will be output");
  log.error("Happen a exception. Cause " + e.getMessage(), e);
</pre>

Modify Configuration
-----------

### Config Root
In the logd.properties, we must configure the root logger first of all. Tags start with string "logd.root", and its value format is [LEVEL],[APPENDER 1],[APPENDER 2],...,[APPENDER N].<br/>
Example:<br/>
<pre>
  logd.root=info,console,FILE
</pre>

### Config Appenders
Darks logs has realized some frequently used appenders. Such as ConsoleAppender, AndroidAppender, FileAppender and so on. If you have configure multiple appenders, it will output log message to each appender when every message comes. All of appenders inherit from Appender<br/>
If you need to configure class full name such as "darks.log.appender.impl.ConsoleAppender", you could just configure ConsoleAppender. If you configure class full name, it will load class directly. Or if you configure class simple name, it will find class in package darks.log.appender.impl, darks.log.filter, darks.log.layout, darks.log.pattern, darks.log.externs and so on.
Example:
<pre>
  logd.root=info,console
  #logd.appender.console=darks.log.appender.impl.ConsoleAppender
  logd.appender.console=ConsoleAppender
  logd.appender.console.layout=PatternLayout
  logd.appender.console.layout.pattern=%d{yyyy-MM-dd HH:mm:ss} %c{1} - %m%n
</pre>

<h4>Appender</h4><br/>
Appender is the base class of all appenders. Appender can configure layout, filter and so on.<br/>

  <h5>layout</h5>
  Logger layout can format log message. You can use darks.log.PatternLayout, darks.log.SimpleLayout or custom layout.
  
  PatternLayout: Use pattern to format message. Such as 
  <pre>%d{yyyy-MM-dd HH:mm:ss} %c{1} - %m%n</pre>
  
  SimpleLayout: Output the simple message. 
  <pre>level - message</pre>
  Custom layout:
<pre>
  public class CustomLayout extends LoggerLayout
  {
      public String format(LogMessage message)
      {
          ...
      }
  }
</pre>
  
  <h5>filter</h5>
  Logger filter can filter log message such as LevelRangeFilter by level.<br/>
  
  LevelRangeFilter: Output message which's level between minimum level and maximum level.<br/>
  Example:
<pre>
  logd.appender.console=ConsoleAppender
  logd.appender.console.layout=PatternLayout
  logd.appender.console.layout.pattern=%d{yyyy-MM-dd HH:mm:ss} %c{1} - %m%n
  logd.appender.console.filter=LevelRangeFilter
  logd.appender.console.filter.levelMin=debug
  logd.appender.console.filter.levelMax=info
  logd.appender.console.filter.accept=false
</pre>

  RegexMatchFilter: Regex match filter will output logs which messages match regex pattern.<br/>
  Example:
<pre>
  logd.appender.console=ConsoleAppender
  logd.appender.console.layout=PatternLayout
  logd.appender.console.layout.pattern=%d{yyyy-MM-dd HH:mm:ss} %c{1} - %m%n
  logd.appender.console.filter=RegexMatchFilter
  logd.appender.console.filter.pattern=darks\d+
  logd.appender.console.filter.accept=true
</pre>

  Custom filter:
<pre>
  public class CustomFilter extends LoggerFilter
  {
      public int decide(LogMessage msg)
      {
          ...
          return ALLOW;
      }
  }
</pre>
  
  <h5>async</h5>
  If true, log message will be output in logger thread.
<pre>logd.appender.console.async=false</pre>
</pre>

<h4>ConsoleAppender</h4>
ConsoleAppender is used to output message to command console.<br/>
Example:
<pre>
  logd.appender.console=ConsoleAppender
  logd.appender.console.layout=PatternLayout
  logd.appender.console.layout.pattern=%d{yyyy-MM-dd HH:mm:ss} %c{1} - %m%n
</pre>

<h4>AndroidAppender</h4>
AndroidAppender is used to output message to android logcat.<br/>
Example:
<pre>
  logd.appender.ANDROID=AndroidAppender
  logd.appender.ANDROID.layout=PatternLayout
  logd.appender.ANDROID.layout.pattern=%m%n
</pre>

<h4>StreamAppender</h4>
StreamAppender  will output log message to stream. You cannot use StreamAppender directly in logd.properties. You should create a sub class which extends StreamAppender.<br/>
Example:
<pre>
  public class CustomAppender extends StreamAppender
  {
      @Override
      public void activateHandler()
      {
          ...
      }
  
      @Override
      protected void expandAppend(LogMessage msg, String log) throws Exception
      {
          ...
      }
  }
</pre>

<h4>SocketAppender</h4>
SocketAppender will send log message through TCP protocol. It extends StreamAppender.<br/>
Example:
<pre>
  logd.appender.SOCKET=SocketAppender
  logd.appender.SOCKET.layout=PatternLayout
  logd.appender.SOCKET.layout.pattern=%d{yyyy-MM-dd HH:mm:ss} %c{1} - %m%n
  logd.appender.SOCKET.serverHost=127.0.0.1
  logd.appender.SOCKET.serverPort=8686
  logd.appender.SOCKET.keepAlive=true
  logd.appender.SOCKET.tcpNoDelay=true
  #Wrap log message with head bytes.
  logd.appender.SOCKET.wrapBytes=true
</pre>

<h4>FileAppender</h4>
FileAppender will output message to file. It extends StreamAppender. If you want to create dynamical file name, you can use ${PROPERTY_VARIABLE} to get system property value such as ${user.dir}. If you want to get android SDCARD root directory, you can just use ${sdcard} to get the absolute path of android SDCARD. If you want to include the date or time in file name, you can use ${D[DATE_PATTERN]} such as ${Dyyyy_MM_dd_HH_mm_ss}.<br/>
Example:
<pre>
  logd.appender.FILE=FileAppender
  logd.appender.FILE.layout=PatternLayout
  logd.appender.FILE.layout.pattern=%d{yyyy-MM-dd HH:mm:ss} %c{1} - %m%n
  logd.appender.FILE.fileName=${user.dir}\log_${Dyyyy_MM_dd_HH_mm_ss_SS}.txt
  #logd.appender.FILE.fileName=${sdcard}/log_${Dyyyy_MM_dd_HH_mm_ss_SS}.txt
  logd.appender.FILE.buffered=true
</pre>

<h4>FileDateSizeAppender</h4>
FileAppender can keep log files in custom days(default 7 days). If current log file's size is out of custom max size(Default 10MB), it will create the new one. It extends FileAppender. <br/>
Example:
<pre>
  logd.appender.FILE=FileDateSizeAppender
  logd.appender.FILE.layout=PatternLayout
  logd.appender.FILE.layout.pattern=%d{yyyy-MM-dd HH:mm:ss} %c{1} - %m%n
  logd.appender.FILE.fileName=${user.dir}\log_${Dyyyy_MM_dd_HH_mm_ss_SS}.txt
  logd.appender.FILE.buffered=true
  logd.appender.FILE.maxSize=10485760
  logd.appender.FILE.keepDay=7
</pre>

<h4>StorgeAppender</h4>
StorgeAppender will output log message to stream. You cannot use StorgeAppender directly in logd.properties. You should create a sub class which extends StorgeAppender.<br/>
Example:
<pre>
  public class CustomDBAppender extends StorgeAppender
  {
      @Override
      public boolean activateHandler()
      {
          return super.activateHandler();
      }
    
      @Override
      public void append(LogMessage msg, String log) throws Exception
      {
          ...
      }
  }
</pre>

<h4>SqliteAppender</h4>
SqliteAppender can use android sqlite database to save log records. It will build the insert SQL statement automatically through columnsMap and table fields.<br/>
Example:
<pre>
  logd.appender.SQLITE=SqliteAppender
  logd.appender.SQLITE.layout=PatternLayout
  logd.appender.SQLITE.dbName=db_records
  logd.appender.SQLITE.dbVersion=1
  logd.appender.SQLITE.table=t_logs
  logd.appender.SQLITE.createSQL=create table if not exists t_logs(_id integer primary key autoincrement,date text,level text,source integer,file text,message text)
  logd.appender.SQLITE.columnsMap.date=%d{yyyy-MM-dd HH:mm:ss}
  logd.appender.SQLITE.columnsMap.level=%p
  logd.appender.SQLITE.columnsMap.source=%L
  logd.appender.SQLITE.columnsMap.file=%f
  logd.appender.SQLITE.columnsMap.message=%m
</pre>

<h4>Custom Appender</h4>
Developer can create custom appender by inherit Appender. If override needPattern return true, it will format log message before append.<br/>
Example:
<pre>
  public class CustomAppender extends Appender
  {
      @Override
      public void append(LogMessage msg, String log) throws Exception
      {
   	      ...
      }
  
      @Override
      public boolean needPattern()
      {
          return true;
      }
  }
</pre>

### Config Pattern
Layout will use DefaultPattern to format message by default.<br/>
<h4>DefaultPattern</h4>
<pre>
  %n, %N: Output a return character.
  %m, %M: Output the log message content.
  %e, %E: Output exception stack information.
  %d, %D: Output date by format pattern. Such as "%d{yyyy-MM-dd HH:mm:ss}".
  %c:     Output the namespace or tags. 
          You can use {layer number} to output the namespace's layer specified. 
          Such as %c{1}, If tag is "darks.log.DemoMain", it will be "DemoMain".
  %C:     Output the class name.
          You can use {layer number} to output the classname's layer specified. 
          Such as %C{2}, If class name is "darks.log.DemoMain", it will be "log.DemoMain".
  %f, %F: Output the source file name.
  %L:     Output the source code line.
  %l:     Output the event information include caller class, thread name, source file and source line.
  %p, %P: Output the log level.
  %r, %R: Output the cost time from startup.
  %t, %T: Output the thread name.
</pre>
Example:
<pre>
  logd.appender.console.layout.pattern=%d{yyyy-MM-dd HH:mm:ss} [%f][%p] - %m%n
  logd.appender.console.layout.pattern=%d{yyyy-MM-dd HH:mm:ss} %c{2} - %m%n
</pre>

### Namespace Category
Developer can use logd.logger.[NAMESPACE] to specify logger for each namespace.<br/>
Example:
<pre>
  logd.logger.darks.log.test=debug
  logd.logger.darks.log.test.file=debug,FILE
  logd.logger.darks.log=debug,console
</pre>
If you specify the sub category for namespace with one or more appenders, sub logger's appenders will be called before root appenders¡£ That is to say message will be output twice. If you want to avoid this case, you can use logd.additivity or logd.inherit which decide whether sub logger inherit root logger.<br/>
Example:
<pre>
#logd.additivity = false
logd.inherit = false
logd.inherit.darks.logs.test = true
</pre>

Comprehensive Example
-----------
<pre>
  logd.root=debug,console,FILE
  logd.additivity = false  

  logd.appender.console=ConsoleAppender
  logd.appender.console.layout=PatternLayout
  logd.appender.console.layout.convertor=DefaultPattern
  logd.appender.console.layout.pattern=%d{yyyy-MM-dd HH:mm:ss} [%p] %c - %m%n
  logd.appender.console.async=false
  logd.appender.console.filter=LevelRangeFilter
  logd.appender.console.filter.levelMin=debug
  logd.appender.console.filter.levelMax=info
  logd.appender.console.filter.accept=false

  logd.appender.FILE=FileDateSizeAppender
  logd.appender.FILE.layout=PatternLayout
  logd.appender.FILE.layout.pattern=%d{yyyy-MM-dd HH:mm:ss} %c{1} - %m%n
  logd.appender.FILE.fileName=${user.dir}\logs\log_${Dyyyy_MM_dd_HH_mm_ss}.txt
  logd.appender.FILE.buffered=true
  logd.appender.FILE.maxSize=2000000

  logd.logger.darks.log.test=info
</pre>
I wish you a pleasant to use darks-logs. If you have some good advice or bug report, please share with us. Thank you!
