FROM lustefaniak/sbt:0.13.13_2.11.8

ADD . /app/

RUN sbt stage

WORKDIR /app/target/universal/stage

CMD ["bin/app"]
