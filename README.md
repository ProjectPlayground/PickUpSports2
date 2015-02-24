# PickupSports2
## Vision
Many students around the world participate in collegiate athletics or intramural sports. Time spent exercising with friends and classmates assists in developing healthy habits and behaviors, while promoting camaraderie and friendship. However, after school ends, it often becomes difficult to find activities and people to play sports with, and this cornerstone of many young people’s lives tends to fall by the wayside. 

This application is designed to synchronize the athletic events of friends and neighbors by promoting helpful communication specifically pertaining to the timing, location, and attendance of an event. Users should be able to sign up with an account and link with their friends so that they may be able to create athletic events for people to attend, either publically or privately, and attend other people's events.

By providing this central interface for communication tailored specifically for sporting events, this application will allow amateur sport teams and individuals to involve themselves more easily in the world around them. Ideally, users should be able to see the activities of others in their area and join in with new people who share similar interests through the ever-challenging medium of athletic competition.

## Installation Instructions
 * clone the github repo
 * import the project with Android Studio
 * open Backend
 * from terminal run node httprunner.js
 * if running from genymotion, run out of Android Studio
 * if running android emulator, change the ip address at the top of app/src/main/java/pickupsports2/ridgewell/pickupsports2/utilities/ServerRequest.java to 10.0.2.2, then run out of Android Studio

## Milestones
Prototype Link: http://bit.ly/1DTe3wF

Build Cycle 1:
 * create an android interface for the events to operate out of
 * establish a set of common files for structural elements of the application

Build Cycle 2:
 * establish Node.js backend for application
 * create retrofit adaptation of front end information and communicate jsons over http

Build Cycle 3:
 * run backend on server
 * integrate database entries useing MongoDB on backend

## User Stories
1. I want to be able to create and delete my own events.
2. I've recently moved, I want to be able to find other people to exercise with.
3. I want to feel safe when playing sports with strangers.
4. I want to browse events near me to find games to join.
5. I want to be able to establish a league or set of teams to play each other regularly.
6. I want to be able to search for users and events
7. For trivial updates about an event, I want to be able to chat with everyone who has joined an event.
