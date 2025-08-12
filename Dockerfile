FROM ubuntu:latest
LABEL authors="mac16"

ENTRYPOINT ["top", "-b"]