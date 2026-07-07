# 첫 번째 단계: 빌드
FROM node:alpine as build-stage
WORKDIR /app
ENV REACT_APP_PROFILE=alpha
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build-and-obfuscate

# 두 번째 단계: 서빙
FROM nginx:alpine
COPY --from=build-stage /app/build /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/default.conf

RUN chmod -R 755 /usr/share/nginx/html && \
    chown -R nginx:nginx /usr/share/nginx/html

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
