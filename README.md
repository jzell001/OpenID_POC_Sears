<h1>
<font><center><b>OpenID POC <br /> Sears Holdings Corporation</b></font><br /><br />
</h1>

This project is a Proof of Concept (POC) that focuses on implementing a third-party OpenID Provider within Sears Holdings Corporation. The OpenID service is an authentication and authorization protocol that eliminates the need for creating login credentials at additional websites. 

The third-party login service would allow Sears members to create accounts and login at participating partner websites by using their Sears login credentials. 

The applications were built on the Spring Boot framework. However, they do not utilize the built in Single Sign On (SSO) Authorization/Resource Server annotations that implement the OpenID process behind the scenes.


<h4>Note: this application utilized the Customer Information System (CIS) Application Programming Interfaces (APIs) within the Sears Quality Assurance (QA) website/system to access a user's information that is in this system. This system will not be accessible outside of the Sears Holdings Corporation. In such a case, the Custom Authentication Provider setup in the Spring Security can be changed to an  `inMemory()`  setup to accomplish the same tasks as this program. </h4>


# Getting Started

There are two separate applications in the project:

- Client application
- Sears application

<h3>Client</h3>
This application represents a client who would like to allow one of it's users to login with the external provider, in this case Sears.
<h3>Sears</h3>
This application is the Authorization and Resource Server, combined into one, for the Sears Holdings Corporation OpenID provider.

# How it Works

The client has to register with Sears prior to trying to allow a user to login via third party login. During this registration, the client is provided with an ID and a secret (referred to as the "client id" and "client secret").

As a part of the registration the client must provide their:

- name (or how they want to be referred in Sears as an application)
- homepage URL (where the user is redirected upon failure to login)
- callback URL (where the user is redirected upon successful login, and where the user's information will be sent)

The client ID, client secret, and the URLs are then stored in the Sears application. In this POC, there is only one client that has access, and it's information is stored in the `GlobalVar.java` file in both applications. The client then uses these details to obtain a user's information. 

There are three URL endpoints on the Sears side that are required to implement the process. They are:

- /accessCodeRequest
- /accessTokenRequest
- /userInfo

The process works as follows:

- The client sends their client id and their URLs via a POST request to the Sears `/accessCodeRequest` endpoint as an XML Header in the POST
- After verifying client id, Sears generates a random access code, stores that code with the client information, and sends that code back as the POST response to the client
- The client then sends their client id, client secret, and the access code to the Sears  `/accessCodeRequest` endpoint as an XML Header in the POST
- After verifying the client id, client secret, and access code, Sears generates a random access token, stores that token with the client information, and sends that token back as the POST response to the client
- The client then sends their client id, client secret, and the access token to the Sears  `/userInfo` endpoint as an XML Header in the POST
- Attempting to access this endpoint triggers Spring Security within the Sears application as access to this endpoint requires the user to login
- A login screen is generated with an email and password field
- After the user enters their email and password, they are then sent to the consent screen, where the user verifies if they would like to grant access to their information to the client application
- If the user grants access, the user's information is then sent to the callback URL of the client that was registered with Sears
- However, if the client information is incorrect (i.e., incorrect secret, code, etc), then the user is redirected to an unauthorized screen, which redirects the user back to the homepage URL that the client registered



# Running the Applications

Each application is built with Maven and Gradle included (although Gradle was not tested in this case). Each application must be run separately as they are registered to different ports.

They can be run in an Eclipse IDE, or in the command line using the following command (via Maven):

    $ mvn spring-boot:run

After each program is initialized, you can go to the following URL in your web browser:

    http://localhost:9999/client

This is where the client application is run from (port 9999, context-path client). This project uses Uber as the client application, so the initial page is the POC homepage for Uber. (Note: Uber was chosen as an example, any client can be used in real-world applications). 

The homepage for the Uber client shows a login screen with a link to login via Sears instead of the usual login process. (Note: the actual Uber login does not exist in this application. It simply redirects back to the Uber login homepage if the user tries to login via Uber directly).

Upon clicking the link to login via Sears, the user is redirected to the Sears application, which is located at the following URL:

    http://localhost:8080

Prior to reaching this screen, the client ID has already been authenticated in the background via POST requests. This page requires the user to login to the system using their email and password. After logging in, the user is redirected to the consent screen.

If the user successfully logs into the system, gives consent, and the client is registered with Sears, the user's information is then sent to the `/hello` page of the Uber (client) application with the user's information, which is then displayed on the screen.


