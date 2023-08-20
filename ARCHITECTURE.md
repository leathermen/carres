# A word about the architectures in general

## Goals of every architecture

Every architecture servers to several primary goals:

-   make the maintenance cost lower (by reducing the complexity)
-   keep the project from being blocked by technologies and decisions
-   induce programmers to follow a certain idea and don't step too far from the Right Path ;)

Or, speaking practically, you have to put your files in folders, otherwise you are soon to be overwhelmed by chaos. Not good. You need some line to go along.

## The evolution of architectures

This long list of architectures and their core features is here to let you know how the bits of SOLID acronym have been discovered. And why currently Clean Architecture accumulates the best from every period.

> _(let's not mention the "procedural" "architectures" that precede 1980s as those don't really lead to any complexity reduction)_

> _"CA reference!" remark would mean that please remember this idea, as it will be important to comprehend the finishing nature of Clean Architecture.)_

Architectures have being evolving for half a century already, and from the topmost perspective the path looks like this:

-   [>> MVC](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93controller) (1979)
    -   The whole application logic is handled procedurally in Controllers.
    -   Models just mirror the state of the database providing the access to one.
-   [>> MVVM](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93viewmodel) (and MV-\*\*) (2000s)
    -   Applications grow bigger, so does a typical view. Template engines provide the way to handle the conditional logic, but even so the templates tend to bloat out.
    -   Here the View Model concept comes, simplifying the data flow back to the normal level.
-   [>> Three Layer Architecture](https://medium.com/@deanrubin/the-three-layered-architecture-fe30cb0e4a6) (2002)
    -   Presentation -> Business Logic -> DBAL
    -   DBAL becomes separated from the business logic, and business logic knows nothing about the presentation layer.
    -   Clean separate layers with some relative freedom of tactical patterns to use, within the layer. _**CA reference! [[OCP](https://www.digitalocean.com/community/conceptual-articles/s-o-l-i-d-the-first-five-principles-of-object-oriented-design#open-closed-principle)]**_
-   [>> DDD](https://betterprogramming.pub/why-domain-driven-design-203099adf32a) (2003; the article by the link is not so short, but so is the whole concept)
    -   Today, DDD is more of a strategy of the concerns separation: business modules interact as separate but friendly units, no responsibility leakage and so on. But at the beginning, DDD used to impose a certain architectural approach.
    -   The core concept: the Business is the thing everything else in the system submits to. _**CA reference! [[DIP](https://www.digitalocean.com/community/conceptual-articles/s-o-l-i-d-the-first-five-principles-of-object-oriented-design#dependency-inversion-principle)]**_
    -   Bound Contexts as the isolated units of the whole large business. Sometimes, even as smaller semi-independent micro-applications. System consists of Bound Contexts. Each Bound Context can use whatever architecture it is, with the only restriction to always be able to communicate with other Contexts in a discussed way.
    -   Here DBAL becomes LESS than a layer. _**CA reference! [[LSP](https://www.digitalocean.com/community/conceptual-articles/s-o-l-i-d-the-first-five-principles-of-object-oriented-design#liskov-substitution-principle)]**_ It, for the first time, becomes only a piece of infrastructure: **"We don't care of how the data is stored. The modelling of the processes is the key."**
-   [>> Ports and Adapters](https://medium.com/idealo-tech-blog/hexagonal-ports-adapters-architecture-e3617bcf00a0) (2005)
    -   While DDD focuses on isolating the holy business rules and processes from the dirty implementation substance, Ports and Adapters architecture brings the separation _frequently changing_ and quite _constant_ application parts. _**CA reference! [[SRP](https://www.digitalocean.com/community/conceptual-articles/s-o-l-i-d-the-first-five-principles-of-object-oriented-design#single-responsibility-principle)]**_
    -   Now, it's officially okay to have multiple input streams for the same application, as now it's easy to leverage the input due to isolated **Ports**, and so it's easy to switch from one storage to another quickly, due to easily replaceable **Adapters**.
    -   The Domain part becomes prior to DBAL. DBAL now lies somewhere out of the heart of the system, mirroring the Domain objects to enable these for the persistence.
-   [>> Onion Architecture](https://www.codeguru.com/csharp/understanding-onion-architecture/) (2008)
    -   It's an extension of Ports and Adapters architecture, in the sense of better separation of the entities into layers.
    -   It's now three separate layers: Domain, Application, Infrastructure. Infrastructure framework-wise replaces some of the Application interfaces to be used Usecase Handlers. _**CA reference! [[ISP](https://www.digitalocean.com/community/conceptual-articles/s-o-l-i-d-the-first-five-principles-of-object-oriented-design#interface-segregation-principle)]**_ (Please know the difference from [>> Three Layer Architecture](https://www.codeguru.com/csharp/understanding-onion-architecture/)!)
    -   A clean, unbroken single direction of the dependencies flow: from the outside to the inside. Layer violation is now really a _violation_. It's called **Dependency Inversion Principle**, as what "D" stands for in Solid.
-   [>> Clean Architecture](https://betterprogramming.pub/the-clean-architecture-beginners-guide-e4b7058c1165) (2012)
    -   [>> This](https://dev.to/eminetto/clean-architecture-2-years-later-4een) is also a good grasp on how CA looks down on the ground.
    -   _**CA reference**_: Takes the best from some of the preceding architectures. If you put together everything highlighted in the previous records, you'll result in **SOLID** principle, combined from multiple sources, discovered in the experimental fashion. Clean Architecture implements the pure vision of the easily maintainable code.
    -   Generally, four layers: Domain, Application, Presentation, Infrastructure. Strict dependency direction (out -> in). Easy way to keep code being changed for not more than one reason.
    -   Freedom not to make decisions until it's necessary: you can always create an Application-level interface with some ultra-simplistic implementation in Infrastructure to postpone the limitations imposed by any certain decision. **Each decision is a loss of other options.**
    -   [>> This article](https://herbertograca.com/2017/11/16/explicit-architecture-01-ddd-hexagonal-onion-clean-cqrs-how-i-put-it-all-together/) by [@hgraca](https://herbertograca.com) puts together all the half-a-century knowledge, making a bit too complicated but still declarative scheme of all the elements of this architecture. Make sure to read it. It proves the idea of Clean Architecture being something of the last complex grasp on the architecture, combining the best from various preceding approaches.

> This is a textual version of a brilliant lecture by [>> Fyodor Schyudlo](https://www.facebook.com/people/%D0%A4%D0%B5%D0%B4%D0%BE%D1%80-%D0%A9%D1%83%D0%B4%D0%BB%D0%BE/100011119055934/) at DotNetRu conference, with some additions from my side. See the [>> full version](https://www.youtube.com/watch?v=WXelYPjwmk0) or the [>> slides](https://speakerdeck.com/dotnetru/fiedor-shchudlo-evoliutsiia-enterprise-arkhitiektur-ot-mvc-do-clean-architecture). **Unfortunately, russian language only,** but you can use the automatic subtitle generation feature, if possible.

> Materials by Herberto Graca aka [>> @hraca](https://herbertograca.com) are extensively used also.

## The usage of Clean Architecture

During my carrer, I try to use Clean Architecture at every place where it makes sense for the following reasons:

-   it's easier to hand over a product organized with the clear idea in mind. Clean Architecture is definitely such an idea. A lot of references and a ton of credit.
-   it reduces the cost of the in-house maintenance, since across all the projects we use the similar ideas. Less effort, less errors, less precious cognitive load for the same amount of use.
