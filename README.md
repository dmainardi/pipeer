# PipeeR - piping development made easy

The final goal of PipeeR is to help small companies in managing day to day working activities.  
The software, for now, is a proof of concepts, but it can be extended thanks to the GPL licence.

Features
---
* English and Italian language.
* Master data set: customers and suppliers.
* Articles: managing items with tags.
* Internal processes.
* Bills of materials: before doing an offer is always useful to prepare a cost analysis. PipeeR help users to create a tree based bill of materials, with processes and articles.

Technologies
---
* JavaEE 7 - JPA, CDI, JSF.
* [Primefaces] (http://primefaces.org)
* [BootsFaces] (http://www.bootsfaces.net)
* [Omnifaces] (http://omnifaces.org)

How to build
---
* Pull the code, it's a Maven based [Netbeans] (https://netbeans.org) project.
* PipeeR has been developed with [Glassfish] (https://glassfish.java.net), so you can use it or [Payara] (http://www.payara.fish/home).
* Open the terminal and move into Glassfish/Payara bin folder and execute [create.sh] (src/main/resources/glassfish/create.sh), a script for initializing the security realm.
* Build and run the project.
* Tada!
