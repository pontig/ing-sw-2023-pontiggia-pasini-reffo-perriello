#### ing-sw-2023-pontiggia-pasini-reffo-perriello

# [My Shelfie](https://www.craniocreations.it/prodotto/my-shelfie) - Board game by Cranio

![My Shelfie Box](src/main/resources/images/My_Shelfie_Box.png)

## Description

The project consists of implementing a distributed system composed of a single server capable of handling only one game and multiple connected clients (from 2 to 4). 
Each client can participate in only one game at a time, but the same computer can host multiple clients simultaneously. 
The MVC (Model-View-Controller) design pattern has been used, and the network connection is managed through both Socket and RMI (Remote Method Invocation). 
Clients can independently choose to use either of them as games between different technologies are supported. 
Interactions and gameplay can take place through a command-line interface (CLI) and a graphical user interface (GUI).

## Working group - GC22
- [Elia pontiggia](https://github.com/pontig) - 10716792

  Email: elia.pontiggia@mail.polimi.it
  
- [Tommaso Pasini](https://github.com/TommiPasi) - 10717211

  Email: tommaso1.pasini@mail.polimi.it
  
- [Tommaso Reffo](https://github.com/tommymmo) - 10787027

  Email: tommaso.reffo@mail.polimi.it
  
- [Maurizio Perriello](https://github.com/MaurizioPerriello16) - 

  Email: maurizio.perriello@mail.polimi.it
  
## Project requirements

Here you can find the complete specifications: [Project Requirements](game materials/project_requirement.pdf)

| Functionality                       | State                                                    |
| :---                                | :---:                                                    |
| Basic Rules                         | ![#2EFF00](https://placehold.co/15x15/2EFF00/2EFF00.png) |
| Complete rules                      | ![#2EFF00](https://placehold.co/15x15/2EFF00/2EFF00.png) |
| Socket                              | ![#2EFF00](https://placehold.co/15x15/2EFF00/2EFF00.png) |
| RMI                                 | ![#2EFF00](https://placehold.co/15x15/2EFF00/2EFF00.png) |
| CLI                                 | ![#2EFF00](https://placehold.co/15x15/2EFF00/2EFF00.png) |
| GUI                                 | ![#2EFF00](https://placehold.co/15x15/2EFF00/2EFF00.png) |
| Chat                                | ![#2EFF00](https://placehold.co/15x15/2EFF00/2EFF00.png) |
| Server persistence                  | ![#2EFF00](https://placehold.co/15x15/2EFF00/2EFF00.png) |
| Resilience to client disconnections | ![#FFFB00](https://placehold.co/15x15/FFFB00/FFFB00.png) |
| Multiple parallel matches           | ![#FF0000](https://placehold.co/15x15/f03c15/FF0000.png) |

Legend:

![#2EFF00](https://placehold.co/15x15/2EFF00/2EFF00.png) - implemented

![#FFFB00](https://placehold.co/15x15/FFFB00/FFFB00.png) - work in progress

![#FF0000](https://placehold.co/15x15/FF0000/FF0000.png) - not implemented

## Documentation
**UML - (Unified Modeling Language)**
- [Class Diagram](deliveries/UML)                 //Need to update the folder
- [Sequance Diagram](deliveries/UML)              //Need to update the folder

**JavaDOC**

The documentation includes descriptions for the majority of the utilized classes and methods, following Java's documentation techniques. 
It can be accessed either by generating it from the code or by visiting [JavaDOC]()       //Need to update the folder

**Test Coverage** - (Server side)
- [Model Test](src/main/test/java/it/polimi/ingsw/model)
- [Controller Test]()                             //Need to update the folder

**Libraries and Plugins** 
| Library/Plugin                     | Description                                                            |
| :---                               | :---                                                                   |
| [Maven](https://maven.apache.org/) | Build automation tool primarily used for Java projects                 |
| [JavaFX](https://openjfx.io/)      | Graphic library to create user interfaces (more innovative than Swing) |
| [JUnit](https://junit.org/junit5/) | Unit testing framework                                                 |

## Run the game

**Windows**

**Ubuntu**

**MacOS**

## Other info
- Supervisor: [Prof. G. Cugola](https://cugola.faculty.polimi.it/)
- Grade: - / 30 cum laude