# Test Android

##Let's talk about things that you gonna see here.

###### - MVVM
###### - Coroutines
###### - Retrofit
###### - Navigation
###### - Koin
###### - Lottie
###### - Mockk (Unit tests)

Well, we did here a short implementation of an events API. 

Here you can see the event list, access an even detail, do check-in and share the event.

The app is based in MVVM architecture, with repository and networking. Using retrofit to fetch data from server with the help of coroutines.
We are using Koin, to manager our dependecy injection, navigation to navigate between our fragments, lottie,  the Airbnb lib to run animations from json and mockk to help us mocking some dependencies on our unit tests.

Keep in mind that it's a simple application, did with the proposal of be a test, so, we did just one case of unit test, a success case of getEvents method.
The main point was just give a good example of a unit test, not create a project with 100% of coverage.

And layout was free...Im not that good in design, so its simple =)

So, enjoy the code =)
