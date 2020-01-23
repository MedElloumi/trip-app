## What happened after solving this task ? 

I have been invited for on site interview with one of the Top 3 LinkedIn Companies, have been asked few questions about Android in general and this task.

Rejected for the following reason: We have decided not to move your application forward since we are looking for a more junior profile.

##  Getting Started

The following README file will provide a short introduction about the way I followed to solve this task.

## Installation

After cloning the project, you should be able to build the project. You might need to add constraint layout manually to Android studio.

## Assumption 1

Addresses or German cities as I choose are parsed from the following URL: 

[https://api.myjson.com/bins/7n29s](https://api.myjson.com/bins/7n29s)

In any case the user was not able to fetch the following URL, A backup file stored in the Android Assets folder will be used as a source for the arrival and departure Address.

Address proposal have been implemented using an AutoCompleteTextView.

User will not be able to save his trip information unless all fields are filled and arrival and departure Address are valid.

## Assumption 2

An Android worker class is responsible to check the internet connection every 15 minutes. What will happens next will depends according to multiple factors.

I choosed to explain my approach through this activity diagram: 

![Activity Diagram](https://i.imgur.com/kfFLWIg.png)

* To reduce this diagram complexity and size I expected that all request to local storage will be done successfully.

## Authors

Mohamed Elloumi

## Documentation

See documentation under Online Documentation: [https://medelloumi.github.io/](https://medelloumi.github.io/)


# Resources

- My previous Android projects available within my Github profile
- Stack Overflow
- Official Android documentation 
