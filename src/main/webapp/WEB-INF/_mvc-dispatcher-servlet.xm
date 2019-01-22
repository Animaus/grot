<?xml version="1.0" encoding="UTF-8"?>
<beans
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns="http://www.springframework.org/schema/beans"
	xmlns:context="http://www.springframework.org/schema/context"
    xmlns:jee="http://www.springframework.org/schema/jee"
	xmlns:jpa="http://www.springframework.org/schema/data/jpa"
	xmlns:jdbc="http://www.springframework.org/schema/jdbc"
	xmlns:mvc="http://www.springframework.org/schema/mvc"
	xmlns:security="http://www.springframework.org/schema/security"
	xmlns:tx="http://www.springframework.org/schema/tx"
	xsi:schemaLocation="
http://www.springframework.org/schema/beans
http://www.springframework.org/schema/beans/spring-beans-4.3.xsd
http://www.springframework.org/schema/context
http://www.springframework.org/schema/context/spring-context-4.3.xsd
http://www.springframework.org/schema/jee
http://www.springframework.org/schema/jee/spring-jee-4.3.xsd
http://www.springframework.org/schema/jdbc 
http://www.springframework.org/schema/jdbc/spring-jdbc-4.3.xsd
http://www.springframework.org/schema/data/jpa
http://www.springframework.org/schema/data/jpa/spring-jpa-1.8.xsd
http://www.springframework.org/schema/data/repository
http://www.springframework.org/schema/data/repository/spring-repository-2.1.xsd
http://www.springframework.org/schema/mvc
http://www.springframework.org/schema/mvc/spring-mvc-4.3.xsd
http://www.springframework.org/schema/security
http://www.springframework.org/schema/security/spring-security-4.2.xsd
http://www.springframework.org/schema/tx
http://www.springframework.org/schema/tx/spring-tx-4.3.xsd"
default-autowire="byName">

	<import resource="classpath:spring/applicationContext.xml"/>

</beans>