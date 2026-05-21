FROM gradle:8.12-jdk21

WORKDIR /app

COPY . .

RUN gradle build --no-daemon

CMD ["gradle", "run"]