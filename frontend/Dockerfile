FROM node:20-alpine3.17

WORKDIR /opt/build/client/

COPY webapp/. /opt/build/client/

RUN npm install

RUN npm run build

ENV NODE_ENV production

EXPOSE 3000

CMD [ "npx", "serve", "build" ]
