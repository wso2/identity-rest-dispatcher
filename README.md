# identity-api-dispatcher

Aggregates the API implementations from [identity-user-api](https://github.com/wso2/identity-api-user/) and 
[identity-server-api](https://github.com/wso2/identity-api-server/) builds a single webapp inorder to expose the 
multiple API endpoints in WSO2 Identity Server. 

*  Refer [identity-user-api](https://github.com/wso2/identity-api-user/) repository for the implementation 
of user APIs

*  Refer [identity-server-api](https://github.com/ayshsandu/identity-server-api/) repository for the implementation 
of server APIs

#### Exposing a new API

1. Add the dependency of the API implementation into the dependencies section of [parent.pom](https://github.com/wso2/identity-rest-dispatcher/blob/master/pom.xml#L121)
2. Include the dependency of the API implementation into the dependencies section of [parent.pom](https://github.com/wso2/identity-rest-dispatcher/blob/master/components/org.wso2.carbon.identity.api.dispatcher/pom.xml)
3. Open the [beans.xml](https://github.com/wso2/identity-rest-dispatcher/blob/master/components/org.wso2.carbon.identity.api.dispatcher/src/main/webapp/WEB-INF/beans.xml)

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
    
4. Add the API implementation from [identity-user-api](https://github.com/wso2/identity-api-user/) or 
[identity-server-api](https://github.com/wso2/identity-api-server/) under the corresponding `server` tag.

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

1. Once complete the build, copy the `api.war` in the `components/org.wso2.carbon.identity.api.dispatcher/target/` 
into `[IS_HOME]/repository/deployment/server/webapps/` location and restart the server. (If already exploded `api` 
folder exists in the location, remove it before restarting)

    ```
    ├── components
    │   └── org.wso2.carbon.identity.api.dispatcher
    │       └── target
    │           ├── api.war
    ```
2. Access the API 

    User APIs
    ```
        https://localhost:9443/api/users/v1/<resource>/<bla-bla-bla>
    ```
    
    Sample endpoint:
    ```
        https://localhost:9443/api/users/v1/me/challenges
    ```
    Sample Request:
    ```
        curl -X GET "https://localhost:9443/api/users/v1/me/challenges" -H "accept: application/json" -k
    ```
    
    Server APIs
    
    ```
        https://localhost:9443/api/server/v1/<resource>/<bla-bla-bla>
    ```
    Sample endpoint:
    ```
        https://localhost:9443/api/server/v1/challenges
    ```
    Sample Request:
    ```
        curl -X GET "https://localhost:9443/api/server/v1/challenges" -H "accept: application/json" -H -k
    ```