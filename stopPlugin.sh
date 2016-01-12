PID=`ps -eaf | grep 'plugin.jar' | grep -v grep | awk '{print $2}'`
if [[ "" !=  "$PID" ]]; then
  echo "Stopping Newrelic Oracle ATG Plugin"
  echo "killing $PID"
  kill -9 $PID
else
  echo "Not running Newrelic Oracle ATG Plugin"
fi



