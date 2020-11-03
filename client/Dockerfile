# build stage
FROM node:12 as build-stage

ARG SERVER_URL
ENV SERVER_URL $SERVER_URL

RUN echo $SERVER_URL

WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build:prod

# production stage
FROM nginx:stable-alpine as production-stage
COPY ./nginx/backend.conf /etc/nginx/conf.d/
COPY --from=build-stage /app/dist/client /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
