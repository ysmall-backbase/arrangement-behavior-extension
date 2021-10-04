## arrangement-behavior-extension.

This project is an example on how to create a behaviour extension for arrangement-manager. 
Behavior extension added so far:

* [getArrangementsByBusinessFunction](https://docs.backbase.com/extranet/technical-docs/endpoints/2021.05/specs/arrangement-client-api.html#api-ProductSummary-getArrangementsByBusinessFunction)

**Make sure you create all the extended classes under com.backbase.dbs.product package** as this is the package that Spring will scan to create the beans.

## DBS Version

2.21.1

## How to use

To use your service extension, you include the JAR build from this artifact to the CLASSPATH used when the service is 
started.


When you run a service as an [executable JAR](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#executable-jar-property-launcher-features), 
use the `loader.path` command line argument to add JARs or directories of JARs to the classpath. For example:

```
./arrangement-behavior-extension.jar -Dloader.path=/path/to/my.jar,/path/to/my/other.jar,/path/to/lib-dir
```

If you are not running the Service as a bootable jar, use the mechanism available in your application server.

## Community Documentation

* [Extend the behavior of a service](https://community.backbase.com/documentation/ServiceSDK/latest/extend_service_behavior)

Add links to documentation including setup, config, etc.

## Confluence Links
Links to relevant confluence pages (design etc).

## Support

Slack, Email, Jira etc.

---
- Dummy line for dummy PR 5
