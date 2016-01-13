# Overview #

Guice-Vaadin-MVP is a lightweight MVP framework for Vaadin.
Framework built on top of on Google Guice and inspired by Vaadin [CDI-Utils](https://vaadin.com/directory#addon/cdi-utils) addon.

## MVP motivation ##
"_MVP is a user interface design pattern engineered to facilitate automated unit testing and improve the separation of concerns in presentation logic_" - **Wikipedia**

[![](http://guice-vaadin-mvp.googlecode.com/svn/wiki/img/mvp.png)](http://www.devx.com/dotnet/Article/33695/0/page/2)


Theory can be found here:
  * http://yeejie.com/blog/category/Design-Pattern.aspx
  * http://msdn.microsoft.com/en-us/library/ff647543.aspx
  * http://martinfowler.com/eaaDev/PassiveScreen.html
  * http://en.wikipedia.org/wiki/Model–view–presenter
  * http://outcoldman.com/ru/blog/show/184


---


## Project features ##
  * Easy to configure and use MVP components;
  * Flexible internal design;
  * Localization support - injection of ResourceBundle, easy access to localized texts via TextBundle, controls auto-refresh on locale change;
  * Built-in testing support;
  * Support of preconfigured components injection;
  * Support for two additional Event Buses - Model and Shared Model;
  * Significant [test coverage](http://code.google.com/p/guice-vaadin-mvp/source/browse/tags/1.0.0/src/test/java/com/google/code/vaadin/junit).


---


## Quickstart ##

1. Read guice-servlet related [documentation](http://code.google.com/p/google-guice/wiki/Servlets)

2. Create your [ScopedUI](http://code.google.com/p/guice-vaadin-mvp/source/browse/tags/1.0.0/src/main/java/com/google/code/vaadin/application/ui/ScopedUI.java) + [AbstractMVPApplicationModule](http://code.google.com/p/guice-vaadin-mvp/source/browse/tags/1.0.0/src/main/java/com/google/code/vaadin/application/AbstractMVPApplicationModule.java) implementations and specify them in the webapp descriptor

3. Attach Guice Filter and specify [MVPApplicationContextListener](http://code.google.com/p/guice-vaadin-mvp/source/browse/tags/1.0.0/src/main/java/com/google/code/vaadin/application/MVPApplicationContextListener.java) in the webapp descriptor

4. Create your Presenter and Views
```
public interface ContactView extends View {

    void openContact();
}

public class ContactViewImpl extends AbstractView implements ContactView {

    @Inject
    @Preconfigured(nullSelectionAllowed = false, sizeFull = true, immediate = true)
    private Table contactsTable;

    @Override
    public void openContact() {
        ... some Contact selection logic from UI controls
        fireViewEvent(new ContactOpenedEvent(contactId));
    }
}

public class ContactPresenter extends AbstractPresenter<ContactView> {

    @Inject
    private ContactService contactService;

    @Observes
    protected void onEvent(ContactOpenedEvent event) {
        contactService.doSomethingWithContact(event.getContactId());
    }
}
```


---


## Mavenize ##

Artifact is available in the Central Maven repository:
```
<dependency>
    <groupId>com.google.code.guice-vaadin-mvp</groupId>
    <artifactId>guice-vaadin-mvp</artifactId>
    <version>1.0.0</version>
</dependency>
```