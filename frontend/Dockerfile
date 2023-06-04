FROM node:20-alpine3.17

WORKDIR /opt/build/client/

COPY package*.json /opt/build/client/

RUN npm install

COPY . /opt/build/client/

RUN npm run build
