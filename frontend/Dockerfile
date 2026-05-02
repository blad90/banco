FROM node:22-alpine AS build
WORKDIR /app
COPY frontend/banco-ui/package*.json ./
RUN npm install
COPY frontend/banco-ui .
RUN npm run build -- --configuration production