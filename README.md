<h1>CernLib</h1>
<p>
CernLib is an application built for those who are either struggling with learning how to develop and consume RESTFul API's using nodejs in their android application.


The subject of building and consuming API's has been of great importance in the
technological world especially the software development ecosystem. But as sad as
it may sound alot of young sotware developers are struggling to get acquinted with
all the intricacies involved in this subject.
Before preparing this article and project, I made some researches and found out
that there exist very little resources on how to build and consume nodejs API's
in android and as such I deemed it important to prepare a little article and build
a basic project that will point out and direct all who want to know how to consume
nodejs API's in android.
So before I continue, I would like to briefly discuss what RESTFul API's are:

A RESTful API is an application program interface (API) that uses HTTP requests to GET, PUT, POST and DELETE data.

A RESTful API -- also referred to as a RESTful web service -- is based on representational state transfer (REST) technology, an architectural style and approach to communications often used in web services development.

An API for a website is code that allows two software programs to communicate with each another. The API spells out the proper way for a developer to write a program requesting services from an operating system or other application. API's are endpoints applications (client-side applications) e.g mobile and web apps connect to inorder to make their requests to the network for data or other purposes.

The REST used by browsers can be thought of as the language of the internet. With cloud use on the rise, APIs are emerging to expose web services. REST is a logical choice for building APIs that allow users to connect and interact with cloud services. RESTful APIs are used by such sites as Amazon, Google, LinkedIn and Twitter.

"With REST, networked components are a resource you request access to -- a black box whose implementation details are unclear. The presumption is that all calls are stateless; nothing can be retained by the RESTful service between executions.

Because the calls are stateless, REST is useful in cloud applications. Stateless components can be freely redeployed if something fails, and they can scale to accommodate load changes. This is because any request can be directed to any instance of a component; there can be nothing saved that has to be remembered by the next transaction. That makes REST preferred for web use, but the RESTful model is also helpful in cloud services because binding to a service through an API is a matter of controlling how the URL is decoded. Cloud computing and microservices are almost certain to make RESTful API design the rule in the future."
<em>--extracts from</em> <a href = "https://searchmicroservices.techtarget.com/definition/RESTful-API">here</a>
</p>

<h2>How can I use this application?</h2>
<p>You can either fork or clone this repository and then you install the necessary tools that will enable you run this application. 
The necessary tools are:
<ol>
<li><strong>Android Studio</strong></li>
<li><strong>Nodejs</strong></li>
<li><strong>Mongodb</strong></li>
</ol>
</p>
<h2>Server Part</h2>
<p>
Once you have them installed, open up your command terminal, navigate to your desired location in your computer and clone this repository (if you want to clone it). Then enter "npm install" to install all the necessary node modules. After which you enter "npm start" to run your server.
But you also need to run mongo and mongod on different instances of the command terminal.
</p>
<h3>Android App Part</h3>
<p>
On the android application part, open up the android project with your Android Studio, open the "URLs" file, change the IP address present on the URLs to your computer's IP address. To get your computer's IP address open up your command terminal and enter ipconfig.
Then install your android app on your phone and make sure that you connect your computer to your phone's hotspot (i.e your mobile phone must share the same WiFi network with your computer). Then open up the app, login and enjoy!.
</p>
<br>
<p>
If you're facing difficulties in any step kindly contact me and i will assist you.
<br>

<strong>PS:</strong> This work or project was done strictly for the purpose of learning and as such some core android and nodejs (principles) were not implemented or observed.
</p>

<p>
If you love this or it was helpful to you please do share it, and generously give me a star.
<strong>Thank you.</strong>
</p>

