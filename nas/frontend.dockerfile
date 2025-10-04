# State 1
FROM node:22.18-alpine3.21 AS builder
WORKDIR /app

COPY package*.json /app
RUN npm install

COPY . /app
RUN npm run build

FROM nginx:stable-alpine3.21-perl

COPY --from=builder /app/build /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80

CMD [ "nginx", "-g", "daemon off;" ]
