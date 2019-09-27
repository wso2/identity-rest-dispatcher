# identity-api-dispatcher

|  Branch | Build Status | Travis CI Status |
| :------------ |:------------- |:-------------
| master      | [![Build Status](https://wso2.org/jenkins/job/platform-builds/job/identity-rest-dispatcher/badge/icon)](https://wso2.org/jenkins/job/platform-builds/job/identity-rest-dispatcher/) | [![Travis CI Status](https://travis-ci.org/wso2/identity-rest-dispatcher.svg?branch=master)](https://travis-ci.org/wso2/identity-rest-dispatcher?branch=master)|

Aggregates the API implementations from [identity-api-user](https://github.com/wso2/identity-api-user/) and 
[identity-api-server](https://github.com/wso2/identity-api-server/) builds a single webapp inorder to expose the 
multiple API endpoints in WSO2 Identity Server. 

*  Refer [identity-api-user](https://github.com/wso2/identity-api-user/) repository for the implementation 
of user APIs

*  Refer [identity-api-server](https://github.com/wso2/identity-api-server/) repository for the implementation 
of server APIs

#### Exposing a new API

1. Add the dependency of the API implementation into the dependencies section of [parent pom file](pom.xml#L121)
2. Include the dependency of the API implementation into the dependencies section of [module pom file](components/org.wso2.carbon.identity.api.dispatcher/pom.xml)
3. Open the [beans.xml](components/org.wso2.carbon.identity.api.dispatcher/src/main/webapp/WEB-INF/beans.xml)

    ```
    
    components
    │   └── org.wso2.carbon.identity.api.dispatcher
    │       ├── pom.xml
    │       ├── src
    │       │   └── main
    │       │       └── webapp
    │       │           ├── META-INF
    │       │           └── WEB-INF
    │       │               ├── beans.xml
    │       │               └── web.xml
    ```
   
4. Import the API CXF xml file. 

    ```
       <import resource="classpath:META-INF/cxf/workflow-engine-server-v1-cxf.xml"/>
       <import resource="classpath:META-INF/cxf/claim-management-server-v1-cxf.xml"/>
       <import resource="classpath:META-INF/cxf/challenge-server-v1-cxf.xml"/>
       <import resource="classpath:META-INF/cxf/email-template-server-v1-cxf.xml"/>
    ```

5. Add the API implementation from [identity-api-user](https://github.com/wso2/identity-api-user/) or 
[identity-api-server](https://github.com/wso2/identity-api-server/) under the corresponding `server` tag.

    ```
    <jaxrs:server id="server" address="/server/v1">
        <jaxrs:serviceBeans>
            <bean class="org.wso2.carbon.identity.rest.api.server.challenge.v1.ChallengesApi"/>
        </jaxrs:serviceBeans>
        <jaxrs:providers>
            <bean class="com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider"/>
        </jaxrs:providers>
    </jaxrs:server>
    <jaxrs:server id="users" address="/users/v1">
        <jaxrs:serviceBeans>
            <bean class="org.wso2.carbon.identity.rest.api.user.association.v1.UsersApi"/>
            <bean class="org.wso2.carbon.identity.rest.api.user.challenge.v1.UserIdApi"/>
            <bean class="org.wso2.carbon.identity.rest.api.user.challenge.v1.MeApi"/>
        </jaxrs:serviceBeans>
        <jaxrs:providers>
            <bean class="com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider"/>
        </jaxrs:providers>
    </jaxrs:server>
    ```
5. Build the component.
    ```
     mvn clean install
    ```

#### Deploy and test

1. Once the build completed, copy the `api.war` in the `components/org.wso2.carbon.identity.api.dispatcher/target/` 
into `[IS_HOME]/repository/deployment/server/webapps/` location and restart the server. (If already exploded `api` 
folder exists in the location, remove it before restarting)

    ```
    ├── components
    │   └── org.wso2.carbon.identity.api.dispatcher
    │       └── target
    │           ├── api.war
    ```
2. Access the API 

    1. User APIs ```https://localhost:9443/api/users/v1/<resource>/```
    
        Sample endpoint:
        ```
            https://localhost:9443/api/users/v1/me/challenges
        ```
        Sample Request:
        ```
            curl -u admin:admin -v -X GET "https://localhost:9443/api/users/v1/me/challenges" -H "accept: application/json" -k
        ```
    
    2. Server APIs ```https://localhost:9443/api/server/v1/<resource>/```
    
        Sample endpoint:
        ```
            https://localhost:9443/api/server/v1/challenges
        ```
        Sample Request:
        ```
            curl -u admin:admin -v -X GET "https://localhost:9443/api/server/v1/challenges" -H "accept: application/json" -k
        ```
