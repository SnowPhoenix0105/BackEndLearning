# syntax=docker/dockerfile:1
FROM node:12-alpine
RUN sed -i 's/dl-cdn.alpinelinux.org/mirrors.aliyun.com/g' /etc/apk/repositories 
RUN apk add --no-cache python g++ make
WORKDIR /app
COPY . .
RUN ["yarn", "install", "--production"]
CMD node src/index.js