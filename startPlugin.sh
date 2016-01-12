if type -p java; then
    echo Found Java executable in PATH
    _java=java
elif [[ -n "$JAVA_HOME" ]] && [[ -x "$JAVA_HOME/bin/java" ]];  then
    echo Found Java executable in JAVA_HOME     
    _java="$JAVA_HOME/bin/java"
else
    echo "No Java found on system. Please install JDK version 1.6 or greater."
fi

if [[ "$_java" ]]; then
    version=$("$_java" -version 2>&1 | awk -F '"' '/version/ {print $2}')
    echo version "$version"
    if [[ "$version" < "1.5" ]]; then
        echo "Please install JDK version 1.6 or greater."
    else
	nohup java -Xmx128m -Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=4000,suspend=n -jar plugin.jar >> ./logs/newrelic_oracle_atg_plugin.log &
    fi
fi




