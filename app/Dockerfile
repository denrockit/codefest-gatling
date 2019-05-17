FROM hseeberger/scala-sbt:8u181_2.12.8_1.2.8

ADD . /app/

RUN cd /app && sbt stage

WORKDIR /app/target/universal/stage

CMD ["bin/app"]
