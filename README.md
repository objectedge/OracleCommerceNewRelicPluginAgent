#Newrelic Oracle ATG Plugin

####JSON response format provided by ATG is as follows:

	{
	  "success": "true|false",
	  "status": "status code"
	  "message": "error|success message",
	  "components": [
	    {
	      "name": "component-name",
	      "description": "component-description",
	      "metrics": [
	        {
	          "name": "metric-name",
	          "unit": "metric-unit",
	          "value": "value"
	        }
	      ]
	    }
	  ]
	}

####Example:

	{
	  "success": "true",
	  "status": 200
	  "message": "OK",
	  "components": [
	    {
	      "name": "/atg/commerce/catalog/Test",
	      "description": "Test component",
	      "metrics": [
	        {
	          "name": "testCount",
	          "unit": "count",
	          "value": 300
	        }
	      ]
	    }
	  ]
	}
	

  