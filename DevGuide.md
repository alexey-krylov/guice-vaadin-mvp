## Developer Guide ##
Welcome to the developer documentation of _guice-vaadin-mvp_ project - lightweight MVP framework for Vaadin built on top of Google Guice.

Current project version is: **1.0.0**


TODO: Localization
TODO: Testing


## Overview ##
Guice-Vaadin-MVP is an add-on for Vaadin framework aiming at simplifying application development with Vaadin and Google Guice.
Here are the utilities it provides:

1. @UIScope
Custom Guice scope designed especially for Vaadin applications.

2. Lightweight MVP framework
2.1.Extend View interface for your view
2.2.Create view instance that extends AbstractView and implements your View extension
2.3.Extend AbstractPresenter and annotate it with @ViewInterface(YourViewInterface.class)
That's it.
-The correct view instance is then automatically injected to your presenter (Your view control logic can then reside in the presenter)
-The view should use CDI's built-in event bus to fire events that are observed by the presenter. The add-on provides a utility for this: fireViewEvent(ParameterDTO). The ParameterDTO can be used to transfer any data to the presenter (CDI Event observers only accept one parameter)
-Call yourViewImplementationInstance.openView() each time the view is accessed (this will eventually invoke yourPresenterInstance.viewOpened())


3. Producers for declaratively defined Vaadin components (@Preconfigured -annotation)
Inject preconfigured Vaadin Components to your views. For example:

@Preconfigured(captionKey="btnUpdate", styleName=Button.STYLE\_LINK, enabled=false)
private Button button;


Axon-Guice is an integration module between [Google Guice](http://code.google.com/p/google-guice/) and [Axon Framework](http://www.axonframework.org).

It :
  * 
  * Full support of [1-8](http://www.axonframework.org/docs/2.0/single.html#introduction) and [10](http://www.axonframework.org/docs/2.0/single.html#performance-tuning) of original Axon Framework documentation parts (all parts except Spring integration);
  * Highly customizable API;
  * Ability to inject all major Axon Framework components: CommandBus, EventBus, CommandGateway, UnitOfWork, Repository;
  * Option to perform components auto-discovery or specify all of them manually - see [ExtendingAxonGuiceModule](ExtendingAxonGuiceModule.md);
  * Significant [test coverage](http://code.google.com/p/axon-guice/source/browse/trunk/src/test/java/com/google/code/axonguice).

## How did this work? ##

There is a four main steps:

1. Carefully read Axon [manual](http://www.axonframework.org/download/) - all Axon features is available with this project

2. Install a Axon-Guice module:
```
// auto-discovery of all CQRS/Axon components
install(new AxonGuiceModule("com.mycomp.domain"));
```

3. Define your Aggregate Roots, Entities, Commands/Events, Command/Event Handlers, Domain Services, Query Services, Sagas and so on

4. [Override specific parts of AxonGuiceModule](ExtendingAxonGuiceModule.md) to meet your production requirements - for example you can use MongoEventStore instead of default FileSystemEventStore.

## Where i can get it? ##

Project is mavenized and published in Central Maven repository.

```
<dependency>
    <groupId>com.google.code.guice-vaadin-mvp</groupId>
    <artifactId>guice-vaadin-mvp</artifactId>
    <version>${version.guice-vaadin-mvp}</version>
</dependency>
```

This project is depends on several 3rd party libraries which is required to work. So, _without_ Maven you should manually add actual versions of this 3rd party libraries. Actual list of them can be viewed in _dependencies_ section of [pom.xml](http://code.google.com/p/guice-vaadin-mvp/source/browse/trunk/pom.xml).

Actual project version will be always on top of this page.