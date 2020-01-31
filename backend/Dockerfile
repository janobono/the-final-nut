FROM debian:10-slim

RUN addgroup --gid 1000 nut \
    && adduser --disabled-password --gecos "" --home "$(pwd)" --ingroup nut --no-create-home --uid 1000 nut

COPY target/*-runner /app/application

RUN chown -R nut:nut /app \
    && chmod 775 /app /app/application

EXPOSE 8080

USER nut

WORKDIR /app

CMD ["./application", "-Dquarkus.http.host=0.0.0.0"]
