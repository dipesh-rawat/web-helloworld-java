FROM openjdk:7

# Dev tools (can be removed for production)
RUN apt update && apt install -y vim curl jq

# Make a directory for the package
RUN mkdir -p /webhello
WORKDIR /webhello

# Copy in the source file
COPY ./WebHello.java /webhello

# Compile it
RUN javac WebHello.java

WORKDIR /
CMD java webhello/WebHello


