# Newrelic Oracle ATG Plugin
This plugin collects metrics from all server instances in the form of JSON. This JSON format looks as follows:

###### JSON response format:
	{
	  "success": "true|false",
	  "status": "status code"
	  "message": "error|success message",
	  "components": [
	    { "name": "component-name",
	      "description": "component-description",
	      "metrics": [{"name": "metric-name", "unit": "metric-unit", "value": "value"}]
	    }
	  ]
	}
    
###### For Example:
	{
	  "success": "true",
	  "status": 200
	  "message": "OK",
	  "components": [
	    { "name": "/atg/commerce/catalog/Test",
	      "description": "Test component",
	      "metrics": [{"name": "testCount", "unit": "count", "value": 300}]
	    }
	  ]
	}
    
----

## Requirements
- A New Relic account. Sign up for a free account [here](http://newrelic.com).
- Java Runtime (JRE) environment Version 1.6 or later.

----

## Installation
#### Step 1 - Downloading and Extracting the Plugin
The latest version of the plugin can be downloaded [here](https://rpm.newrelic.com/extensions/com.objectedge.oracle.newrelic).  Once the plugin is on your box, extract it to a location.

**Note** - This plugin is distributed in tar.gz format and can be extracted with the following command on Unix-based systems.

```
tar -xvzf newrelic_wikipedia_plugin-X.Y.Z.tar.gz
```
#### Step 2 - Configuring the plugin
Check out the [configuration information](#configuration-information) section for details on configuring your plugin.

#### Step 3 - Start and stop the Plugin
To run the plugin, execute the startPlugin.sh shell script in terminal:
```
$ ./startPlugin.sh
```
To terminate the plugin, execute the stopPlugin.sh shell script in terminal:

```
$ ./stopPlugin.sh
```
----

## Configuration Information

### Configuration Files

You will need to modify two configuration files in order to set this plugin up to run.  The first (`newrelic.json`) contains configurations used by all Platform plugins (e.g. license key, logging information, proxy settings) and can be shared across your plugins.  The second (`plugin.json`) contains data specific to each plugin such as a list of hosts and port combination for what you are monitoring.  Templates for both of these files should be located in the '`config`' directory in your extracted plugin folder. 

#### Configuring the `plugin.json` file: 

The `plugin.json` file has a provided template in the `config` directory named `plugin.template.json`.  If you are installing manually, make a copy of this template file and rename it to `plugin.json` (the New Relic Platform Installer will automatically handle creation of configuration files for you).  

Below is an example of the `plugin.json` file's contents, you can add multiple objects to the "agents" array to monitor different instances:

```
{
  "agents": [
    {
      "name" : "Estore-PRD-1",
      "host" : "estore-host-name-1"
      "port" : estore-port-1
    },
    {
      "name" : "Estore-PRD-2",
      "host" : "estore-prd-url"
    },
    {
      "name" : "BCC-PRD",
      "host" : "bcc-prd-host-name"
      "port" : bcc-prd-port
    },
    	.........
    	.........
    	.........
  ]
}
```

**note** - The "name" attribute is used to identify specific instances in the New Relic UI. 

#### Configuring the `newrelic.json` file: 

The `newrelic.json` file also has a provided template in the `config` directory named `newrelic.template.json`.  If you are installing manually, make a copy of this template file and rename it to `newrelic.json`.  

The `newrelic.json` is a standardized file containing configuration information that applies to any plugin (e.g. license key, logging, etc.), so going forward you will be able to copy a single `newrelic.json` file from one plugin to another.  Below is a list of the configuration fields that can be managed through this file:

##### Configuring your New Relic License Key

Your New Relic license key is the only required field in the `newrelic.json` file as it is used to determine what account you are reporting to.  If you do not know what your license key is, you can learn about it [here](https://newrelic.com/docs/subscriptions/license-key).

Example: 

```
{
  "license_key": "YOUR_LICENSE_KEY_HERE"
}
```

##### Logging configuration

By default Platform plugins will have their logging turned on; however, you can manage these settings with the following configurations:

`log_level` - The log level. Valid values: [`debug`, `info`, `warn`, `error`, `fatal`]. Defaults to `info`.

`log_file_name` - The log file name. Defaults to `newrelic_plugin.log`.

`log_file_path` - The log file path. Defaults to `logs`.

`log_limit_in_kbytes` - The log file limit in kilobytes. Defaults to `25600` (25 MB). If limit is set to `0`, the log file size would not be limited.

Example:

```
{
  "license_key": "YOUR_LICENSE_KEY_HERE"
  "log_level": "debug",
  "log_file_path": "/var/logs/newrelic"
}
```
----