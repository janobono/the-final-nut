FROM node:12.14-alpine as build

# install dependencies
WORKDIR /app
COPY package.json package-lock.json ./
RUN npm ci --production

###
# Only copy over the Node pieces we need
# ~> Saves 35MB
###
FROM node:12.14-alpine

RUN addgroup --gid 2000 nut; \
    adduser --disabled-password --gecos "" --home "$(pwd)" --ingroup nut --no-create-home --uid 2000 nut

WORKDIR /app

COPY --from=build /app .
COPY static static
COPY __sapper__ __sapper__

RUN chown -R nut:nut /app

EXPOSE 3000

USER nut

CMD ["node", "__sapper__/build"]
