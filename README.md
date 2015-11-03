# locker

A safe place to store passwords and certificates so that Docker Containers can fetch database connection strings,
host names and passwords from a central service in a safe, encrypted, secure manner.

## Quick start
1. Make sure you have Java 1.8 and Maven 3 installed.
1. Clone this project.
1. Go to `src/main/script` and run `createSelfSignedServerCertificateKeystore.sh`
1. Go to back to the project root and run `mvn exec:java`
1. Open [the WADL](https://localhost:8443/api/application.wadl) in a browser or REST client

## Goals:

1. Provides secrets (passwords) to Docker containers through REST calls, based on the Docker's ssl
   certificate (through session token exchange)
1. Administrates which secrets are distributed to which docker, so that when a docker is compromised, it is easy to tell
   which secrets are compromised, and in turn which dockers need to be re-provisioned with new certificates and secrets.
1. Provides REST administration interface to store and delete secrets, and connect them to certificate ids of Dockers.
1. Java based, as few frameworks as possible, reliable, simple, safe, fast.

## Nice to haves:

1. Nifty User interface for the mouse-oriented administrator who is the only one awake at 3 a.m. and needs to solve a problem.
1. Distributed instances

## Example use case:

A Docker is built with an ssl identity and the location of locker.
When the docker is started, it fetches secrets through https REST calls with locker.
Locker provides secrets to the container based on its identity and the configured secrets.

The Docker file, the source code and the git repository is never contaminated with production secrets. All code can be
safely checked out by anyone. When the build server or, for instance, a Kubernetes cluster is responsible for starting
containers, the distribution of identities can be put in the hands of the build server or Kubernetes, but the secrets
will still not be visible. Only at startup time, when the Docker fetches secrets through SSL, are secrets transferred
to the memory of the Docker container. No Environment variables, no mounting of secret files under low permission
directories. Simple access.

[locker's home is on GitHub](https://github.com/realrolfje/locker)